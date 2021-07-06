package com.abitic.monitordesempenho.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abitic.monitordesempenho.data.model.Nota
import com.abitic.monitordesempenho.databinding.ItemProvaRecenteBinding


class NotaAdapter(private val listener:OnItemClickListener):ListAdapter<Nota,NotaAdapter.NotasViewHolder>(DiffCallBack())
{


    inner class NotasViewHolder(private val binding: ItemProvaRecenteBinding):RecyclerView.ViewHolder(binding.root)
    {
        init {
            binding.apply {
                root.setOnClickListener {
                    val position = absoluteAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val nota = getItem(position)
                        listener.onItemClick(nota)
                    }
                }
            }
        }

        fun bind(nota: Nota)
        {

            binding.apply {
                txtNota.text = nota.nota.toString()
                txtDisciplinaProva.text = nota.disciplina
                Log.d("NOTA", "bind: "+nota.disciplina+" "+nota.nota)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(nota:Nota)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotasViewHolder {
        val binding = ItemProvaRecenteBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return NotasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotasViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class DiffCallBack: DiffUtil.ItemCallback<Nota>()
    {
        override fun areItemsTheSame(oldItem: Nota, newItem: Nota) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Nota, newItem: Nota) = oldItem == newItem


    }


}