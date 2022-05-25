package com.ncabanes.sqlite01

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class miSQLiteHelper(context: Context) : SQLiteOpenHelper(
    context, "amigos.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val ordenCreacion = "CREATE TABLE amigos " +
                "(_id INTEGER PRIMARY KEY," +
                "nombre TEXT, apellidouno TEXT, apellidodos TEXT, email TEXT, telefono INTEGER)"
        db!!.execSQL(ordenCreacion)
    }

    override fun onUpgrade(db: SQLiteDatabase?,
               oldVersion: Int, newVersion: Int) {
        val ordenBorrado = "DROP TABLE IF EXISTS amigos"
        db!!.execSQL(ordenBorrado)
        onCreate(db)
    }

    fun anyadirDato(nick: Int, nombre: String, apeluno: String, apeldos: String, email: String, tlf: Int) { //
        val datos = ContentValues()
        datos.put("_id", nick)
        datos.put("nombre", nombre)
        datos.put("apellidouno", apeluno)
        datos.put("apellidodos", apeldos)
        datos.put("email", email)
        datos.put("telefono", tlf)

        val db = this.writableDatabase
        db.insert("amigos", null, datos)
        db.close()
    }

    fun borrarDato(id: Int) : Int {
        val args = arrayOf(id.toString())

        val db = this.writableDatabase
        val borrados = db.delete("amigos", "_id = ?", args)
        // db.execSQL("DELETE FROM amigos WHERE _id = ?", args)
        db.close()
        return borrados
    }

    fun modificarDato(nick: Int, nombre: String, apeluno: String, apeldos: String, email: String, tlf: Int) {
        val args = arrayOf(nick.toString())

        val datos = ContentValues()
        datos.put("_id", nick)
        datos.put("nombre", nombre)
        datos.put("apellidouno", apeluno)
        datos.put("apellidodos", apeldos)
        datos.put("email", email)
        datos.put("telefono", tlf)

        val db = this.writableDatabase
        db.update("amigos", datos, "_id = ?", args)
        db.close()
    }
}