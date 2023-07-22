package com.example.pancingapps.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pancingapps.Dao.PancingDao
import com.example.pancingapps.model.Pancing

@Database(entities = [Pancing ::class], version = 1, exportSchema = false)
abstract class PancingDatabase: RoomDatabase() {
    abstract fun pancingDao(): PancingDao

    companion object {
        private var INSTANCE: PancingDatabase? = null

        private val migration1to2 : Migration =object :Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE snack_table ADD COLUMN latitude Double DEFAULT 0.0")
                database.execSQL("ALTER TABLE snack_table ADD COLUMN longitude Double DEFAULT 0.0")
            }
        }

        fun getDatabase(Context: Context): PancingDatabase {
            return INSTANCE ?: synchronized( this) {
                val instance = Room.databaseBuilder(
                    Context.applicationContext,
                    PancingDatabase::class.java,
                    "pancing_database"
                )
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}