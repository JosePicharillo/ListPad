package br.edu.ifsp.listpad.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.listpad.R
import br.edu.ifsp.listpad.model.Item

class ItemAdapter(val itemLista: ArrayList<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    var listener: ItemListener? = null
    var itemList = ArrayList<Item>()

    init {
        this.itemList = itemLista
    }

    fun setClickListener(listener: ItemListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemAdapter.ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_celula, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.desc.text = itemList[position].descricao

        if (itemList[position].flag == 1) {
            holder.flag.isChecked = true
            holder.desc.setTextColor(Color.parseColor("#FF6347"))
            holder.desc.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG)
        }

    }

    interface ItemListener {
        fun onItemClick(pos: Int)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val desc = view.findViewById<TextView>(R.id.item)
        val flag = view.findViewById<CheckBox>(R.id.check_item)

        init {
            view.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }
}