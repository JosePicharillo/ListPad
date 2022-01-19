package br.edu.ifsp.listpad.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.listpad.R
import br.edu.ifsp.listpad.adapter.ItemAdapter
import br.edu.ifsp.listpad.data.DatabaseHelper
import br.edu.ifsp.listpad.model.Item
import br.edu.ifsp.listpad.model.Tarefa
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ItemTarefaActivity : AppCompatActivity() {

    var TITLE = "ITENS LISTA"
    var itensLista = ArrayList<Item>()
    lateinit var itemAdapter: ItemAdapter
    private var tarefa = Tarefa()
    val db = DatabaseHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        title = TITLE

        tarefa = this.intent.getSerializableExtra("tarefa") as Tarefa

        val fab = findViewById<FloatingActionButton>(R.id.fab_item)
        fab.setOnClickListener {
            val intent = Intent(applicationContext, CadastroItemTarefaActivity::class.java)
            intent.putExtra("tarefa", tarefa)
            startActivity(intent)
        }

        updateUI()
    }

    fun updateUI() {
        itensLista = tarefa.id?.let { db.listarItem(it) }!!
        val texto = findViewById<TextView>(R.id.textSemItem)
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview_item)

        if (itensLista.size > 0){
            itemAdapter = ItemAdapter(itensLista)
            texto.visibility = View.GONE
            recyclerview.visibility = View.VISIBLE

            recyclerview.layoutManager = LinearLayoutManager(this)
            recyclerview.adapter = itemAdapter

            var listener = object : ItemAdapter.ItemListener {
                override fun onItemClick(pos: Int) {
                    val i = itemAdapter.itemList[pos]
                    atualizarItem(i)
                }
            }
            itemAdapter.setClickListener(listener)
        } else {
            texto.visibility = View.VISIBLE
            recyclerview.visibility = View.GONE
        }

    }

    fun atualizarItem(i: Item) {
        if (i.flag == 0) {
            val item = Item(i.id, i.descricao, 1, i.tarefaId)
            if (db.atualizarItem(item) > 0) {
                Toast.makeText(this, "Item Finalizado", Toast.LENGTH_LONG).show()
            }
            updateUI()
        } else {
            val item = Item(i.id, i.descricao, 0, i.tarefaId)
            if (db.atualizarItem(item) > 0) {
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
            if (itensLista.size == 0) {
                if (db.apagarTarefa(tarefa) > 0) {
                    Toast.makeText(this, "Lista Excluída", Toast.LENGTH_LONG).show()
                }
                finish()
            } else {
                for (i in itensLista) {
                    db.apagarItem(i)
                }
                if (db.apagarTarefa(tarefa) > 0) {
                    Toast.makeText(this, "Lista Excluída", Toast.LENGTH_LONG).show()
                }
                finish()
            }
        }

        if (item.itemId == R.id.action_editarTarefa) {
            val intent = Intent(applicationContext, EditarTarefaActivity::class.java)
            intent.putExtra("tarefa", tarefa)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

}