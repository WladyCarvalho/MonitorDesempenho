package com.abitic.monitordesempenho.ui.perfil

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.abitic.monitordesempenho.R
import com.abitic.monitordesempenho.databinding.PerflFFragmentBinding
import com.abitic.monitordesempenho.ui.disciplina.bottomd.BottomAdicionarProfessorViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class perfl_f : Fragment(R.layout.perfl_f_fragment) {

    private val viewmodel: PerflFViewModel by viewModels()
    private  var bottomSheet: BottomSheetDialog?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = PerflFFragmentBinding.bind(view)
        binding.apply {

            viewLifecycleOwner.lifecycleScope.launch {
                txtNomePerfil.text = viewmodel.preferenciasFlow.first().nome
                txtAno.text = viewmodel.preferenciasFlow.first().ano
                txtCurso.text = viewmodel.preferenciasFlow.first().curso
                txtSemestre.text = viewmodel.preferenciasFlow.first().semestre
            }

            btnEditar.setOnClickListener {
               val action = perfl_fDirections.actionNavigationPerflFToBottomEditarPerfil()
                findNavController().navigate(action)
            }
        }



    }




}

