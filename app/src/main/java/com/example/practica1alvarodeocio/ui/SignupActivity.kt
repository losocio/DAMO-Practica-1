package com.example.practica1alvarodeocio.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practica1alvarodeocio.databinding.ActivitySignupBinding
import com.example.practica1alvarodeocio.model.Usuario
import com.google.android.material.snackbar.Snackbar

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar layout
        binding = ActivitySignupBinding.inflate(layoutInflater)
        // Set a nuevo layout
        setContentView(binding.root)

        if (!binding.editCorreo.text.toString().isEmpty()
            && !binding.editNombre.text.toString().isEmpty()
            && !binding.editPass.text.toString().isEmpty()
            && !binding.editPass2.text.toString().isEmpty()
            && (binding.editPass.text.toString()
                .equals(binding.editPass2.text.toString()))
        ) {
            // si todos los datos estan completos -> paso de pantalla

            val usuario =
                Usuario(
                    binding.editNombre.text.toString(),
                    binding.editCorreo.text.toString(),
                    binding.editPass.text.toString()
                )

            // Cambiar de activity, volver al login
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("usuario",usuario)
            startActivity(intent)

        } else {
            // si todos los datos estan completos pero las pass no coincida -> aviso
            // los datos no estan completos pero las pass no coincida -> aviso
            Snackbar.make(binding.root, "Fallo en el proceso", Snackbar.LENGTH_SHORT).show()
        }

    }
}