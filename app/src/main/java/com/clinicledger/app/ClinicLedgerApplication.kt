package com.clinicledger.app

import android.app.Application
import com.clinicledger.app.data.database.AppDatabase
import com.clinicledger.app.data.repository.ClinicRepository
import com.clinicledger.app.data.repository.PatientVisitRepository

class ClinicLedgerApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val clinicRepository by lazy { ClinicRepository(database) }
    val patientVisitRepository by lazy { PatientVisitRepository(database.patientVisitDao()) }
}
