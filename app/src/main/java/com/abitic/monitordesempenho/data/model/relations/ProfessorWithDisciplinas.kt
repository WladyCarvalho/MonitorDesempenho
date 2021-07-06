package com.abitic.monitordesempenho.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.abitic.monitordesempenho.data.model.Disciplina
import com.abitic.monitordesempenho.data.model.Professor

data class ProfessorWithDisciplinas
    (
    @Embedded val professor:Professor,
    @Relation(
        parentColumn = "professorId",
        entityColumn = "professorOwnerId"
    )
    val disciplinas:List<Disciplina>
     )
