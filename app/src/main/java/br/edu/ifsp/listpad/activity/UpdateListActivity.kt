package br.edu.ifsp.listpad.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.listpad.MainActivity
import br.edu.ifsp.listpad.R
import br.edu.ifsp.listpad.data.DatabaseHelper
import br.edu.ifsp.listpad.model.List

class UpdateListActivity : AppCompatActivity() {

    private var titleActivity = "EDITAR LISTA"
    private lateinit var spinner: Spinner
    var categoria = ""
    private var list = List()
    private var urgent: Int? = 0 // FALSE = 0 | TRUE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_lista)
        title = titleActivity

        list = this.intent.getSerializableExtra("list") as List

        val nome = findViewById<EditText>(R.id.editTextNome)
        val desc = findViewById<EditText>(R.id.editTextDescricao)
        val urg = findViewById<CheckBox>(R.id.checkBoxUrgente)

        nome.setText(list.nome)
        desc.setText(list.descricao)
        if (list.flag == 1) {
            urg.isChecked = true
        }

        infoCategory()
    }

    private fun infoCategory() {
        spinner = findViewById(R.id.spinnerCategoria)
        val category = arrayOf("", "Tarefas", "Compras", "Compromissos", "Geral")
        category[0] = list.categoria
        spinner.adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, category)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                categoria = category[pos]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_editar_tarefa, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = DatabaseHelper(this)
        if (item.itemId == R.id.action_EditarTarefa) {

            val nome = findViewById<EditText>(R.id.editTextNome).text.toString()
            val desc = findViewById<EditText>(R.id.editTextDescricao).text.toString()

            val t = List(list.id, nome, desc, urgent, categoria)
            if (db.updateList(t) > 0) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Informações alteradas", Toast.LENGTH_LONG).show()
            }
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked
            urgent = if (checked) {
                1
            } else {
                0
            }
        }
    }
}