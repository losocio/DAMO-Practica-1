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
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authFirebase: FirebaseAuth
    private var userAuth: FirebaseUser? = null
    private var usuario: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar y set layout
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Instacio
        instancias()

        // Si el intent trae un usuario lo guardo en usuario
        if (intent.getSerializableExtra("usuario")!=null){
            usuario = intent.getSerializableExtra("usuario") as Usuario
            binding.editCorreo.setText(usuario!!.correo)
            binding.editPass.setText(usuario!!.pass)
        }

        // Seteo los listeners
        binding.botonLogin.setOnClickListener(this)
        binding.botonSignUp.setOnClickListener(this)

    }

    // Funcion para instanciar lo que necesite instanciar
    fun instancias() {
        // Instancio FirebaseAuth, toma info del google-services.json y se conecta a Firebase
        authFirebase = FirebaseAuth.getInstance()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            // Si pulso Login
            binding.botonLogin.id -> {
                // Si los campos no estan vacios
                if (!binding.editCorreo.text.toString().isEmpty() &&
                    !binding.editPass.text.toString().isEmpty()
                ) {
                    // Hago login en Firebase
                    authFirebase.signInWithEmailAndPassword(
                        // Le paso email y pass
                        binding.editCorreo.text.toString(),
                        binding.editPass.text.toString()
                    ).addOnCompleteListener {
                        // Si es correcto paso a MainActivity
                        if (it.isSuccessful) {
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            intent.putExtra("correo", binding.editCorreo.text.toString())
                            intent.putExtra("uid", authFirebase.currentUser?.uid)
                            startActivity(intent)
                        } else {
                            // Si no es correcto muestro un mensaje
                            Snackbar.make(binding.root, "Fallo en los datos", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            // Si pulso el boton de registro
            binding.botonSignUp.id -> {

                // Paso a SignUpActivity para registrar nuevo usuario
                val intent = Intent(applicationContext, SignupActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }
}