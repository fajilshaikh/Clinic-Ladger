package com.clinicledger.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.clinicledger.app.data.backup.BackupManager
import com.clinicledger.app.data.entity.PatientVisit
import com.clinicledger.app.data.repository.ClinicRepository
import com.clinicledger.app.data.repository.PatientVisitRepository
import com.clinicledger.app.navigation.Screen
import com.clinicledger.app.ui.components.ClinicManagementDialog
import com.clinicledger.app.ui.components.ClinicSwitcherMenu
import com.clinicledger.app.ui.components.ConfirmDialog
import com.clinicledger.app.ui.components.NewEntryDialog
import com.clinicledger.app.ui.screens.HistoryScreen
import com.clinicledger.app.ui.screens.HomeScreen
import com.clinicledger.app.ui.screens.ReportsScreen
import com.clinicledger.app.ui.screens.SettingsScreen
import com.clinicledger.app.ui.security.ActivationGate
import com.clinicledger.app.ui.theme.ClinicLedgerTheme
import com.clinicledger.app.util.deleteClinicLogo
import com.clinicledger.app.util.formatInr
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as ClinicLedgerApplication

        setContent {
            ClinicLedgerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ActivationGate {
                        ClinicLedgerApp(
                            clinicRepo = app.clinicRepository,
                            visitRepo = app.patientVisitRepository,
                            backupManager = BackupManager(applicationContext, app.database)
                        )
                    }
                }
            }
        }
    }
}

private fun iconFor(screen: Screen): ImageVector = when (screen) {
    Screen.Home -> Icons.Filled.Home
    Screen.History -> Icons.Filled.History
    Screen.Reports -> Icons.Filled.BarChart
    Screen.Settings -> Icons.Filled.Settings
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicLedgerApp(
    clinicRepo: ClinicRepository,
    visitRepo: PatientVisitRepository,
    backupManager: BackupManager
) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val activeClinicsState by clinicRepo.activeClinics.collectAsState(initial = null)
    val activeClinics = activeClinicsState ?: emptyList()
    val allClinics by clinicRepo.allClinics.collectAsState(initial = emptyList())
    var selectedClinicId by remember { mutableStateOf<Long?>(null) }

    LaunchedEffect(activeClinicsState) {
        val clinics = activeClinicsState ?: return@LaunchedEffect
        if (clinics.isEmpty()) {
            try {
                val id = clinicRepo.insertClinic("Main Clinic")
                selectedClinicId = id
            } catch (e: Exception) {
                // Another launch already created the default clinic; the next Flow emission will pick it up.
            }
        } else if (selectedClinicId == null || clinics.none { it.id == selectedClinicId }) {
            val def = clinics.find { it.isDefault } ?: clinics.first()
            selectedClinicId = def.id
        }
    }

    val selectedClinic = activeClinics.find { it.id == selectedClinicId }
    val todayVisits by remember(selectedClinicId) { visitRepo.getTodayVisits(selectedClinicId) }
        .collectAsState(initial = emptyList())
    val allVisits by visitRepo.allVisits.collectAsState(initial = emptyList())

    var showEntryDialog by remember { mutableStateOf(false) }
    var editingVisit by remember { mutableStateOf<PatientVisit?>(null) }
    var showAddClinicDialog by remember { mutableStateOf(false) }
    var newClinicName by remember { mutableStateOf("") }
    var newClinicError by remember { mutableStateOf<String?>(null) }
    var showManageClinicsDialog by remember { mutableStateOf(false) }
    var pendingDeleteVisit by remember { mutableStateOf<PatientVisit?>(null) }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    fun openNewEntry(visit: PatientVisit?) {
        editingVisit = visit
        showEntryDialog = true
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    ClinicSwitcherMenu(
                        clinics = activeClinics,
                        selectedClinicId = selectedClinicId,
                        onSelect = { selectedClinicId = it },
                        onAddNew = { showAddClinicDialog = true },
                        onManageAll = { showManageClinicsDialog = true }
                    )
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("DR", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                Screen.bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(iconFor(screen), contentDescription = screen.title) },
                        label = { Text(screen.title) }
                    )
                }
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { openNewEntry(null) },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("ADD PATIENT", fontWeight = FontWeight.Bold) }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    clinicName = selectedClinic?.name ?: "Clinic",
                    visits = todayVisits,
                    onQuickAdd = { openNewEntry(null) },
                    onEditVisit = { openNewEntry(it) },
                    onDeleteVisit = { pendingDeleteVisit = it }
                )
            }
            composable(Screen.History.route) {
                HistoryScreen(
                    clinics = allClinics,
                    visits = allVisits,
                    onEditVisit = { openNewEntry(it) },
                    onDeleteVisit = { pendingDeleteVisit = it }
                )
            }
            composable(Screen.Reports.route) {
                ReportsScreen(clinics = activeClinics, visitRepo = visitRepo)
            }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    clinicRepo = clinicRepo,
                    visitRepo = visitRepo,
                    backupManager = backupManager,
                    clinics = allClinics,
                    onManageClinics = { showManageClinicsDialog = true },
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }

    if (showEntryDialog) {
        NewEntryDialog(
            clinics = activeClinics,
            defaultClinicId = selectedClinicId,
            editingVisit = editingVisit,
            onDismiss = {
                showEntryDialog = false
                editingVisit = null
            },
            onFetchNoteSuggestions = { visitRepo.getRecentNotes() },
            onSave = { name, amount, clinicId, visitDate, notes ->
                val editing = editingVisit
                coroutineScope.launch {
                    if (editing != null) {
                        visitRepo.updateVisit(
                            editing.copy(patientName = name, amount = amount, clinicId = clinicId, visitDate = visitDate, notes = notes)
                        )
                        snackbarHostState.showSnackbar("Entry updated")
                    } else {
                        visitRepo.insertVisit(name, amount, clinicId, visitDate, notes)
                        snackbarHostState.showSnackbar("Patient entry saved!")
                    }
                }
                showEntryDialog = false
                editingVisit = null
            }
        )
    }

    if (showAddClinicDialog) {
        AlertDialog(
            onDismissRequest = {
                showAddClinicDialog = false
                newClinicName = ""
                newClinicError = null
            },
            title = { Text("Add Clinic Location") },
            text = {
                OutlinedTextField(
                    value = newClinicName,
                    onValueChange = {
                        newClinicName = it
                        newClinicError = null
                    },
                    label = { Text("Clinic Name") },
                    placeholder = { Text("e.g. City Care Clinic") },
                    isError = newClinicError != null,
                    supportingText = newClinicError?.let { { Text(it) } },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    val trimmed = newClinicName.trim()
                    when {
                        trimmed.isBlank() -> newClinicError = "Please enter a clinic name."
                        allClinics.any { it.name.equals(trimmed, ignoreCase = true) } ->
                            newClinicError = "A clinic named \"$trimmed\" already exists."
                        else -> {
                            coroutineScope.launch {
                                try {
                                    val newId = clinicRepo.insertClinic(trimmed)
                                    selectedClinicId = newId
                                    newClinicName = ""
                                    newClinicError = null
                                    showAddClinicDialog = false
                                } catch (e: Exception) {
                                    newClinicError = e.message ?: "Could not add clinic."
                                }
                            }
                        }
                    }
                }) { Text("Add") }
            },
            dismissButton = {
                TextButton(onClick = {
                    showAddClinicDialog = false
                    newClinicName = ""
                    newClinicError = null
                }) { Text("Cancel") }
            }
        )
    }

    if (showManageClinicsDialog) {
        ClinicManagementDialog(
            clinics = allClinics,
            onDismiss = { showManageClinicsDialog = false },
            onAdd = { name ->
                coroutineScope.launch {
                    try {
                        clinicRepo.insertClinic(name)
                    } catch (e: Exception) {
                        snackbarHostState.showSnackbar(e.message ?: "Could not add clinic.")
                    }
                }
            },
            onRename = { clinic, newName -> coroutineScope.launch { clinicRepo.updateClinic(clinic.copy(name = newName)) } },
            onToggleActive = { clinic ->
                coroutineScope.launch {
                    if (clinic.isActive) clinicRepo.deactivateClinic(clinic.id) else clinicRepo.activateClinic(clinic.id)
                }
            },
            onDelete = { clinic ->
                coroutineScope.launch {
                    deleteClinicLogo(clinic.logoPath)
                    clinicRepo.deleteClinicWithData(clinic.id)
                    if (selectedClinicId == clinic.id) selectedClinicId = null
                    snackbarHostState.showSnackbar("Clinic deleted")
                }
            },
            onSetLogo = { clinic, path ->
                coroutineScope.launch { clinicRepo.updateClinic(clinic.copy(logoPath = path)) }
            }
        )
    }

    pendingDeleteVisit?.let { visit ->
        ConfirmDialog(
            title = "Delete Entry?",
            message = "Delete ${visit.patientName}'s entry of ${formatInr(visit.amount)}? This cannot be undone.",
            confirmLabel = "Delete",
            onConfirm = {
                coroutineScope.launch {
                    visitRepo.deleteVisit(visit)
                    snackbarHostState.showSnackbar("Entry deleted")
                }
            },
            onDismiss = { pendingDeleteVisit = null }
        )
    }
}
