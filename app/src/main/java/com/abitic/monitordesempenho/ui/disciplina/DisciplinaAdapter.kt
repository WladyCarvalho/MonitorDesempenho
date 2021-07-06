package com.abitic.monitordesempenho.ui.disciplina


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abitic.monitordesempenho.data.model.Disciplina
import com.abitic.monitordesempenho.databinding.ItemDisciplinaBinding
import androidx.recyclerview.widget.ListAdapter


class DisciplinaAdapter(private val listener: OnItemClickListener): ListAdapter<Disciplina, DisciplinaAdapter.DisciplinaViewHolder>(DiffCallBack())  {

    inner class DisciplinaViewHolder(private val binding:ItemDisciplinaBinding):RecyclerView.ViewHolder(binding.root)
    {
        init {
            binding.apply {
                root.setOnClickListener {

                }
            }
        }

        fun bind(disciplina:Disciplina)
        {
            binding.apply {
                txtDisciplina.text = disciplina.desc
            }
        }

    }

    // Fim da classe interna


    interface OnItemClickListener{
        fun onItemClick(disciplina:Disciplina)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisciplinaViewHolder {
        val binding = ItemDisciplinaBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DisciplinaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DisciplinaViewHolder, position: Int) {
       val current = getItem(position)
        holder.bind(current)
    }


class DiffCallBack: DiffUtil.ItemCallback<Disciplina>()
{
    override fun areItemsTheSame(oldItem: Disciplina, newItem: Disciplina) = oldItem.disciplinaId == newItem.disciplinaId


    override fun areContentsTheSame(oldItem: Disciplina, newItem: Disciplina) = oldItem == newItem


}

}