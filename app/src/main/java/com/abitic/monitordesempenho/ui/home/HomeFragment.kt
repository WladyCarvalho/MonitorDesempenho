package com.abitic.monitordesempenho.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abitic.monitordesempenho.R
import com.abitic.monitordesempenho.data.model.Nota
import com.abitic.monitordesempenho.data.model.pojos.NotaDiscProf
import com.abitic.monitordesempenho.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home),NotaAdapter.OnItemClickListener {

    private val viewmodel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeBinding.bind(view)
        val adaptador = NotaAdapter(this)



        binding.apply {

            recyclerHome.apply {
                adapter = adaptador
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val nota = adaptador.currentList[viewHolder.absoluteAdapterPosition]
                    viewmodel.onNotaSwipe(nota)
                }
            }).attachToRecyclerView(recyclerHome)

            fabHome.setOnClickListener {
                viewmodel.onAddNovaNotaClick()
            }
        }

        viewmodel.notas.observe(viewLifecycleOwner){
            adaptador.submitList(it)
            var s=0.0
            for (n in it) {
                s+=n.nota
            }
            val media = s/it.size

            binding.txtMediaTotal.text = media.toString()

        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewmodel.notaEvent.collect {

                evento->
                when(evento){
                    is HomeViewModel.NotasEvento.MostrarMensagemDesfazerElliminar -> {
                        Snackbar.make(requireView(),"Nota eliminada", Snackbar.LENGTH_LONG)
                                .setAction("DESFAZER"){
                                    viewmodel.onDesfazerEliminarClick(evento.nota)
                                }.show()
                    }
                    HomeViewModel.NotasEvento.NavegarParaAddNota -> {
                        val action = HomeFragmentDirections.actionNavigationHomeToBottomRegistrarNota(null,"Add_Nota")
                        findNavController().navigate(action)
                    }
                    is HomeViewModel.NotasEvento.NavegarParaEditNota -> {
                        val action = HomeFragmentDirections.actionNavigationHomeToBottomRegistrarNota(evento.nota,"Editar nota")
                        findNavController().navigate(action)
                    }

                }
            }
        }
    }

    override fun onItemClick(nota: Nota) {
            viewmodel.onNotaSelecionada(nota)
    }



}




