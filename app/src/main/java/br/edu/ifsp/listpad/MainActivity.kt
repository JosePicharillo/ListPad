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
import br.edu.ifsp.listpad.activity.AddListActivity
import br.edu.ifsp.listpad.activity.ItemListActivity
import br.edu.ifsp.listpad.adapter.ListAdapter
import br.edu.ifsp.listpad.data.DatabaseHelper
import br.edu.ifsp.listpad.model.List
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val db = DatabaseHelper(this)
    private var lists = ArrayList<List>()
    lateinit var listAdapter: ListAdapter
    private var titleActivity = "LISTAS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(applicationContext, AddListActivity::class.java)
            startActivity(intent)
        }

        title = titleActivity
        updateUI()
    }

    private fun updateUI() {
        lists = db.allLists()
        val text = findViewById<TextView>(R.id.textSemLista)
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        if (lists.size > 0) {
            listAdapter = ListAdapter(lists)
            text.visibility = View.GONE
            recyclerview.visibility = View.VISIBLE

            recyclerview.layoutManager = LinearLayoutManager(this)
            recyclerview.adapter = listAdapter

            val listener = object : ListAdapter.ListListener {
                override fun onItemClick(pos: Int) {
                    val intent = Intent(applicationContext, ItemListActivity::class.java)
                    val t = listAdapter.lists[pos]
                    intent.putExtra("list", t)
                    startActivity(intent)
                }
            }
            listAdapter.setClickListener(listener)
        } else {
            text.visibility = View.VISIBLE
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
                listAdapter.filter.filter(p0)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }
}