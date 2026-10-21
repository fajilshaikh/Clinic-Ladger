package com.clinicledger.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clinicledger.app.data.entity.Clinic
import com.clinicledger.app.data.entity.PatientVisit
import com.clinicledger.app.ui.components.VisitCard

@Composable
fun HistoryScreen(
    clinics: List<Clinic>,
    visits: List<PatientVisit>,
    onEditVisit: (PatientVisit) -> Unit,
    onDeleteVisit: (PatientVisit) -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }
    val clinicNamesById = remember(clinics) { clinics.associate { it.id to it.name } }
    val filteredVisits = remember(visits, query) {
        if (query.isBlank()) visits
        else visits.filter { it.patientName.contains(query, ignoreCase = true) }
    }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Search patient name") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (filteredVisits.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(top = 48.dp), contentAlignment = Alignment.TopCenter) {
                Text(
                    if (query.isBlank()) "No patient visits recorded yet." else "No patients match \"$query\".",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(bottom = 96.dp)) {
                items(filteredVisits, key = { it.id }) { visit ->
                    VisitCard(
                        visit = visit,
                        clinicName = clinicNamesById[visit.clinicId] ?: "Unknown Clinic",
                        showDate = true,
                        onEdit = { onEditVisit(visit) },
                        onDelete = { onDeleteVisit(visit) }
                    )
                }
            }
        }
    }
}
