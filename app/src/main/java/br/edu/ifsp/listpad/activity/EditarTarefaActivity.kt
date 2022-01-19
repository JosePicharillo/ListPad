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
import br.edu.ifsp.listpad.model.Tarefa

class EditarTarefaActivity : AppCompatActivity() {

    var TITLE = "EDITAR LISTA"
    lateinit var spinner: Spinner
    var categoria = ""
    private var tarefa = Tarefa()
    var urgente: Int? = 0 // FALSE = 0 | TRUE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_tarefa)
        title = TITLE

        tarefa = this.intent.getSerializableExtra("tarefa") as Tarefa

        val nome = findViewById<EditText>(R.id.editTextNome)
        val desc = findViewById<EditText>(R.id.editTextDescricao)
        val urg = findViewById<CheckBox>(R.id.checkBoxUrgente)

        nome.setText(tarefa.nome)
        desc.setText(tarefa.descricao)
        if (tarefa.flag == 1){ urg.isChecked = true }

        infoCategoria()
    }

    private fun infoCategoria() {
        spinner = findViewById(R.id.spinnerCategoria)
        val categorias = arrayOf("", "Tarefas", "Compras", "Compromissos", "Geral")
        categorias.set(0, tarefa.categoria)
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
        menuInflater.inflate(R.menu.menu_editar_tarefa, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = DatabaseHelper(this)
        if (item.itemId == R.id.action_EditarTarefa) {

            val nome = findViewById<EditText>(R.id.editTextNome).text.toString()
            val desc = findViewById<EditText>(R.id.editTextDescricao).text.toString()

            val t = Tarefa(tarefa.id, nome, desc, urgente, categoria)
            if (db.atualizarTarefa(t) > 0) {
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

            if (checked == true){
                urgente = 1
            } else {
                urgente = 0
            }
        }
    }
}