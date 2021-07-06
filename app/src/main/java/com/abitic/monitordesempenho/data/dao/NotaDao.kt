package com.abitic.monitordesempenho.data.dao

import androidx.room.*
import com.abitic.monitordesempenho.data.model.Nota
import kotlinx.coroutines.flow.Flow

@Dao
interface NotaDao {

    @Query("SELECT * FROM tb_nota ORDER BY criado DESC")
    fun getNotas(): Flow<List<Nota>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(nota:Nota)

    @Update
    suspend fun update(nota:Nota)

    @Delete
    suspend fun delete(nota:Nota)
}