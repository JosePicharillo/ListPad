package br.edu.ifsp.listpad.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.listpad.R
import br.edu.ifsp.listpad.data.DatabaseHelper
import br.edu.ifsp.listpad.model.Item
import br.edu.ifsp.listpad.model.Tarefa

class CadastroItemTarefaActivity : AppCompatActivity() {

    val db = DatabaseHelper(this)
    var TITLE = "NOVO ITEM"

    private var tarefa = Tarefa()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        tarefa = this.intent.getSerializableExtra("tarefa") as Tarefa

        title = TITLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = DatabaseHelper(this)
        if (item.itemId == R.id.action_salvarItem) {
            saveItem(db)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveItem(db: DatabaseHelper) {
        val desc = findViewById<EditText>(R.id.editTextDescItem).text.toString()

        val i = Item(null, desc, 0, tarefa.id)
        if (db.inserirItem(i) > 0) {
            Toast.makeText(this, "Novo Item Inserido", Toast.LENGTH_LONG).show()
        }
        finish()
    }
}