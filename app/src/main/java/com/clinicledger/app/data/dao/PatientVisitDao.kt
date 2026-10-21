package com.clinicledger.app.data.dao

import androidx.room.*
import com.clinicledger.app.data.entity.PatientVisit
import kotlinx.coroutines.flow.Flow

data class ClinicSummary(
    val clinicId: Long,
    val clinicName: String,
    val totalPatients: Int,
    val totalAmount: Long
)

data class ClinicAmountSummary(
    val clinicId: Long,
    val totalPatients: Int,
    val totalAmount: Long
)

@Dao
interface PatientVisitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVisit(visit: PatientVisit): Long

    @Update
    suspend fun updateVisit(visit: PatientVisit)

    @Delete
    suspend fun deleteVisit(visit: PatientVisit)

    @Query("DELETE FROM patient_visits WHERE id = :id")
    suspend fun deleteVisitById(id: Long)

    @Query("DELETE FROM patient_visits WHERE clinicId = :clinicId")
    suspend fun deleteVisitsByClinicId(clinicId: Long)

    @Query("SELECT * FROM patient_visits WHERE id = :id LIMIT 1")
    suspend fun getVisitById(id: Long): PatientVisit?

    @Query("SELECT * FROM patient_visits WHERE clinicId = :clinicId AND visitDate >= :startOfDay AND visitDate <= :endOfDay ORDER BY visitDate DESC")
    fun getTodayVisitsByClinic(clinicId: Long, startOfDay: Long, endOfDay: Long): Flow<List<PatientVisit>>

    @Query("SELECT * FROM patient_visits WHERE visitDate >= :startOfDay AND visitDate <= :endOfDay ORDER BY visitDate DESC")
    fun getTodayVisitsAllClinics(startOfDay: Long, endOfDay: Long): Flow<List<PatientVisit>>

    @Query("SELECT COUNT(*) FROM patient_visits WHERE clinicId = :clinicId AND visitDate >= :startOfDay AND visitDate <= :endOfDay")
    fun getTodayPatientCountByClinic(clinicId: Long, startOfDay: Long, endOfDay: Long): Flow<Int>

    @Query("SELECT COALESCE(SUM(amount), 0) FROM patient_visits WHERE clinicId = :clinicId AND visitDate >= :startOfDay AND visitDate <= :endOfDay")
    fun getTodayCollectionByClinic(clinicId: Long, startOfDay: Long, endOfDay: Long): Flow<Long>

    @Query("SELECT * FROM patient_visits WHERE visitDate >= :startDate AND visitDate <= :endDate ORDER BY visitDate DESC")
    fun getVisitsByDateRange(startDate: Long, endDate: Long): Flow<List<PatientVisit>>

    @Query("SELECT * FROM patient_visits WHERE clinicId = :clinicId AND visitDate >= :startDate AND visitDate <= :endDate ORDER BY visitDate DESC")
    fun getVisitsByClinicAndDateRange(clinicId: Long, startDate: Long, endDate: Long): Flow<List<PatientVisit>>

    @Query("SELECT * FROM patient_visits WHERE patientName LIKE '%' || :query || '%' ORDER BY visitDate DESC")
    fun searchPatients(query: String): Flow<List<PatientVisit>>

    @Query("SELECT * FROM patient_visits WHERE clinicId = :clinicId AND patientName LIKE '%' || :query || '%' ORDER BY visitDate DESC")
    fun searchPatientsInClinic(clinicId: Long, query: String): Flow<List<PatientVisit>>

    @Query("SELECT COUNT(*) FROM patient_visits WHERE clinicId = :clinicId")
    suspend fun getVisitCountForClinic(clinicId: Long): Int

    @Query(
        """
        SELECT notes FROM patient_visits
        WHERE notes IS NOT NULL AND TRIM(notes) != ''
        GROUP BY notes
        ORDER BY MAX(updatedAt) DESC
        LIMIT 30
        """
    )
    suspend fun getRecentNotes(): List<String>

    @Query(
        """
        SELECT clinicId, COUNT(*) as totalPatients, COALESCE(SUM(amount), 0) as totalAmount
        FROM patient_visits
        WHERE visitDate >= :start AND visitDate <= :end
        GROUP BY clinicId
        """
    )
    fun getClinicBreakdown(start: Long, end: Long): Flow<List<ClinicAmountSummary>>

    @Query("SELECT * FROM patient_visits ORDER BY visitDate DESC")
    fun getAllVisits(): Flow<List<PatientVisit>>

    @Query("SELECT * FROM patient_visits ORDER BY visitDate DESC")
    suspend fun getAllVisitsList(): List<PatientVisit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(visits: List<PatientVisit>)

    @Query("DELETE FROM patient_visits")
    suspend fun deleteAllVisits()
}
