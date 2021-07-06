package com.abitic.monitordesempenho.ui.perfil.bottom

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.abitic.monitordesempenho.R
import com.abitic.monitordesempenho.databinding.BottomEditarPerfilFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bottom_adicionar_professor_fragment.*
import kotlinx.android.synthetic.main.bottom_editar_perfil_fragment.*
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class bottom_editar_perfil : BottomSheetDialogFragment(), AdapterView.OnItemSelectedListener {


    private val viewModel: BottomEditarPerfilViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_editar_perfil_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = BottomEditarPerfilFragmentBinding.bind(view)

        val SemestreAdapter = ArrayAdapter(requireContext(),R.layout.item_semestre, viewModel.SEMESTRES)
        binding.acpSemestreP.setAdapter(SemestreAdapter)

        binding.apply {

        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            binding.apply {



                edtNomeP.setText(viewModel.preferenciasFlow.first().nome)
                edtAnoP.setText(viewModel.preferenciasFlow.first().ano)
                edtCursoP.setText(viewModel.preferenciasFlow.first().curso)
                acpSemestreP.setSelection(viewModel.preferenciasFlow.first().position)

                viewModel.nome = viewModel.preferenciasFlow.first().nome
                viewModel.ano = viewModel.preferenciasFlow.first().ano
                viewModel.semestre = viewModel.preferenciasFlow.first().semestre
                viewModel.curso = viewModel.preferenciasFlow.first().curso

                edtNomeP.addTextChangedListener {
                    viewModel.nome =it.toString()
                }

                edtAnoP.addTextChangedListener {
                    viewModel.ano = it.toString()
                }

                edtCursoP.addTextChangedListener {
                    viewModel.curso = it.toString()
                }

                btnSalvarP.setOnClickListener {
                   viewModel.updateUserValues()
                    val action = bottom_editar_perfilDirections.actionBottomEditarPerfilToNavigationPerflF()
                    findNavController().navigate(action)
                }
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.semestre = viewModel.SEMESTRES[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
       //nada por enquanto
    }
}