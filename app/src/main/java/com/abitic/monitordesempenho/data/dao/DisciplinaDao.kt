package com.abitic.monitordesempenho.data.dao

import androidx.room.*
import com.abitic.monitordesempenho.data.model.Disciplina
import com.abitic.monitordesempenho.data.model.Nota
import com.abitic.monitordesempenho.data.model.relations.DisciplinaWithNotas
import com.abitic.monitordesempenho.data.model.relations.ProfessorWithDisciplinas
import kotlinx.coroutines.flow.Flow

@Dao
interface DisciplinaDao {

    @Query("SELECT * FROM tb_disciplina")
    fun getDisciplinas(): Flow<List<Disciplina>>

    @Query("SELECT * FROM tb_disciplina where ( disciplinaId==:id)")
    fun getDisciplina(id:Long): Flow<Disciplina>

    @Transaction
    @Query("SELECT * FROM tb_disciplina")
    fun getDisciplinasWithNota(): Flow<List<DisciplinaWithNotas>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(disciplina: Disciplina):Long

    @Update
    suspend fun update(disciplina: Disciplina)

    @Delete
    suspend fun delete(disciplina: Disciplina)
}