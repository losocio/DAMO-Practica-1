package com.example.practica1alvarodeocio.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.practica1alvarodeocio.adapters.AdaptadorReceta
import com.example.practica1alvarodeocio.databinding.ActivityMainBinding
import com.example.practica1alvarodeocio.model.Receta
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adaptadorReceta: AdaptadorReceta
    //private lateinit var uidCurrentUser: String
    //private lateinit var nombre: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Intancio lo neceario
        instancias()
        persoAdaptadores()

        // Hago la peticion
        peticionJSON()

        //this.nombre = intent.getStringExtra("correo")!!
        //this.uidCurrentUser = intent.getStringExtra("uid")!!
        binding.textoSuperior.text = "Recetario"

        // Acciones a realizar sobre los elementos del RecyclerView
        acciones()

    }

    private fun peticionJSON() {
        // Definir la peticion
        val url = "https://dummyjson.com/recipes"
        val peticion: JsonObjectRequest = JsonObjectRequest(url, {
            // Guardo la respuesta en un objeto JSON
            val recetas: JSONArray = it.getJSONArray("recipes")

            // Para cada receta en le objeto JSON
            for (i in 0..<recetas.length()) {
                val receta: JSONObject = recetas.getJSONObject(i)

                // Convierto el JSON a un objeto de la clase Receta
                val recetaOBJ: Receta = Gson().fromJson(receta.toString(), Receta::class.java)

                // Añado recetaOBJ a la lista del adaptador
                adaptadorReceta.addReceta(recetaOBJ)

                /* Usamos Gson para convertir el JSON a un objeto de la clase Producto
                val id = producto.getInt("id")
                val title = producto.getString("title")
                val description = producto.getString("description")
                val category = producto.getString("category")
                val thumbnail = producto.getString("thumbnail")
                val price = producto.getDouble("price")
                val productoOBJ: Producto = Producto(id,title,description, thumbnail, category, price)
                */

                // Guardo un log con el id y el nombre de la receta
                Log.v("dats", "${recetaOBJ.id} ${recetaOBJ.name}")
            }
        }, {
            // En caso de error guardo un log de error
            Log.v("dats", "Error de conexion")
        })

        // Lanzar la peticion
        Volley.newRequestQueue(applicationContext).add(peticion)
    }

    // Acciones a realizar sobre los eementos del RecyclerView
    fun acciones() {
        binding.spinnerSeleccion.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                val selecccion: Marca = parent!!.adapter.getItem(position) as Marca

                // filtrar la lista -> DATASET OK
                // cambiar la lista -> ADAPTADOR
                // adaptadorModelo.cambiarLista(DataSet.getAllModelos(selecccion.nombre))


                Snackbar.make(
                    binding.root,
                    "${selecccion.nombre} ${selecccion.calidad}",
                    Snackbar.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    fun instancias() {
        adaptadorReceta = AdaptadorReceta(this)
    }


    fun persoAdaptadores() {
        // junta parte grafica con parte logica
        binding.spinnerSeleccion.adapter = adapterSpinner;
        // muestra el desplegable de forma visible
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.recyclerModelos.adapter = adaptadorReceta
        binding.recyclerModelos.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.recyclerModelos.layoutManager =
                GridLayoutManager(this, 2)
        }

        //GridLayoutManager(this,2)

        //LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

}