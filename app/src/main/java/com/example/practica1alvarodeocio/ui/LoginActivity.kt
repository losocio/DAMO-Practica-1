package com.example.practica1alvarodeocio.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.practica1alvarodeocio.R
import com.example.practica1alvarodeocio.databinding.ActivityLoginBinding
import com.example.practica1alvarodeocio.model.Usuario

class LoginActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private var usuario: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar layout
        binding = ActivityLoginBinding.inflate(layoutInflater)
        // Set a nuevo layout
        setContentView(binding.root)

        if (intent.getSerializableExtra("usuario")!=null){
            usuario = intent.getSerializableExtra("usuario") as Usuario
            binding.editCorreo.setText(usuario!!.correo)
            binding.editPass.setText(usuario!!.pass)
        }

        binding.botonLogin.setOnClickListener(this)
        binding.botonSignUp.setOnClickListener(this)

        /*
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        */

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            binding.botonLogin.id->{
                if(!binding.editCorreo.text.toString().isEmpty() &&
                    !binding.editPass.text.toString().isEmpty()){

                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.putExtra("correo", binding.editCorreo.text.toString())
                    startActivity(intent)

                }


            }
            binding.botonSignUp.id->{
                val intent = Intent(applicationContext, SignupActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}