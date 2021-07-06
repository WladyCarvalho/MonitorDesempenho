package com.abitic.monitordesempenho.ui.perfil

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.abitic.monitordesempenho.data.dao.ConfiguracaoUser


class PerflFViewModel @ViewModelInject constructor(
    @Assisted
    private val state:SavedStateHandle,
    private val preferencias: ConfiguracaoUser
) : ViewModel() {

    val preferenciasFlow = preferencias.preferencesFlow

}