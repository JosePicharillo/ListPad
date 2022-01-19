package br.edu.ifsp.listpad

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.listpad.activity.CadastroTarefaActivity
import br.edu.ifsp.listpad.activity.ItemTarefaActivity
import br.edu.ifsp.listpad.adapter.TarefaAdapter
import br.edu.ifsp.listpad.data.DatabaseHelper
import br.edu.ifsp.listpad.model.Tarefa
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    val db = DatabaseHelper(this)
    var tarefasLista = ArrayList<Tarefa>()
    lateinit var tarefaAdapter: TarefaAdapter
    var TITLE = "BLOCO DE LISTAS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(applicationContext, CadastroTarefaActivity::class.java)
            startActivity(intent)
        }

        title = TITLE
        updateUI()
    }

    fun updateUI() {
        tarefasLista = db.listarTarefas()
        val texto = findViewById<TextView>(R.id.textSemLista)
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        if (tarefasLista.size > 0) {
            tarefaAdapter = TarefaAdapter(tarefasLista)
            texto.visibility = View.GONE
            recyclerview.visibility = View.VISIBLE

            recyclerview.layoutManager = LinearLayoutManager(this)
            recyclerview.adapter = tarefaAdapter

            val listener = object : TarefaAdapter.TarefaListener {
                override fun onItemClick(pos: Int) {
                    val intent = Intent(applicationContext, ItemTarefaActivity::class.java)
                    val t = tarefaAdapter.tarefasLista[pos]
                    intent.putExtra("tarefa", t)
                    startActivity(intent)
                }
            }
            tarefaAdapter.setClickListener(listener)
        } else {
            texto.visibility = View.VISIBLE
            recyclerview.visibility = View.GONE
        }

    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                tarefaAdapter.filter.filter(p0)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}