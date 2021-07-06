package com.abitic.monitordesempenho.ui.disciplina

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abitic.monitordesempenho.R
import com.abitic.monitordesempenho.data.model.Disciplina
import com.abitic.monitordesempenho.data.model.Professor
import com.abitic.monitordesempenho.databinding.DisciplinasFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.disciplinas_fragment.*

@AndroidEntryPoint
class DisciplinasFragment : Fragment(R.layout.disciplinas_f_fragment),DisciplinaAdapter.OnItemClickListener {

    private val viewmodel:DisciplinasViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = DisciplinasFragmentBinding.bind(view)
        val a_adapter = DisciplinaAdapter(this)



        binding.apply {
            recyclerD.apply {
                adapter = a_adapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val disciplina = a_adapter.currentList[viewHolder.absoluteAdapterPosition]
                    viewmodel.onDisciplinaSwipe(disciplina)
                }
            }).attachToRecyclerView(recyclerD)
        }

        binding.btnAddDisciplina.setOnClickListener {
                val action = DisciplinasFragmentDirections.actionDisciplinasFragmentToInserirDisciplina(null,"")
            findNavController().navigate(action)
        }
        viewmodel.disciplinas.observe(viewLifecycleOwner){
            a_adapter.submitList(it)
        }


    }

    override fun onItemClick(disciplina: Disciplina) {
        //Navegar para edição da disciplina
    }
}