package com.abitic.monitordesempenho.ui.home.bottomh

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.abitic.monitordesempenho.ADD_NOTA_RESULT_OK
import com.abitic.monitordesempenho.EDIT_NOTA_RESULT_OK
import com.abitic.monitordesempenho.data.dao.DisciplinaDao
import com.abitic.monitordesempenho.data.dao.NotaDao
import com.abitic.monitordesempenho.data.model.Disciplina
import com.abitic.monitordesempenho.data.model.Nota
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class BottomRegistrarNotaViewModel @ViewModelInject constructor(
        private val notaDao: NotaDao,
        private val disciplinaDao: DisciplinaDao,
        @Assisted
        private val state: SavedStateHandle
) : ViewModel() {

    val disciplinas = disciplinaDao.getDisciplinas().asLiveData()
    val nota = state.get<Nota>("nota")
    var disciplina = state.get<Disciplina>("disciplina")

    var valorNota = state.get<Double>("valorNota")?:nota?.nota?:0.0
        set(value){
            field = value
            state.set("valorNota",value)
        }

    var nomeDisciplina = state.get<String>("nomeDisciplina")?:disciplina?.desc?:""
        set(value) {
            field= value
            state.set("nomeDisciplina",value)
        }

    private val addEditNotaEventCanal = Channel<AddEditNotaEvent>()
    val addEditNotaEvent = addEditNotaEventCanal.receiveAsFlow()

    fun onSaveClick(nota:Double, id:Long, disciplina:String){
        val novaNota = Nota(nota,disciplina,id)
        criarNota(novaNota)
    }

    private fun mostrarMensagem(text:String) = viewModelScope.launch {
        addEditNotaEventCanal.send(AddEditNotaEvent.ShowInvalidInputMessage(text))
    }

    private fun criarNota(nota:Nota) = viewModelScope.launch {
        notaDao.insert(nota)
        addEditNotaEventCanal.send(AddEditNotaEvent.NavegarDevoltaComResultado(ADD_NOTA_RESULT_OK))
    }

    private fun updateNota(nota:Nota) = viewModelScope.launch {
        notaDao.update(nota)
        addEditNotaEventCanal.send(AddEditNotaEvent.NavegarDevoltaComResultado(EDIT_NOTA_RESULT_OK))
    }

    sealed class AddEditNotaEvent {
        data class ShowInvalidInputMessage(val msg:String):AddEditNotaEvent()
        data class NavegarDevoltaComResultado(val result:Int):AddEditNotaEvent()
    }


}