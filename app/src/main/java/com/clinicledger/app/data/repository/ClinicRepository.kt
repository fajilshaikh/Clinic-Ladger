package com.clinicledger.app.data.repository

import androidx.room.withTransaction
import com.clinicledger.app.data.database.AppDatabase
import com.clinicledger.app.data.entity.Clinic
import kotlinx.coroutines.flow.Flow

class ClinicAlreadyExistsException(name: String) : Exception("A clinic named \"$name\" already exists.")

class ClinicRepository(private val database: AppDatabase) {
    private val clinicDao = database.clinicDao()

    val allClinics: Flow<List<Clinic>> = clinicDao.getAllClinics()
    val activeClinics: Flow<List<Clinic>> = clinicDao.getActiveClinics()

    suspend fun insertClinic(name: String): Long {
        val trimmed = name.trim()
        require(trimmed.isNotBlank()) { "Clinic name is required." }
        if (clinicDao.findByName(trimmed) != null) {
            throw ClinicAlreadyExistsException(trimmed)
        }
        val isFirst = clinicDao.getClinicCount() == 0
        val clinic = Clinic(
            name = trimmed,
            isActive = true,
            isDefault = isFirst
        )
        return clinicDao.insertClinic(clinic)
    }

    suspend fun updateClinic(clinic: Clinic) {
        clinicDao.updateClinic(clinic.copy(updatedAt = System.currentTimeMillis()))
    }

    suspend fun deactivateClinic(id: Long) {
        clinicDao.deactivateClinic(id)
    }

    suspend fun activateClinic(id: Long) {
        clinicDao.activateClinic(id)
    }

    suspend fun setDefaultClinic(id: Long) {
        clinicDao.clearDefaultClinic()
        clinicDao.setDefaultClinic(id)
    }

    suspend fun getClinicById(id: Long): Clinic? = clinicDao.getClinicById(id)

    suspend fun getClinicCount(): Int = clinicDao.getClinicCount()

    suspend fun deleteAllClinics() = clinicDao.deleteAllClinics()

    suspend fun deleteClinicWithData(clinicId: Long) {
        database.withTransaction {
            val clinic = clinicDao.getClinicById(clinicId) ?: return@withTransaction
            database.patientVisitDao().deleteVisitsByClinicId(clinicId)
            clinicDao.deleteClinic(clinic)
            if (clinic.isDefault) {
                val remaining = clinicDao.getAllClinicsSnapshot()
                val newDefault = remaining.firstOrNull { it.isActive } ?: remaining.firstOrNull()
                newDefault?.let { clinicDao.setDefaultClinic(it.id) }
            }
        }
    }
}
