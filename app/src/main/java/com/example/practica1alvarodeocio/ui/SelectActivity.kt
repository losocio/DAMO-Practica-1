package com.example.practica1alvarodeocio.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.practica1alvarodeocio.R

class SelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        val textViewNombre = findViewById<TextView>(R.id.nombre)
        val imageViewImagen = findViewById<ImageView>(R.id.imagen)
        val textViewIngredientes = findViewById<TextView>(R.id.ingredientes)
        val textViewPasos = findViewById<TextView>(R.id.pasos)

        val intent = intent
        val nombre = intent?.getStringExtra("nombre")
        val imagen = intent?.getStringExtra("imagen")
        val ingredientes = intent?.getStringExtra("ingredientes")
        val pasos = intent?.getStringExtra("pasos")

        textViewNombre.text = nombre

        Glide.with(this).load(imagen)
            .placeholder(R.drawable.meal).into(imageViewImagen)

        textViewIngredientes.text = ingredientes
        textViewPasos.text = pasos

    }
}