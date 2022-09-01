package br.edu.ifsp.listpad.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.listpad.R
import br.edu.ifsp.listpad.data.DatabaseHelper
import br.edu.ifsp.listpad.model.List

class AddListActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    var category = ""
    private var titleNewList = "NOVA LISTA"
    private var urgent: Int? = 0 // FALSE = 0 | TRUE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_lista)

        title = titleNewList

        infoCategory()
    }

    private fun infoCategory() {
        spinner = findViewById(R.id.spinnerCategoria)
        val category = arrayOf("Tarefas", "Compras", "Compromissos", "Geral")

        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, category)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                this@AddListActivity.category = category[pos]
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
            saveTask(db)
        }
        return super.onOptionsItemSelected(item)
    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked
            urgent = if (checked){ 1 } else { 0 }
        }
    }

    private fun saveTask(db: DatabaseHelper) {
        val nome = findViewById<EditText>(R.id.editTextNome).text.toString()
        val desc = findViewById<EditText>(R.id.editTextDescricao).text.toString()

        val t = List(null, nome, desc, urgent, category)
        if (db.addList(t) > 0) {
            Toast.makeText(this, "Nova Tarefa Inserida", Toast.LENGTH_LONG).show()
        }
        finish()
    }

}