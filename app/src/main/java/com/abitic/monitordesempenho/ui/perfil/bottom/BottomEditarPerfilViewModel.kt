package com.abitic.monitordesempenho.ui.perfil.bottom

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abitic.monitordesempenho.data.dao.ConfiguracaoUser
import com.abitic.monitordesempenho.data.model.Semestre
import kotlinx.coroutines.launch

class BottomEditarPerfilViewModel @ViewModelInject constructor(
        private val preferencias: ConfiguracaoUser
) : ViewModel() {
    val SEMESTRES = arrayOf(
            Semestre.PRIMEIRO.semestre, Semestre.SEGUNDO.semestre
    )

    val preferenciasFlow = preferencias.preferencesFlow

    var ano:String = ""
    var semestre:String = ""
    var curso:String = ""
    var nome:String=""
    var position:Int=0


    fun updateUserValues() = viewModelScope.launch {
        preferencias.updateAno(ano)
        preferencias.updateNome(nome)
        preferencias.updateSemestre(semestre)
        preferencias.updateCurso(curso)
        preferencias.updatePosition(position)
    }
}