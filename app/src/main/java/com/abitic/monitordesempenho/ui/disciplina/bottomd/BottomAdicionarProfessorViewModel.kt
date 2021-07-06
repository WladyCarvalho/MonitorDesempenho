package com.abitic.monitordesempenho.ui.disciplina.bottomd

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abitic.monitordesempenho.ADD_PROFESSOR_RESULT_OK
import com.abitic.monitordesempenho.data.dao.ConfiguracaoUser
import com.abitic.monitordesempenho.data.dao.DisciplinaDao
import com.abitic.monitordesempenho.data.dao.ProfessorDao
import com.abitic.monitordesempenho.data.model.Professor
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BottomAdicionarProfessorViewModel @ViewModelInject constructor(
    private val professorDao:ProfessorDao,
    @Assisted
    private val state: SavedStateHandle
) : ViewModel()
{
    val professor = state.get<Professor>("professor")
    var nomeProfessor = state.get<String>("nomeProfessor")?:professor?.nome?:""
        set(value) {
            field = value
            state.set("nomeProfessor",value)
        }

    private val addProfessorEventoCanal = Channel<AddProfessorEvent>()
    val addProfessorEvent = addProfessorEventoCanal.receiveAsFlow()

    fun addNovoProfessorClick(){
        if (nomeProfessor.isBlank()) {
            mostrarMensagemDeErro("Nome não pode ser nulo!")
            return
        }else {
                val novoProf = Professor(nomeProfessor)
                criarProfessor(novoProf)
        }
    }

    private fun mostrarMensagemDeErro(txt:String) = viewModelScope.launch{
        addProfessorEventoCanal.send(AddProfessorEvent.MostrarMensagemEntradaInvalida(txt))
    }


    fun criarProfessor(p:Professor) = viewModelScope.launch{
        professorDao.insert(p)
        addProfessorEventoCanal.send(AddProfessorEvent.NavegarComResultado(ADD_PROFESSOR_RESULT_OK))
    }

    sealed class AddProfessorEvent {
        data class MostrarMensagemEntradaInvalida(val msg:String):AddProfessorEvent()
        data class NavegarComResultado(val resultado:Int):AddProfessorEvent()
    }
}