package com.abitic.monitordesempenho.data.model.relations

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.abitic.monitordesempenho.data.model.Disciplina
import com.abitic.monitordesempenho.data.model.Nota

data class DisciplinaWithNotas(
    @Embedded val disciplina:Disciplina,
    @Relation(
        parentColumn = "disciplinaId",
        entityColumn = "ownerDisciplinaId"
    )
    val notas:List<Nota>
)
