package com.example.practica1alvarodeocio.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practica1alvarodeocio.R
import com.example.practica1alvarodeocio.model.Receta
import com.example.practica1alvarodeocio.ui.SelectActivity

class AdaptadorReceta(var context: Context) : RecyclerView.Adapter<AdaptadorReceta.RecetaViewHolder>() {

    // Lista de las recetas
    var lista: ArrayList<Receta> = ArrayList()


    // Definir cada uno de los elementos de la fila
    inner class RecetaViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var imagen: ImageView = item.findViewById(R.id.imagenFila)
        var titulo: TextView = item.findViewById(R.id.nombreFila)
        var dificultad: TextView = item.findViewById(R.id.dificultadFila)
        var puntuacion: TextView = item.findViewById(R.id.puntuacionFila)

        val itemRecyclerView: ConstraintLayout = item.findViewById(R.id.itemRecyclerView)
    }

    // Creara la plantilla asociada
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecetaViewHolder {

        val vista: View = LayoutInflater.from(context).inflate(
            R.layout.receta_modelo,
            parent, false
        )
        return RecetaViewHolder(vista)
    }

    // Cuantos elementos tengo pintar -> LOS QUE HAY EN LA LISTA A REPRESENTAR
    override fun getItemCount(): Int {
        return lista.size;
    }

    // Asociar el holder (XML) con datos de la LISTA
    override fun onBindViewHolder(holder: RecetaViewHolder, position: Int) {

        val elemento = lista[position]

        // Asocio imagen con Glide
        Glide.with(context).load(elemento.image)
            .placeholder(R.drawable.meal).into(holder.imagen)

        // Asocio Nombre, Dificultad y Puntuación
        holder.titulo.text = elemento.name
        holder.dificultad.text = "Dificultad: ${elemento.difficulty}"
        holder.puntuacion.text = "Puntuación: ${elemento.rating}"
        //holder.puntuacion.text = "Ingredientes: ${elemento.ingredients}"

        //
        //val cont = holder.itemRecyclerView.context
        holder.itemRecyclerView.setOnClickListener {
            // Lanzar la actividad seleccionada
            val intent = Intent(it.context, SelectActivity::class.java)
            intent.putExtra("nombre", elemento.name)
            intent.putExtra("imagen", elemento.image)
            intent.putExtra("ingredientes", elemento.ingredients)
            intent.putExtra("pasos", elemento.instructions)

            it.context.startActivity(intent)
        }
    }

    // Añadir receta a la lista
    fun addReceta(x: Receta) {
        lista.add(x)
        notifyItemInserted(lista.size - 1)
    }
}