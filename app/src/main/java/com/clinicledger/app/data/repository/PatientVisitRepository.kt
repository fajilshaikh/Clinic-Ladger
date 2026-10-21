package com.clinicledger.app.data.repository

import com.clinicledger.app.data.dao.ClinicAmountSummary
import com.clinicledger.app.data.dao.PatientVisitDao
import com.clinicledger.app.data.entity.PatientVisit
import kotlinx.coroutines.flow.Flow
import java.util.Calendar

class PatientVisitRepository(private val patientVisitDao: PatientVisitDao) {

    val allVisits: Flow<List<PatientVisit>> = patientVisitDao.getAllVisits()

    fun getClinicBreakdown(start: Long, end: Long): Flow<List<ClinicAmountSummary>> =
        patientVisitDao.getClinicBreakdown(start, end)

    fun getTodayVisits(clinicId: Long?): Flow<List<PatientVisit>> {
        val (start, end) = getTodayBounds()
        return if (clinicId != null && clinicId > 0) {
            patientVisitDao.getTodayVisitsByClinic(clinicId, start, end)
        } else {
            patientVisitDao.getTodayVisitsAllClinics(start, end)
        }
    }

    fun getTodayCount(clinicId: Long): Flow<Int> {
        val (start, end) = getTodayBounds()
        return patientVisitDao.getTodayPatientCountByClinic(clinicId, start, end)
    }

    fun getTodayCollection(clinicId: Long): Flow<Long> {
        val (start, end) = getTodayBounds()
        return patientVisitDao.getTodayCollectionByClinic(clinicId, start, end)
    }

    fun getVisitsByFilter(clinicId: Long?, startDate: Long, endDate: Long): Flow<List<PatientVisit>> {
        return if (clinicId != null && clinicId > 0) {
            patientVisitDao.getVisitsByClinicAndDateRange(clinicId, startDate, endDate)
        } else {
            patientVisitDao.getVisitsByDateRange(startDate, endDate)
        }
    }

    fun searchPatients(clinicId: Long?, query: String): Flow<List<PatientVisit>> {
        return if (clinicId != null && clinicId > 0) {
            patientVisitDao.searchPatientsInClinic(clinicId, query)
        } else {
            patientVisitDao.searchPatients(query)
        }
    }

    suspend fun insertVisit(
        patientName: String,
        amount: Long,
        clinicId: Long,
        visitDate: Long = System.currentTimeMillis(),
        notes: String? = null
    ): Long {
        require(patientName.isNotBlank()) { "Patient name is required." }
        require(amount > 0) { "Please enter a valid amount." }
        require(clinicId > 0) { "Please select a clinic." }

        val visit = PatientVisit(
            patientName = patientName.trim(),
            amount = amount,
            clinicId = clinicId,
            visitDate = visitDate,
            notes = notes?.trim()?.takeIf { it.isNotBlank() }
        )
        return patientVisitDao.insertVisit(visit)
    }

    suspend fun getRecentNotes(): List<String> = patientVisitDao.getRecentNotes()

    suspend fun updateVisit(visit: PatientVisit) {
        patientVisitDao.updateVisit(visit.copy(updatedAt = System.currentTimeMillis()))
    }

    suspend fun deleteVisit(visit: PatientVisit) {
        patientVisitDao.deleteVisit(visit)
    }

    suspend fun hasVisitsForClinic(clinicId: Long): Boolean {
        return patientVisitDao.getVisitCountForClinic(clinicId) > 0
    }

    suspend fun deleteAllVisits() = patientVisitDao.deleteAllVisits()

    private fun getTodayBounds(): Pair<Long, Long> {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
        val start = cal.timeInMillis

        cal.set(Calendar.HOUR_OF_DAY, 23)
        cal.set(Calendar.MINUTE, 59)
        cal.set(Calendar.SECOND, 59)
        cal.set(Calendar.MILLISECOND, 999)
        val end = cal.timeInMillis

        return Pair(start, end)
    }
}
