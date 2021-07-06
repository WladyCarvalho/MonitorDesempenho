package com.abitic.monitordesempenho.ui.add_disciplina

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.abitic.monitordesempenho.data.dao.DisciplinaDao
import com.abitic.monitordesempenho.data.dao.ProfessorDao
import com.abitic.monitordesempenho.data.model.Disciplina
import com.abitic.monitordesempenho.data.model.Semestre
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class InserirDisciplinaViewModel @ViewModelInject constructor(
    private val disciplinaDao: DisciplinaDao,
    private val professorDao: ProfessorDao,
    @Assisted
    private val state: SavedStateHandle

) : ViewModel()
{
    val professores = professorDao.getProfessores().asLiveData()
    private val addEditDisciplinaEventoCanal = Channel<AddEditDisciplinaEvent>()
    val addEditDisciplinaEvent = addEditDisciplinaEventoCanal.receiveAsFlow()

    val disciplina = state.get<Disciplina>("disciplina")

    var nomeDisciplina = state.get<String>("nomeDisciplina")?:disciplina?.desc?:""
        set(value) {
            field = value
            state.set("nomeDisciplina",value)
        }
    var idProfessor = state.get<Long>("idProfessor")?:disciplina?.professorOwnerId?:0
        set(value) {
            field = value
            state.set("idProfessor",value)
        }
    var idNivel = state.get<Long>("idNivel")?:disciplina?.nivelOwnerId?:0
        set(value) {
            field = value
            state.set("idNivel",value)
        }



     val SEMESTRES = arrayOf(
        Semestre.PRIMEIRO, Semestre.SEGUNDO
    )

    fun salvarDisciplinaClick(){
        if (nomeDisciplina.isBlank()) {
            mostrarMensagemErro("O campo não pode estar vazio")
            return
        }
        if (disciplina!=null) {
            val actualizarDisciplina = disciplina.copy(desc = nomeDisciplina, professorOwnerId = idProfessor, nivelOwnerId = idNivel)
            actualizarDisciplina(actualizarDisciplina)
        }else
        {
            val novaDisciplina = Disciplina(desc = nomeDisciplina, professorOwnerId = idProfessor, nivelOwnerId = idNivel)
            criarNovaDisciplina(novaDisciplina)
        }
    }

    private fun mostrarMensagemErro(text:String) = viewModelScope.launch {
        addEditDisciplinaEventoCanal.send(AddEditDisciplinaEvent.ShowInvalidInputMessage(text))
    }

    private fun criarNovaDisciplina(disciplina: Disciplina) = viewModelScope.launch {
        disciplinaDao.insert(disciplina)
    }

    private fun actualizarDisciplina(disciplina: Disciplina)=viewModelScope.launch {
        disciplinaDao.update(disciplina)
    }

    //Eventos para as DisciplinasFragment
    sealed class AddEditDisciplinaEvent{
        data class ShowInvalidInputMessage(val msg:String):AddEditDisciplinaEvent()
    }

}