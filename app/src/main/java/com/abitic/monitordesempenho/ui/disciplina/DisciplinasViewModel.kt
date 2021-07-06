package com.abitic.monitordesempenho.ui.disciplina

import android.preference.PreferenceManager
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.abitic.monitordesempenho.data.dao.ConfiguracaoUser
import com.abitic.monitordesempenho.data.dao.DisciplinaDao
import com.abitic.monitordesempenho.data.model.Disciplina
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DisciplinasViewModel @ViewModelInject constructor(
    private val disciplinaDao: DisciplinaDao,
    @Assisted
    private val state: SavedStateHandle

) : ViewModel() {

    val disciplinas = disciplinaDao.getDisciplinas().asLiveData()

    fun onDisciplinaSwipe(disciplina:Disciplina) = viewModelScope.launch {

        //Eliminar disciplina
    }

    sealed class DisciplinasEventos {
        data class mostrarDesfazerEliminacMensagem(val msg:String):DisciplinasEventos()

    }

}