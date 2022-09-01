package br.edu.ifsp.listpad.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.listpad.R
import br.edu.ifsp.listpad.data.DatabaseHelper
import br.edu.ifsp.listpad.model.Item
import br.edu.ifsp.listpad.model.List

class AddItemListActivity : AppCompatActivity() {

    private val db = DatabaseHelper(this)
    private var titleActivity = "NOVO ITEM"

    private var list = List()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        list = this.intent.getSerializableExtra("list") as List

        title = titleActivity
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_salvarItem) {
            saveItem(db)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveItem(db: DatabaseHelper) {
        val desc = findViewById<EditText>(R.id.editTextDescItem).text.toString()

        val i = Item(null, desc, 0, list.id)
        if (db.addItem(i) > 0) {
            Toast.makeText(this, "Novo Item Inserido", Toast.LENGTH_LONG).show()
        }
        finish()
    }
}