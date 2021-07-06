package com.abitic.monitordesempenho.data.dao

import androidx.room.*
import com.abitic.monitordesempenho.data.model.Professor
import com.abitic.monitordesempenho.data.model.relations.ProfessorWithDisciplinas
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfessorDao {

    @Transaction
    @Query("SELECT * FROM tb_professor")
    fun getProfssorWithDisciplinas(): Flow<List<ProfessorWithDisciplinas>>

    @Query("SELECT * FROM tb_professor")
    fun getProfessores(): Flow<List<Professor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(professor: Professor):Long

    @Update
    suspend fun update(professor: Professor)

    @Delete
    suspend fun delete(professor: Professor)
}