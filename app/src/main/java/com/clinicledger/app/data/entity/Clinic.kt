package com.clinicledger.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clinics")
data class Clinic(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val isActive: Boolean = true,
    val isDefault: Boolean = false,
    val logoPath: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
