package com.abitic.monitordesempenho.ui.add_disciplina

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.abitic.monitordesempenho.R
import com.abitic.monitordesempenho.databinding.InserirDisciplinaFragmentBinding
import com.abitic.monitordesempenho.utils.exhaustive
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class Inserir_disciplina : Fragment(R.layout.inserir_disciplina_fragment) {

    private val viewModel: InserirDisciplinaViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val binding = InserirDisciplinaFragmentBinding.bind(view)


        val SemestreAdapter = ArrayAdapter(requireContext(),R.layout.item_semestre, viewModel.SEMESTRES)
        binding.dropSemestre.setAdapter(SemestreAdapter)

        viewModel.professores.observe(viewLifecycleOwner)
        {
            val ProfessoresAdpter = ArrayAdapter(requireContext(),R.layout.item_semestre,it)
            binding.dropProfessor.setAdapter(ProfessoresAdpter)
        }

        binding.apply {

                txtNomeDisciplina.setText(viewModel.nomeDisciplina)
                txtNomeDisciplina.addTextChangedListener {
                    viewModel.nomeDisciplina = it.toString()
                }


                btnSalvarDisciplina.setOnClickListener {
                    viewModel.salvarDisciplinaClick()
                }

                btnAddProfessor.setOnClickListener {
                    val action = Inserir_disciplinaDirections.actionInserirDisciplinaToBottomAdicionarProfessor()
                    findNavController().navigate(action)
                }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.addEditDisciplinaEvent.collect {
                evento ->
                when(evento){
                    is InserirDisciplinaViewModel.AddEditDisciplinaEvent.ShowInvalidInputMessage -> {
                        return@collect Snackbar.make(requireView(),evento.msg, Snackbar.LENGTH_LONG).show()
                    }
                }.exhaustive
            }
        }

}

}