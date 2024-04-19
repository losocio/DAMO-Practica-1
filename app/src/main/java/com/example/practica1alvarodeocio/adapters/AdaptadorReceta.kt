package com.example.practica1alvarodeocio.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practica1alvarodeocio.R
import com.example.practica1alvarodeocio.model.Receta

class AdaptadorReceta(var context: Context) : RecyclerView.Adapter<AdaptadorReceta.MyHolder>() {

    // Lista de las recetas
    var lista: ArrayList<Receta> = ArrayList()

    // Definir cada uno de los elementos de la fila
    class MyHolder(item: View) : RecyclerView.ViewHolder(item) {
        var imagen: ImageView = item.findViewById(R.id.imagenFila)
        var titulo: TextView = item.findViewById(R.id.nombreFila)
        var dificultad: TextView = item.findViewById(R.id.dificultadFila)
        var puntuacion: TextView = item.findViewById(R.id.puntuacionFila)
    }

    // Creara la plantilla asociada
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {

        val vista: View = LayoutInflater.from(context).inflate(
            R.layout.receta_modelo,
            parent, false
        )
        return MyHolder(vista)
    }

    // Cuantos elementos tengo pintar -> LOS QUE HAY EN LA LISTA A REPRESENTAR
    override fun getItemCount(): Int {
        return lista.size;
    }

    // Asociar el holder (XML) con datos de la LISTA
    override fun onBindViewHolder(holder: MyHolder, position: Int) {

        val elemento = lista[position]

        // Asocio Nombre, Dificultad y Puntuación
        holder.titulo.text = elemento.name
        holder.dificultad.text = elemento.difficulty
        holder.dificultad.text = elemento.rating.toString()

        // Asocio imagen con Glide
        Glide.with(context).load(elemento.image)
            .placeholder(R.drawable.user).into(holder.imagen)
    }

    fun addReceta(x: Receta) {
        lista.add(x)
        notifyItemInserted(lista.size - 1)
    }
}