package com.abitic.monitordesempenho.data.dao

import androidx.room.*
import com.abitic.monitordesempenho.data.model.Nivel
import com.abitic.monitordesempenho.data.model.relations.NivelWithDisciplinas
import kotlinx.coroutines.flow.Flow

@Dao
interface NivelDao {

    @Query("SELECT * FROM tb_nivel")
    fun getNiveis(): Flow<List<Nivel>>

    @Transaction
    @Query("SELECT * FROM tb_nivel")
    fun getNiveisWithDisciplina():Flow<NivelWithDisciplinas>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(nivel: Nivel):Long

    @Update
    suspend fun update(nivel: Nivel)

    @Delete
    suspend fun delete(nivel: Nivel)
}