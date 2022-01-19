package br.edu.ifsp.listpad.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.ifsp.listpad.model.Item
import br.edu.ifsp.listpad.model.Tarefa

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_NAME = "listpad.db"
        private val DATABASE_VERSION = 1
        private val TABLE_NAME_TAREFA = "tarefa"
        private val TABLE_NAME_ITEM = "item"
        private val ID = "id"
        private val NOME = "nome"
        private val DESC = "descricao"
        private val FLAG = "flag"
        private val CATEGORIA = "categoria"
        private val TAREFA_ID = "tarefaId"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE_TAREFA =
            "CREATE TABLE $TABLE_NAME_TAREFA ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $NOME TEXT, $DESC TEXT, $FLAG BOOLEAN, $CATEGORIA TEXT)"
        p0?.execSQL(CREATE_TABLE_TAREFA)

        val CREATE_TABLE_ITEM =
            "CREATE TABLE $TABLE_NAME_ITEM ($ID INTEGER PRIMARY KEY AUTOINCREMENT, $DESC TEXT, $FLAG BOOLEAN, $TAREFA_ID INTEGER)"
        p0?.execSQL(CREATE_TABLE_ITEM)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    /**
     * TABELA "TAREFA"
     */

    fun inserirTarefa(tarefa: Tarefa): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID, tarefa.id)
        values.put(NOME, tarefa.nome)
        values.put(DESC, tarefa.descricao)
        values.put(FLAG, tarefa.flag)
        values.put(CATEGORIA, tarefa.categoria)
        val result = db.insert(TABLE_NAME_TAREFA, null, values)
        db.close()
        return result
    }

    fun atualizarTarefa(tarefa: Tarefa): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID, tarefa.id)
        values.put(NOME, tarefa.nome)
        values.put(DESC, tarefa.descricao)
        values.put(FLAG, tarefa.flag)
        values.put(CATEGORIA, tarefa.categoria)
        val result = db.update(TABLE_NAME_TAREFA, values, "$ID=?", arrayOf(tarefa.id.toString()))
        db.close()
        return result
    }

    fun apagarTarefa(tarefa: Tarefa): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME_TAREFA, "$ID=?", arrayOf(tarefa.id.toString()))
        db.close()
        return result
    }

    fun listarTarefas(): ArrayList<Tarefa> {
        val listaTarefas = ArrayList<Tarefa>()
        val query = "SELECT * FROM $TABLE_NAME_TAREFA ORDER BY $NOME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val t = Tarefa(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getString(4)
            )
            listaTarefas.add(t)
        }
        cursor.close()
        db.close()
        return listaTarefas
    }

    /**
     * TABELA "ITEM"
     */

    fun inserirItem(item: Item): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID, item.id)
        values.put(DESC, item.descricao)
        values.put(FLAG, item.flag)
        values.put(TAREFA_ID, item.tarefaId)
        val result = db.insert(TABLE_NAME_ITEM, null, values)
        db.close()
        return result
    }

    fun listarItem(id: Int): ArrayList<Item> {
        val listaItens = ArrayList<Item>()
        val query = "SELECT * FROM $TABLE_NAME_ITEM WHERE $TAREFA_ID = $id"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val i = Item(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3)
            )
            listaItens.add(i)
        }
        cursor.close()
        db.close()
        return listaItens
    }

    fun apagarItem(item: Item): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME_ITEM, "$ID=?", arrayOf(item.id.toString()))
        db.close()
        return result
    }

    fun atualizarItem(item: Item): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID, item.id)
        values.put(DESC, item.descricao)
        values.put(FLAG, item.flag)
        values.put(TAREFA_ID, item.tarefaId)
        val result = db.update(TABLE_NAME_ITEM, values, "$ID=?", arrayOf(item.id.toString()))
        db.close()
        return result
    }

}