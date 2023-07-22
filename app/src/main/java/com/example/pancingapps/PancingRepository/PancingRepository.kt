package com.example.pancingapps.repository



import com.example.pancingapps.Dao.PancingDao
import com.example.pancingapps.model.Pancing
import kotlinx.coroutines.flow.Flow

class PancingRepository(private val pancingDao: PancingDao){
    val allPancings: Flow<List<Pancing>> = pancingDao.getALLPancing()

    suspend fun insertPancing(pancing: Pancing) {
        pancingDao.insertPancing(pancing)
    }

     suspend fun DeletePancing(pancing: Pancing) {
         pancingDao.deletePancing(pancing)

     }

    suspend fun updatePancing(pancing: Pancing) {
        pancingDao.updatePancing(pancing)
    }
}

