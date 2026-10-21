package com.clinicledger.app.ui.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.clinicledger.app.data.entity.Clinic
import com.clinicledger.app.util.saveClinicLogo

@Composable
fun ClinicManagementDialog(
    clinics: List<Clinic>,
    onDismiss: () -> Unit,
    onAdd: (String) -> Unit,
    onRename: (Clinic, String) -> Unit,
    onToggleActive: (Clinic) -> Unit,
    onDelete: (Clinic) -> Unit,
    onSetLogo: (Clinic, String) -> Unit
) {
    val context = LocalContext.current
    var newClinicName by remember { mutableStateOf("") }
    var newClinicError by remember { mutableStateOf<String?>(null) }
    var renamingClinic by remember { mutableStateOf<Clinic?>(null) }
    var deletingClinic by remember { mutableStateOf<Clinic?>(null) }
    var logoPickTarget by remember { mutableStateOf<Clinic?>(null) }
    val activeCount = clinics.count { it.isActive }

    val logoPickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        val target = logoPickTarget
        if (uri != null && target != null) {
            val path = saveClinicLogo(context, target.id, uri)
            if (path != null) {
                onSetLogo(target, path)
            }
        }
        logoPickTarget = null
    }

    Dialog(onDismissRequest = onDismiss, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Surface(
            modifier = Modifier.fillMaxWidth(0.94f).padding(vertical = 24.dp).heightIn(max = 560.dp),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.Top) {
                    IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Clinic Locations", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(
                            "Add, rename, delete, or toggle active clinics",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Box(
                        modifier = Modifier.size(36.dp).background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(10.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Business, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = newClinicName,
                            onValueChange = {
                                newClinicName = it
                                newClinicError = null
                            },
                            placeholder = { Text("New clinic name (e.g. City Care)") },
                            isError = newClinicError != null,
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        Button(onClick = {
                            val trimmed = newClinicName.trim()
                            when {
                                trimmed.isBlank() -> newClinicError = "Please enter a clinic name."
                                clinics.any { it.name.equals(trimmed, ignoreCase = true) } ->
                                    newClinicError = "A clinic named \"$trimmed\" already exists."
                                else -> {
                                    onAdd(trimmed)
                                    newClinicName = ""
                                    newClinicError = null
                                }
                            }
                        }) { Text("+ Add") }
                    }
                    newClinicError?.let {
                        Text(it, fontSize = 12.sp, color = MaterialTheme.colorScheme.error)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "REGISTERED LOCATIONS (${clinics.size})",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f, fill = false),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(clinics, key = { it.id }) { clinic ->
                        val canToggleOff = !(clinic.isActive && activeCount <= 1)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ClinicLogoAvatar(
                                logoPath = clinic.logoPath,
                                modifier = Modifier.clickable {
                                    logoPickTarget = clinic
                                    logoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Text(clinic.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                    if (clinic.isDefault) {
                                        Box(
                                            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(50)).padding(horizontal = 8.dp, vertical = 1.dp)
                                        ) {
                                            Text("Default", fontSize = 10.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                        }
                                    }
                                }
                                if (!clinic.isActive) {
                                    Text("Inactive", fontSize = 11.sp, color = MaterialTheme.colorScheme.error)
                                }
                            }
                            IconButton(onClick = { renamingClinic = clinic }) {
                                Icon(Icons.Default.Edit, contentDescription = "Rename", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(18.dp))
                            }
                            IconButton(onClick = { onToggleActive(clinic) }, enabled = canToggleOff) {
                                Icon(
                                    Icons.Default.PowerSettingsNew,
                                    contentDescription = if (clinic.isActive) "Deactivate" else "Activate",
                                    tint = if (!canToggleOff) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                                        else if (clinic.isActive) MaterialTheme.colorScheme.tertiary
                                        else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            IconButton(onClick = { deletingClinic = clinic }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Text("Deactivate vs. Delete", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Deactivating hides a clinic from daily patient entry while safely preserving its historical records. Deleting a clinic permanently removes it and all of its patient visit records — this cannot be undone.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }

    renamingClinic?.let { clinic ->
        var renameText by remember(clinic.id) { mutableStateOf(clinic.name) }
        var renameError by remember(clinic.id) { mutableStateOf<String?>(null) }
        AlertDialog(
            onDismissRequest = { renamingClinic = null },
            title = { Text("Rename Clinic") },
            text = {
                Column {
                    OutlinedTextField(
                        value = renameText,
                        onValueChange = {
                            renameText = it
                            renameError = null
                        },
                        isError = renameError != null,
                        supportingText = renameError?.let { { Text(it) } },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val trimmed = renameText.trim()
                    when {
                        trimmed.isBlank() -> renameError = "Please enter a clinic name."
                        clinics.any { it.id != clinic.id && it.name.equals(trimmed, ignoreCase = true) } ->
                            renameError = "A clinic named \"$trimmed\" already exists."
                        else -> {
                            onRename(clinic, trimmed)
                            renamingClinic = null
                        }
                    }
                }) { Text("Save") }
            },
            dismissButton = {
                TextButton(onClick = { renamingClinic = null }) { Text("Cancel") }
            }
        )
    }

    deletingClinic?.let { clinic ->
        ConfirmDialog(
            title = "Delete Clinic?",
            message = "Permanently delete \"${clinic.name}\" and all of its patient visit records? This cannot be undone.",
            confirmLabel = "Delete",
            onConfirm = { onDelete(clinic) },
            onDismiss = { deletingClinic = null }
        )
    }
}
