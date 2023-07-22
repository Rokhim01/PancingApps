package com.example.pancingapps.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pancingapps.model.Pancing
import kotlinx.coroutines.flow.Flow

@Dao
interface PancingDao {

    @Query("SELECT * FROM Pancing_table ORDER BY name ASC")

    fun getALLPancing(): Flow<List<Pancing>>

    @Insert
    suspend fun insertPancing(pancing: Pancing)

    @Delete
    suspend fun deletePancing(pancing: Pancing)

    @Update
    fun updatePancing(pancing: Pancing)
}