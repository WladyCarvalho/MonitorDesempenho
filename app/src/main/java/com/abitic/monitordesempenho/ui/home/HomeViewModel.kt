package com.abitic.monitordesempenho.ui.home

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.abitic.monitordesempenho.ADD_NOTA_RESULT_OK
import com.abitic.monitordesempenho.EDIT_NOTA_RESULT_OK
import com.abitic.monitordesempenho.data.dao.DisciplinaDao
import com.abitic.monitordesempenho.data.dao.MonitorDao
import com.abitic.monitordesempenho.data.dao.NotaDao
import com.abitic.monitordesempenho.data.model.Nota
import com.abitic.monitordesempenho.data.model.pojos.NotaDiscProf
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
        private val notaDao: NotaDao,
        private val disciplinaDao: DisciplinaDao,
        private val monitorDao: MonitorDao,

        @Assisted
        private val state: SavedStateHandle
) : ViewModel()
{
    val provasData = monitorDao.getNotaDiscProf().asLiveData()
    val notas = notaDao.getNotas().asLiveData()

    private val notaEventoCanal = Channel<NotasEvento>()
    val notaEvent = notaEventoCanal.receiveAsFlow()

    fun onNotaSelecionada(nota:Nota) = viewModelScope.launch {
        notaEventoCanal.send(NotasEvento.NavegarParaEditNota(nota))
    }

    fun onNotaSwipe(nota:Nota) = viewModelScope.launch {
        notaDao.delete(nota)
        notaEventoCanal.send(NotasEvento.MostrarMensagemDesfazerElliminar(nota))
    }

    fun onDesfazerEliminarClick(nota:Nota) = viewModelScope.launch {
        notaDao.insert(nota)
    }

    fun onAddNovaNotaClick() = viewModelScope.launch {
        notaEventoCanal.send(NotasEvento.NavegarParaAddNota)
    }

    fun onAddEditResult(result:Int) = viewModelScope.launch {
        when(result){
            ADD_NOTA_RESULT_OK -> mostrarMensagemConfirmacao("Nota adicionada")
            EDIT_NOTA_RESULT_OK -> mostrarMensagemConfirmacao("Nota actualizada")
        }
    }



    fun mostrarMensagemConfirmacao(text:String) = viewModelScope.launch {

    }

    sealed class NotasEvento{

        object NavegarParaAddNota:NotasEvento()
        data class NavegarParaEditNota(val nota: Nota):NotasEvento()
        data class MostrarMensagemDesfazerElliminar(val nota:Nota):NotasEvento()


    }
}