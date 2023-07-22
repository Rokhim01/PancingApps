package com.example.pancingapps.application

import android.app.Application
import com.example.pancingapps.repository.PancingRepository

class PancingApps:Application() {
    val database by lazy { PancingDatabase.getDatabase(this) }
    val repository by lazy { PancingRepository(database.pancingDao()) }
}