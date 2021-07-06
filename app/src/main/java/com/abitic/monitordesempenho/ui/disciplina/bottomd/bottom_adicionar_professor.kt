package com.abitic.monitordesempenho.ui.disciplina.bottomd

import android.content.ContentValues.TAG
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.abitic.monitordesempenho.R
import com.abitic.monitordesempenho.databinding.BottomAdicionarProfessorFragmentBinding
import com.google.android.material.snackbar.Snackbar
import com.abitic.monitordesempenho.utils.exhaustive
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class bottom_adicionar_professor : BottomSheetDialogFragment() {

    private val viewmodel:BottomAdicionarProfessorViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_adicionar_professor_fragment,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = BottomAdicionarProfessorFragmentBinding.bind(view)


        binding.apply {

            edtNomeProfessor.addTextChangedListener {
                viewmodel.nomeProfessor = it.toString()
            }

            btnSalvarProfessor.setOnClickListener {
                viewmodel.addNovoProfessorClick()
                Log.e("SALVAR", "Salvar Prof: mostrarProf", )
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewmodel.addProfessorEvent.collect {
                evento ->
                when(evento){
                    is BottomAdicionarProfessorViewModel.AddProfessorEvent.MostrarMensagemEntradaInvalida -> {
                        return@collect Snackbar.make(view,evento.msg, Snackbar.LENGTH_LONG).show()
                    }
                    is BottomAdicionarProfessorViewModel.AddProfessorEvent.NavegarComResultado -> {
                        binding.edtNomeProfessor.clearFocus()
                       val action = bottom_adicionar_professorDirections.actionBottomAdicionarProfessorToInserirDisciplina(null,"")
                        findNavController().navigate(action)
                    }
                }.exhaustive
            }
        }
    }
}