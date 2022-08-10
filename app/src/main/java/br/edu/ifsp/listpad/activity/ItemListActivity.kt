package br.edu.ifsp.listpad.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.listpad.R
import br.edu.ifsp.listpad.adapter.ItemAdapter
import br.edu.ifsp.listpad.data.DatabaseHelper
import br.edu.ifsp.listpad.model.Item
import br.edu.ifsp.listpad.model.List
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ItemListActivity : AppCompatActivity() {

    private var titleActivity = "ITENS LISTA"
    private var itemList = ArrayList<Item>()
    lateinit var itemAdapter: ItemAdapter
    private var list = List()
    private val db = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        title = titleActivity

        list = this.intent.getSerializableExtra("list") as List

        val fab = findViewById<FloatingActionButton>(R.id.fab_item)
        fab.setOnClickListener {
            val intent = Intent(applicationContext, AddItemListActivity::class.java)
            intent.putExtra("list", list)
            startActivity(intent)
        }

        updateUI()
    }

    private fun updateUI() {
        itemList = list.id?.let { db.listItem(it) }!!
        val text = findViewById<TextView>(R.id.textSemItem)
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview_item)

        if (itemList.size > 0) {
            itemAdapter = ItemAdapter(itemList)
            text.visibility = View.GONE
            recyclerview.visibility = View.VISIBLE

            recyclerview.layoutManager = LinearLayoutManager(this)
            recyclerview.adapter = itemAdapter

            val listener = object : ItemAdapter.ItemListener {
                override fun onItemClick(pos: Int) {
                    val i = itemAdapter.itemList[pos]
                    updateItem(i)
                }
            }
            itemAdapter.setClickListener(listener)
        } else {
            text.visibility = View.VISIBLE
            recyclerview.visibility = View.GONE
        }

    }

    fun updateItem(i: Item) {
        if (i.flag == 0) {
            val item = Item(i.id, i.descricao, 1, i.idList)
            if (db.updateItem(item) > 0) {
                Toast.makeText(this, "Item Finalizado", Toast.LENGTH_LONG).show()
            }
            updateUI()
        } else {
            val item = Item(i.id, i.descricao, 0, i.idList)
            if (db.updateItem(item) > 0) {
                Toast.makeText(this, "Item Não Finalizado", Toast.LENGTH_LONG).show()
            }
            updateUI()
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = DatabaseHelper(this)

        if (item.itemId == R.id.action_removerTarefa) {
            if (itemList.size == 0) {
                if (db.removeList(list) > 0) {
                    Toast.makeText(this, "Lista Excluída", Toast.LENGTH_LONG).show()
                }
                finish()
            } else {
                for (i in itemList) {
                    db.removeItem(i)
                }
                if (db.removeList(list) > 0) {
                    Toast.makeText(this, "Lista Excluída", Toast.LENGTH_LONG).show()
                }
                finish()
            }
        }

        if (item.itemId == R.id.action_editarTarefa) {
            val intent = Intent(applicationContext, UpdateListActivity::class.java)
            intent.putExtra("list", list)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

}