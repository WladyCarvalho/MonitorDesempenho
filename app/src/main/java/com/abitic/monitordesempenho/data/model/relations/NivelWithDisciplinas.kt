package com.abitic.monitordesempenho.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.abitic.monitordesempenho.data.model.Disciplina
import com.abitic.monitordesempenho.data.model.Nivel

data class NivelWithDisciplinas(
    @Embedded val nivel:Nivel,
    @Relation(
        parentColumn = "nivelId",
        entityColumn = "nivelOwnerId"
    )
    val disciplinas: List<Disciplina>
)