package br.edu.ifsp.listpad.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.listpad.R
import br.edu.ifsp.listpad.model.List

class ListAdapter(val lists: ArrayList<List>) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>(), Filterable {

    var listener: ListListener? = null
    var listFilterable = ArrayList<List>()

    init {
        this.listFilterable = lists
    }

    fun setClickListener(listener: ListListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ListAdapter.ListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.lista_celula, parent, false)
        return ListViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.name.text = listFilterable[position].nome
        holder.desc.text = listFilterable[position].descricao
        holder.category.text = listFilterable[position].categoria

        if (listFilterable[position].flag == 1) {
            holder.urgent.setImageResource(R.drawable.ic_baseline_urgente_02)
        }
    }

    override fun getItemCount(): Int {
        return listFilterable.size
    }

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.nome)
        val desc: TextView = view.findViewById(R.id.descricao)
        val urgent: ImageView = view.findViewById(R.id.urgente)
        val category: TextView = view.findViewById(R.id.categoria)

        init {
            view.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    interface ListListener {
        fun onItemClick(pos: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charSearch = p0.toString()
                listFilterable = if (charSearch.isEmpty()) {
                    lists
                } else {
                    val resultList = ArrayList<List>()
                    for (row in lists) {
                        if (row.nome.lowercase().contains(p0.toString().lowercase())) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = listFilterable
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listFilterable = p1?.values as ArrayList<List>
                notifyDataSetChanged()
            }
        }
    }

}