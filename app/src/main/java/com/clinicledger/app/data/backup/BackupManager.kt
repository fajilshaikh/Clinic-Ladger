package com.clinicledger.app.data.backup

import android.content.Context
import com.clinicledger.app.data.database.AppDatabase
import com.clinicledger.app.data.entity.Clinic
import com.clinicledger.app.data.entity.PatientVisit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class BackupPayload(
    val appVersion: String = "1.0.0",
    val exportedAt: Long = System.currentTimeMillis(),
    val clinics: List<Clinic>,
    val visits: List<PatientVisit>
)

class BackupManager(private val context: Context, private val database: AppDatabase) {
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    suspend fun createBackupJson(): String = withContext(Dispatchers.IO) {
        val clinics = database.clinicDao().getAllClinics().first()
        val visits = database.patientVisitDao().getAllVisitsList()
        val payload = BackupPayload(
            clinics = clinics,
            visits = visits
        )
        gson.toJson(payload)
    }

    suspend fun restoreFromJson(jsonString: String): Result<Pair<Int, Int>> = withContext(Dispatchers.IO) {
        try {
            val payload = gson.fromJson(jsonString, BackupPayload::class.java)
            if (payload.clinics.isEmpty() && payload.visits.isEmpty()) {
                return@withContext Result.failure(Exception("Backup file contains no records."))
            }
            // Insert restored records
            for (clinic in payload.clinics) {
                database.clinicDao().insertClinic(clinic)
            }
            database.patientVisitDao().insertAll(payload.visits)
            Result.success(Pair(payload.clinics.size, payload.visits.size))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
