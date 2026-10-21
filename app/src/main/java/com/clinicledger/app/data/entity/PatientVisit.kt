package com.clinicledger.app.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "patient_visits",
    foreignKeys = [
        ForeignKey(
            entity = Clinic::class,
            parentColumns = ["id"],
            childColumns = ["clinicId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index(value = ["clinicId"]),
        Index(value = ["visitDate"]),
        Index(value = ["patientName"])
    ]
)
data class PatientVisit(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val patientName: String,
    val amount: Long, // Amount in numeric INR
    val clinicId: Long,
    val visitDate: Long = System.currentTimeMillis(),
    val notes: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
