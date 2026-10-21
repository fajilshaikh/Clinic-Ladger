package com.clinicledger.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clinicledger.app.data.entity.Clinic

@Composable
fun ClinicSwitcherMenu(
    clinics: List<Clinic>,
    selectedClinicId: Long?,
    onSelect: (Long) -> Unit,
    onAddNew: () -> Unit,
    onManageAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedClinic = clinics.find { it.id == selectedClinicId }

    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { expanded = true }
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            ClinicLogoAvatar(logoPath = selectedClinic?.logoPath, size = 20.dp, iconSize = 12.dp)
            Text(
                selectedClinic?.name ?: "Select Clinic",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, modifier = Modifier.size(18.dp))
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            Text(
                "SWITCH CLINIC LOCATION",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            clinics.forEach { clinic ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            ClinicLogoAvatar(logoPath = clinic.logoPath, size = 22.dp, iconSize = 14.dp)
                            Text(clinic.name, fontWeight = FontWeight.Medium)
                            if (clinic.isDefault) {
                                Box(
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(50))
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text("Default", fontSize = 10.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
                                }
                            }
                        }
                    },
                    trailingIcon = {
                        if (clinic.id == selectedClinicId) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                        }
                    },
                    onClick = {
                        onSelect(clinic.id)
                        expanded = false
                    }
                )
            }
            HorizontalDivider()
            DropdownMenuItem(
                text = { Text("Add New Clinic", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium) },
                leadingIcon = { Icon(Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
                onClick = {
                    expanded = false
                    onAddNew()
                }
            )
            DropdownMenuItem(
                text = { Text("Manage All Clinics", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                leadingIcon = { Icon(Icons.Default.Tune, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant) },
                onClick = {
                    expanded = false
                    onManageAll()
                }
            )
        }
    }
}
