package br.edu.ifsp.listpad.adapter

import android.annotation.SuppressLint
import android.view.*
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.listpad.R
import br.edu.ifsp.listpad.model.Tarefa

class TarefaAdapter(val tarefasLista: ArrayList<Tarefa>) :
    RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder>(), Filterable {

    var listener: TarefaListener? = null
    var tarefasListaFiltrerable = ArrayList<Tarefa>() //Filtro Pesquisa

    init {
        this.tarefasListaFiltrerable = tarefasLista
    }

    fun setClickListener(listener: TarefaListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TarefaAdapter.TarefaViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.tarefa_celula, parent, false)
        return TarefaViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        holder.nome.text = tarefasListaFiltrerable[position].nome
        holder.desc.text = tarefasListaFiltrerable[position].descricao
        holder.categoria.text = tarefasListaFiltrerable[position].categoria

        if (tarefasListaFiltrerable[position].flag == 1){
            holder.urgente.setImageResource(R.drawable.ic_baseline_urgente_02)
        }
    }

    override fun getItemCount(): Int {
        return tarefasListaFiltrerable.size
    }

    inner class TarefaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nome = view.findViewById<TextView>(R.id.nome)
        val desc = view.findViewById<TextView>(R.id.descricao)
        val urgente = view.findViewById<ImageView>(R.id.urgente)
        val categoria = view.findViewById<TextView>(R.id.categoria)

        init {
            view.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    interface TarefaListener {
        fun onItemClick(pos: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charSearch = p0.toString()
                if (charSearch.isEmpty()) {
                    tarefasListaFiltrerable = tarefasLista
                } else {
                    val resultList = ArrayList<Tarefa>()
                    for (row in tarefasLista) {
                        if (row.nome.lowercase().contains(p0.toString().lowercase())) {
                            resultList.add(row)
                        }
                    }
                    tarefasListaFiltrerable = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = tarefasListaFiltrerable
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                tarefasListaFiltrerable = p1?.values as ArrayList<Tarefa>
                notifyDataSetChanged()
            }
        }
    }

}