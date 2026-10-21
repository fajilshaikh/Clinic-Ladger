package com.clinicledger.app.ui.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.clinicledger.app.data.entity.Clinic
import com.clinicledger.app.data.entity.PatientVisit
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private val PRESET_AMOUNTS = listOf(50L, 100L, 150L, 200L, 300L, 500L, 1000L)

@OptIn(ExperimentalMaterial3Api::class, androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun NewEntryDialog(
    clinics: List<Clinic>,
    defaultClinicId: Long?,
    editingVisit: PatientVisit? = null,
    onDismiss: () -> Unit,
    onFetchNoteSuggestions: suspend () -> List<String> = { emptyList() },
    onSave: (patientName: String, amount: Long, clinicId: Long, visitDate: Long, notes: String?) -> Unit
) {
    val context = LocalContext.current
    val isEditing = editingVisit != null

    var patientName by remember { mutableStateOf(editingVisit?.patientName ?: "") }
    var amountText by remember { mutableStateOf(editingVisit?.amount?.toString() ?: "") }
    var selectedClinicId by remember {
        mutableStateOf(editingVisit?.clinicId ?: defaultClinicId ?: clinics.firstOrNull()?.id)
    }
    var visitDate by remember { mutableStateOf(editingVisit?.visitDate ?: System.currentTimeMillis()) }
    var showDateTimeEditor by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf<String?>(null) }
    var amountError by remember { mutableStateOf<String?>(null) }
    var clinicDropdownExpanded by remember { mutableStateOf(false) }
    var notes by remember { mutableStateOf(editingVisit?.notes ?: "") }
    var noteSuggestions by remember { mutableStateOf<List<String>>(emptyList()) }
    var notesDropdownExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        noteSuggestions = onFetchNoteSuggestions()
    }

    val filteredNoteSuggestions = remember(notes, noteSuggestions) {
        if (notes.isBlank()) {
            noteSuggestions.take(8)
        } else {
            noteSuggestions.filter {
                it.contains(notes, ignoreCase = true) && !it.equals(notes, ignoreCase = true)
            }.take(8)
        }
    }

    val dateTimeFormatter = remember { SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault()) }
    val timeFormatter = remember { SimpleDateFormat("hh:mm a", Locale.getDefault()) }
    val selectedClinic = clinics.find { it.id == selectedClinicId }

    fun pickDate() {
        val cal = Calendar.getInstance().apply { timeInMillis = visitDate }
        DatePickerDialog(
            context,
            { _, year, month, day ->
                val newCal = Calendar.getInstance().apply { timeInMillis = visitDate }
                newCal.set(year, month, day)
                visitDate = newCal.timeInMillis
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    fun pickTime() {
        val cal = Calendar.getInstance().apply { timeInMillis = visitDate }
        TimePickerDialog(
            context,
            { _, hour, minute ->
                val newCal = Calendar.getInstance().apply { timeInMillis = visitDate }
                newCal.set(Calendar.HOUR_OF_DAY, hour)
                newCal.set(Calendar.MINUTE, minute)
                visitDate = newCal.timeInMillis
            },
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            false
        ).show()
    }

    fun attemptSave() {
        nameError = if (patientName.isBlank()) "Please fill in this field." else null
        val amount = amountText.toLongOrNull()
        amountError = if (amount == null || amount <= 0) "Please enter a valid amount." else null
        val clinicId = selectedClinicId
        if (nameError == null && amountError == null && clinicId != null) {
            onSave(patientName.trim(), amount!!, clinicId, visitDate, notes.trim().ifBlank { null })
        }
    }

    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .padding(vertical = 24.dp),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            if (isEditing) "Edit Entry" else "New Entry",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            "Fast <10s digital ledger entry",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        "PATIENT NAME *",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    OutlinedTextField(
                        value = patientName,
                        onValueChange = {
                            patientName = it
                            nameError = null
                        },
                        placeholder = { Text("e.g. Arun, Sunil, Raj") },
                        isError = nameError != null,
                        supportingText = nameError?.let { { Text(it) } },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        "AMOUNT (₹) *",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    OutlinedTextField(
                        value = amountText,
                        onValueChange = {
                            amountText = it.filter { ch -> ch.isDigit() }
                            amountError = null
                        },
                        leadingIcon = { Text("₹", fontWeight = FontWeight.Bold) },
                        placeholder = { Text("0") },
                        isError = amountError != null,
                        supportingText = amountError?.let { { Text(it) } },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        PRESET_AMOUNTS.forEach { preset ->
                            AssistChip(
                                onClick = {
                                    amountText = preset.toString()
                                    amountError = null
                                },
                                label = { Text("₹$preset") }
                            )
                        }
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        "CLINIC LOCATION *",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    ExposedDropdownMenuBox(
                        expanded = clinicDropdownExpanded,
                        onExpandedChange = { clinicDropdownExpanded = it }
                    ) {
                        OutlinedTextField(
                            value = selectedClinic?.let { it.name + if (it.isDefault) " (Default)" else "" } ?: "Select clinic",
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { Icon(Icons.Default.KeyboardArrowDown, contentDescription = null) },
                            modifier = Modifier.fillMaxWidth().menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = clinicDropdownExpanded,
                            onDismissRequest = { clinicDropdownExpanded = false }
                        ) {
                            clinics.forEach { clinic ->
                                DropdownMenuItem(
                                    text = { Text(clinic.name + if (clinic.isDefault) " (Default)" else "") },
                                    onClick = {
                                        selectedClinicId = clinic.id
                                        clinicDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        "NOTES (OPTIONAL)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    ExposedDropdownMenuBox(
                        expanded = notesDropdownExpanded && filteredNoteSuggestions.isNotEmpty(),
                        onExpandedChange = { notesDropdownExpanded = it }
                    ) {
                        OutlinedTextField(
                            value = notes,
                            onValueChange = {
                                notes = it
                                notesDropdownExpanded = true
                            },
                            placeholder = { Text("e.g. Paracetamol 500mg, 2x daily") },
                            minLines = 2,
                            maxLines = 4,
                            modifier = Modifier.fillMaxWidth().menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = notesDropdownExpanded && filteredNoteSuggestions.isNotEmpty(),
                            onDismissRequest = { notesDropdownExpanded = false }
                        ) {
                            filteredNoteSuggestions.forEach { suggestion ->
                                DropdownMenuItem(
                                    text = { Text(suggestion) },
                                    onClick = {
                                        notes = suggestion
                                        notesDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Column {
                    TextButton(onClick = { showDateTimeEditor = !showDateTimeEditor }) {
                        Icon(Icons.Default.Schedule, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Change Date/Time (Defaults to Now)", fontSize = 13.sp)
                    }
                    if (showDateTimeEditor) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(start = 4.dp)) {
                            OutlinedButton(onClick = { pickDate() }, modifier = Modifier.weight(1f)) {
                                Text(dateTimeFormatter.format(Date(visitDate)), fontSize = 12.sp)
                            }
                            OutlinedButton(onClick = { pickTime() }, modifier = Modifier.weight(1f)) {
                                Text(timeFormatter.format(Date(visitDate)), fontSize = 12.sp)
                            }
                        }
                    }
                }

                Button(
                    onClick = { attemptSave() },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isEditing) "UPDATE ENTRY" else "SAVE ENTRY", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
