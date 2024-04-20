package com.example.practica1alvarodeocio.ui

import android.content.Intent
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
        binding.textoSuperior.text = "RECETARIO"

        // Acciones a realizar sobre los elementos del RecyclerView
        //acciones()

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

                /* TODO: pruebo sin GSON, da error
                // Convierto el JSON a un objeto de la clase Receta
                val recetaOBJ: Receta = Gson().fromJson(receta.toString(), Receta::class.java)
                 */

                val id = receta.getInt("id")
                val name = receta.getString("name")
                val image = receta.getString("image")
                val difficulty = receta.getString("difficulty")
                val rating = receta.getDouble("rating")

                // Convierto los arrays de ingredientes e instrucciones a strings
                val ingredientsArray = receta.getJSONArray("ingredients")
                val ingredientsList = List(ingredientsArray.length()) { ingredientsArray.getString(it) }
                val ingredients = ingredientsList.joinToString("\n")

                val instructionsArray = receta.getJSONArray("instructions")
                val instructionsList = List(instructionsArray.length()) { instructionsArray.getString(it) }
                val instructions = instructionsList.joinToString("\n")

                // Creo un objeto de la clase Receta
                val recetaOBJ: Receta = Receta(id, name, image, difficulty, rating, ingredients, instructions)

                // Añado recetaOBJ a la lista del adaptador
                adaptadorReceta.addReceta(recetaOBJ)

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


    /*
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
    */

    fun instancias() {
        adaptadorReceta = AdaptadorReceta(this)
    }

    fun persoAdaptadores() {
        binding.recyclerRecetas.adapter = adaptadorReceta
        binding.recyclerRecetas.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.recyclerRecetas.layoutManager =
                GridLayoutManager(this, 2)
        }
    }

}