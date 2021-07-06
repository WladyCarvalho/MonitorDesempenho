package com.abitic.monitordesempenho.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.abitic.monitordesempenho.data.model.Disciplina
import com.abitic.monitordesempenho.data.model.pojos.NotaDiscProf

import com.abitic.monitordesempenho.data.model.relations.ProfessorWithDisciplinas
import kotlinx.coroutines.flow.Flow

@Dao
interface MonitorDao {

    @Transaction
    @Query("SELECT * FROM tb_professor")
    fun getDisciplinasWithProfessor(): Flow<List<ProfessorWithDisciplinas>>

    @Query("SELECT tb_disciplina.`desc` AS disciplina, tb_disciplina.disciplinaId AS idDisc, tb_professor.nome AS professor, " +
            "tb_professor.professorId AS idProf, tb_nota.nota AS nota, tb_nota.id AS idNota " +
            "FROM tb_disciplina, tb_nota, tb_professor " +
            "WHERE (tb_disciplina.professorOwnerId = tb_professor.professorId) AND (tb_nota.ownerDisciplinaId = tb_disciplina.disciplinaId)")
    fun getNotaDiscProf():Flow<List<NotaDiscProf>>

}