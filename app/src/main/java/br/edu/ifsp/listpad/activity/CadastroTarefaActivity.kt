package br.edu.ifsp.listpad.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.listpad.R
import br.edu.ifsp.listpad.data.DatabaseHelper
import br.edu.ifsp.listpad.model.Tarefa

class CadastroTarefaActivity : AppCompatActivity() {

    lateinit var spinner: Spinner
    var categoria = ""
    var TITLE = "NOVA LISTA"
    var urgente: Int? = 0 // FALSE = 0 | TRUE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tarefa)

        title = TITLE

        infoCategoria()
    }

    private fun infoCategoria() {
        spinner = findViewById(R.id.spinnerCategoria)
        val categorias = arrayOf("Tarefas", "Compras", "Compromissos", "Geral")
        spinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categorias)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                categoria = categorias[pos]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = DatabaseHelper(this)
        if (item.itemId == R.id.action_salvarTarefa) {
            saveTarefa(db)
        }
        return super.onOptionsItemSelected(item)
    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            if (checked == true){
                urgente = 1
            } else {
                urgente = 0
            }
        }
    }

    private fun saveTarefa(db: DatabaseHelper) {
        val nome = findViewById<EditText>(R.id.editTextNome).text.toString()
        val desc = findViewById<EditText>(R.id.editTextDescricao).text.toString()

        val t = Tarefa(null, nome, desc, urgente, categoria)
        if (db.inserirTarefa(t) > 0) {
            Toast.makeText(this, "Nova Tarefa Inserida", Toast.LENGTH_LONG).show()
        }
        finish()
    }

}