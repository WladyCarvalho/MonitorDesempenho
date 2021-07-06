package com.abitic.monitordesempenho.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.abitic.monitordesempenho.data.dao.*
import com.abitic.monitordesempenho.data.model.Disciplina
import com.abitic.monitordesempenho.data.model.Nivel
import com.abitic.monitordesempenho.data.model.Nota
import com.abitic.monitordesempenho.data.model.Professor
import com.abitic.monitordesempenho.utils.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Nota::class,Professor::class,Disciplina::class,Nivel::class], version = 2,exportSchema = true)
abstract class MonitorDB:RoomDatabase() {

    abstract fun monitorDAO():MonitorDao
    abstract fun notaDAO():NotaDao
    abstract fun nivelDAO():NivelDao
    abstract fun professorDAO():ProfessorDao
    abstract fun disciplinaDAO():DisciplinaDao

    class CallBack @Inject constructor(
        private val database: Provider<MonitorDB>,
       @ApplicationScope private val applicationScope: CoroutineScope
    ):RoomDatabase.Callback(){

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val daoProfessor = database.get().professorDAO()
            val daoNota = database.get().notaDAO()
            val daoDisciplina = database.get().disciplinaDAO()
            val daoNivel = database.get().nivelDAO()
            val daoMonitor = database.get().monitorDAO()

            applicationScope.launch {
                daoProfessor.insert(Professor("Miguel Garay"))
                daoProfessor.insert(Professor("Adisley"))
                daoProfessor.insert(Professor("Yuniel Gonzales"))
            }

        }
    }
}