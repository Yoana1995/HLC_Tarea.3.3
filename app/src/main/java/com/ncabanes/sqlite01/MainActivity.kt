package com.ncabanes.sqlite01

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.ncabanes.sqlite01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var amigosDBHelper: miSQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        amigosDBHelper = miSQLiteHelper(this)

        val nombres = arrayOf("Yoana","Marta","Sofia","Laila","Manuel","Carlos","Antonio","Jessica","Luis","Fran","Sandra")
        val apellidouno = arrayOf("Morgado","Martin","Mora","Prieto","Ramires","Santana","Ruiz","Tinoco","Gonzales","Rodriguez","Camacho")
        val apellidodos = arrayOf("Camacho","Rodriguez","Gonzales","Tinoco","Lopez","Muriel","Prieto","Martin","Mora","Martin","Morgado")
        val email = arrayOf("Yoana@ejemplo.com","Marta@ejemplo.com","Sofia@ejemplo.com","Laila@ejemplo.com","Manuel@ejemplo.com","Carlos@ejemplo.com","Antonio@ejemplo.com","Jessica@ejemplo.com","Luis@ejemplo.com","Antoinio@ejemplo.com","Sandra@ejemplo.com")
        val tlf = arrayOf("625789635","965875236","254896356","632487596","658749236","965784589","951245863","632145897","987456321","965874123","652315968")
        for (posicion in nombres.indices) {
            amigosDBHelper.anyadirDato(
                posicion.toInt(),
                nombres.get(posicion).toString(),
                apellidouno.get(posicion).toString(),
                apellidodos.get(posicion).toString(),
                email.get(posicion).toString(),
                tlf.get(posicion).toString().toInt()
            )
        }

        binding.btConsultar.setOnClickListener {
            var nick = binding.etId.text
            if (nick.isNotBlank()) {
                val args = arrayOf(nick.toString())
                val db : SQLiteDatabase = amigosDBHelper.readableDatabase
                val cursor = db.rawQuery(
                    "SELECT * FROM amigos WHERE _id = ?", args)
                borrar()
                if (cursor.moveToFirst()) {
                    do {
                        binding.etId.append(cursor.getInt(0).toString())
                        binding.etNombre.append(cursor.getString(1).toString())
                        binding.etApe1.append(cursor.getString(2).toString())
                        binding.etApe2.append(cursor.getString(3).toString())
                        binding.etEmail.append(cursor.getString(4).toString())
                        binding.etTlf.append(cursor.getString(5).toString())
                    } while (cursor.moveToNext())
                }
            }else{
                Toast.makeText(this, "Debe escribir un Nick", Toast.LENGTH_LONG).show()
            }

        }

        binding.btConsultartlf.setOnClickListener {
            var tlf = binding.etTlf.text
            if (tlf.isNotBlank()) {
                val args = arrayOf(tlf.toString())
                val db : SQLiteDatabase = amigosDBHelper.readableDatabase
                val cursor = db.rawQuery(
                    "SELECT * FROM amigos WHERE telefono = ?", args)
                borrar()
                if (cursor.moveToFirst()) {
                    do {
                        binding.etId.append(cursor.getInt(0).toString())
                        binding.etNombre.append(cursor.getString(1).toString())
                        binding.etApe1.append(cursor.getString(2).toString())
                        binding.etApe2.append(cursor.getString(3).toString())
                        binding.etEmail.append(cursor.getString(4).toString())
                        binding.etTlf.append(cursor.getString(5).toString())
                    } while (cursor.moveToNext())
                }
            }else{
                Toast.makeText(this, "Debe escribir un número de teléfono", Toast.LENGTH_LONG).show()
            }

        }

        binding.btBorrar.setOnClickListener {

            var cantidad = 0

            if (binding.etId.text.isNotBlank()) {
                cantidad = amigosDBHelper.borrarDato(
                        binding.etId.text.toString().toInt())
                borrar()
            }
            else {
                Toast.makeText(this,
                        "Debe introducir un Nick",
                        Toast.LENGTH_LONG).show()
            }

        }

        binding.btModificar.setOnClickListener {
            if (binding.etId.text.isNotBlank() &&
                    binding.etNombre.text.isNotBlank()){
                amigosDBHelper.modificarDato(
                        binding.etId.text.toString().toInt(),
                        binding.etNombre.text.toString(),
                        binding.etApe1.text.toString(),
                        binding.etApe2.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etTlf.text.toString().toInt()
                )

                borrar()

                Toast.makeText(this, "Modificado",
                        Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this,
                        "Los campos Nick y Nombre son obligatorios",
                        Toast.LENGTH_LONG).show()
            }
        }

        binding.btConsultarLV.setOnClickListener {
            val intentListView = Intent(this, ActivityLista::class.java)
            startActivity(intentListView)
        }

        val dato = intent.getStringExtra("dato").toString()
        if (dato != "null") {
            findViewById<TextView>(R.id.etId).text = dato
            findViewById<TextView>(R.id.etNombre).text = intent.getStringExtra("dato1").toString()
            findViewById<TextView>(R.id.etApe1).text = intent.getStringExtra("dato2").toString()
            findViewById<TextView>(R.id.etApe2).text = intent.getStringExtra("dato3").toString()
            findViewById<TextView>(R.id.etEmail).text = intent.getStringExtra("dato4").toString()
            findViewById<TextView>(R.id.etTlf).text = intent.getStringExtra("dato5").toString()
        }
    }
    fun  borrar(){
        binding.etId.text.clear()
        binding.etNombre.text.clear()
        binding.etApe1.text.clear()
        binding.etApe2.text.clear()
        binding.etEmail.text.clear()
        binding.etTlf.text.clear()
    }
}