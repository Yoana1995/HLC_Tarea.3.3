package com.ncabanes.sqlite01

import android.app.Notification
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cursoradapter.widget.CursorAdapter
import com.ncabanes.sqlite01.databinding.ActivityListaBinding
import com.ncabanes.sqlite01.databinding.ActivityMainBinding
import com.ncabanes.sqlite01.databinding.ItemListviewBinding
import java.util.*
import android.content.Intent as Intent

class ActivityLista : AppCompatActivity() {

    lateinit var binding: ActivityListaBinding
    lateinit var amigosDBHelper: miSQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityListaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        amigosDBHelper = miSQLiteHelper(this)

        val db : SQLiteDatabase = amigosDBHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM amigos",
            null)

        val adaptador = CursorAdapterListView(this, cursor)
        binding.lvDatos.adapter = adaptador
        db.close()
    }

    inner class CursorAdapterListView(context: Context, cursor: Cursor) :
        CursorAdapter(context, cursor, FLAG_REGISTER_CONTENT_OBSERVER) {

        override fun newView(context: Context?,
                             cursor: Cursor?, parent: ViewGroup?): View {
            val inflater = LayoutInflater.from(context)
            return inflater.inflate(R.layout.item_listview,
                parent, false )
        }

        override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
            val bindingItems = ItemListviewBinding.bind(view!!)
            bindingItems.tvItemId.text = cursor!!.getString(0)
            bindingItems.tvItemNombre.text = cursor!!.getString(1)
            bindingItems.tvItemApellido1.text = cursor!!.getString(2)
            bindingItems.tvItemApellido2.text = cursor!!.getString(3)
            bindingItems.tvItemEmail.text = cursor!!.getString(4)
            bindingItems.tvItemTlf.text = cursor!!.getString(5)

            view.setOnClickListener {
                val x = Intent(this@ActivityLista, MainActivity::class.java)
                x.putExtra("dato", bindingItems.tvItemId.text)
                x.putExtra("dato1", bindingItems.tvItemNombre.text)
                x.putExtra("dato2", bindingItems.tvItemApellido1.text)
                x.putExtra("dato3", bindingItems.tvItemApellido2.text)
                x.putExtra("dato4", bindingItems.tvItemEmail.text)
                x.putExtra("dato5", bindingItems.tvItemTlf.text)
                startActivity(x)
            }

        }

    }
}