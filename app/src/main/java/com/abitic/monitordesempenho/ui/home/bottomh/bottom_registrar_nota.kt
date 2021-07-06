package com.abitic.monitordesempenho.ui.home.bottomh

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.abitic.monitordesempenho.R
import com.abitic.monitordesempenho.data.model.Disciplina
import com.abitic.monitordesempenho.databinding.BottomRegistrarNotaFragmentBinding
import com.abitic.monitordesempenho.utils.exhaustive
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class bottom_registrar_nota : BottomSheetDialogFragment(){

    private val  viewModel: BottomRegistrarNotaViewModel by viewModels()
    private  var disciplina2:Disciplina?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_registrar_nota_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = BottomRegistrarNotaFragmentBinding.bind(view)



        binding.apply {

            viewModel.disciplinas.observe(viewLifecycleOwner){
                val disciplinasAdapter = ArrayAdapter(requireContext(), R.layout.item_semestre, it)
                binding.actDisciplina.setAdapter(disciplinasAdapter)
            }

            edtNota.setText(viewModel.valorNota.toString())
            actDisciplina.setText(viewModel.valorNota.toString())

            btnAddNota.setOnClickListener {
                val nota = edtNota.text.toString().toDouble()
                viewModel.onSaveClick(nota,disciplina2!!.disciplinaId,disciplina2!!.desc)
            }

            actDisciplina.setOnItemClickListener { parent, view, position, id ->
                val disciplina = parent?.getItemAtPosition(position)
                if (disciplina is Disciplina) {
                   disciplina2 = disciplina
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addEditNotaEvent.collect { evento ->
                when(evento){
                    is BottomRegistrarNotaViewModel.AddEditNotaEvent.NavegarDevoltaComResultado -> {
                        binding.edtNota.clearFocus()
                        val action =
                            bottom_registrar_notaDirections.actionBottomRegistrarNotaToNavigationHome()
                        findNavController().navigate(action)
                    }
                    is BottomRegistrarNotaViewModel.AddEditNotaEvent.ShowInvalidInputMessage -> {
                        return@collect Snackbar.make(
                            requireView(),
                            evento.msg,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }.exhaustive
            }
        }
    }




}