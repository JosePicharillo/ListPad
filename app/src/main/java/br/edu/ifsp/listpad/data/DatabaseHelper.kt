package br.edu.ifsp.listpad.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.ifsp.listpad.model.Item
import br.edu.ifsp.listpad.model.List

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, databaseName, null, databaseVersion
) {

    companion object {
        private const val databaseName = "listpad.db"
        private const val databaseVersion = 1
        private const val tableList = "list"
        private const val tableItem = "item"
        private const val id = "id"
        private const val name = "nome"
        private const val description = "description"
        private const val flag = "flag"
        private const val category = "category"
        private const val idItem = "idItem"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val createTableList = "CREATE TABLE $tableList (" +
                "$id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$name TEXT, " +
                "$description TEXT, " +
                "$flag BOOLEAN, " +
                "$category TEXT)"
        p0?.execSQL(createTableList)

        val createTableItem = "CREATE TABLE $tableItem (" +
                "$id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$description TEXT, " +
                "$flag BOOLEAN, " +
                "$idItem INTEGER)"
        p0?.execSQL(createTableItem)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    /**
     * TABLE "LIST"
     */

    fun addList(list: List): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(id, list.id)
        values.put(name, list.nome)
        values.put(description, list.descricao)
        values.put(flag, list.flag)
        values.put(category, list.categoria)
        val result = db.insert(tableList, null, values)
        db.close()
        return result
    }

    fun updateList(list: List): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(id, list.id)
        values.put(name, list.nome)
        values.put(description, list.descricao)
        values.put(flag, list.flag)
        values.put(category, list.categoria)
        val result = db.update(tableList, values, "$id=?", arrayOf(list.id.toString()))
        db.close()
        return result
    }

    fun removeList(list: List): Int {
        val db = this.writableDatabase
        val result = db.delete(tableList, "$id=?", arrayOf(list.id.toString()))
        db.close()
        return result
    }

    fun allLists(): ArrayList<List> {
        val lists = ArrayList<List>()
        val query = "SELECT * FROM $tableList ORDER BY $name"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val t = List(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getString(4)
            )
            lists.add(t)
        }
        cursor.close()
        db.close()
        return lists
    }

    /**
     * TABlE "ITEM"
     */

    fun addItem(item: Item): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(id, item.id)
        values.put(description, item.descricao)
        values.put(flag, item.flag)
        values.put(idItem, item.idList)
        val result = db.insert(tableItem, null, values)
        db.close()
        return result
    }

    fun listItem(id: Int): ArrayList<Item> {
        val listItem = ArrayList<Item>()
        val query = "SELECT * FROM $tableItem WHERE $idItem = $id"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val i = Item(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getInt(3)
            )
            listItem.add(i)
        }
        cursor.close()
        db.close()
        return listItem
    }

    fun removeItem(item: Item): Int {
        val db = this.writableDatabase
        val result = db.delete(tableItem, "$id=?", arrayOf(item.id.toString()))
        db.close()
        return result
    }

    fun updateItem(item: Item): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(id, item.id)
        values.put(description, item.descricao)
        values.put(flag, item.flag)
        values.put(idItem, item.idList)
        val result = db.update(tableItem, values, "$id=?", arrayOf(item.id.toString()))
        db.close()
        return result
    }

}