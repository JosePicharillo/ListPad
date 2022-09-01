package br.edu.ifsp.listpad.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.listpad.R
import br.edu.ifsp.listpad.model.Item

class ItemAdapter(val item: ArrayList<Item>) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    var listener: ItemListener? = null
    var itemList = ArrayList<Item>()

    init {
        this.itemList = item
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
            holder.check.setImageResource(R.drawable.ic_baseline_radio_button_checked_24)
            holder.desc.setTextColor(Color.parseColor("#C8191B"))
            holder.desc.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.check.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24)
            holder.desc.setTextColor(Color.parseColor("#008D58"))
        }

    }

    interface ItemListener {
        fun onItemClick(pos: Int)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val desc: TextView = view.findViewById(R.id.item)
        val check: ImageView = view.findViewById(R.id.image_check)

        init {
            view.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }
}