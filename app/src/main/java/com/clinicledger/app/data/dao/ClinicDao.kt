package com.clinicledger.app.data.dao

import androidx.room.*
import com.clinicledger.app.data.entity.Clinic
import kotlinx.coroutines.flow.Flow

@Dao
interface ClinicDao {
    @Query("SELECT * FROM clinics ORDER BY isDefault DESC, name ASC")
    fun getAllClinics(): Flow<List<Clinic>>

    @Query("SELECT * FROM clinics WHERE isActive = 1 ORDER BY isDefault DESC, name ASC")
    fun getActiveClinics(): Flow<List<Clinic>>

    @Query("SELECT * FROM clinics WHERE id = :id LIMIT 1")
    suspend fun getClinicById(id: Long): Clinic?

    @Query("SELECT * FROM clinics WHERE name = :name COLLATE NOCASE LIMIT 1")
    suspend fun findByName(name: String): Clinic?

    @Query("SELECT * FROM clinics ORDER BY isDefault DESC, name ASC")
    suspend fun getAllClinicsSnapshot(): List<Clinic>

    @Query("SELECT COUNT(*) FROM clinics")
    suspend fun getClinicCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClinic(clinic: Clinic): Long

    @Update
    suspend fun updateClinic(clinic: Clinic)

    @Delete
    suspend fun deleteClinic(clinic: Clinic)

    @Query("UPDATE clinics SET isActive = 0, updatedAt = :timestamp WHERE id = :id")
    suspend fun deactivateClinic(id: Long, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE clinics SET isActive = 1, updatedAt = :timestamp WHERE id = :id")
    suspend fun activateClinic(id: Long, timestamp: Long = System.currentTimeMillis())

    @Query("UPDATE clinics SET isDefault = 0")
    suspend fun clearDefaultClinic()

    @Query("UPDATE clinics SET isDefault = 1 WHERE id = :id")
    suspend fun setDefaultClinic(id: Long)

    @Query("DELETE FROM clinics")
    suspend fun deleteAllClinics()
}
