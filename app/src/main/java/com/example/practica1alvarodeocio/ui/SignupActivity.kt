package com.example.practica1alvarodeocio.ui

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.practica1alvarodeocio.databinding.ActivitySignupBinding
import com.example.practica1alvarodeocio.model.Usuario
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var authFirebase: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Instanciar
        instancias()

        binding.botonRegistro.setOnClickListener {

            // Si los campos no estan vacios y las pass coinciden
            if (!binding.editCorreo.text.toString().isEmpty()
                && !binding.editNombre.text.toString().isEmpty()
                && !binding.editPass.text.toString().isEmpty()
                && !binding.editPass2.text.toString().isEmpty()
                && (binding.editPass.text.toString()
                    .equals(binding.editPass2.text.toString()))
            ) {
                // si todos los datos estan completos -> paso de pantalla

                // Creo un objeto Usuario con los datos introducidos
                val usuario =
                    Usuario(
                        nombre = binding.editNombre.text.toString(),
                        correo = binding.editCorreo.text.toString(),
                        pass = binding.editPass.text.toString()
                    )

                // Creo un usuario en Firebase con los datos introducidos
                authFirebase.createUserWithEmailAndPassword(
                    usuario.correo,
                    usuario.pass
                ).addOnCompleteListener {
                    // Si el proceso es exitoso
                    if (it.isSuccessful) {
                        // Creo el nuevo usuario en la base de datos
                        firebaseDatabase.reference.child("usuarios")
                            .child(authFirebase.currentUser!!.uid).setValue(usuario)

                        // Muestro un mensaje de exito
                        Snackbar.make(
                            binding.root,
                            "Usuario registrado con exito",
                            Snackbar.LENGTH_SHORT
                        ).setAction("Volver a login") {
                            val intent = Intent(this, LoginActivity::class.java)
                            intent.putExtra("usuario",usuario)
                            startActivity(intent)
                        }.show()
                    // Si el proceso falla
                    } else {
                        // Muestro un mensaje de fallo
                        Snackbar.make(
                            binding.root,
                            "Fallo en el proceso",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }

            } else {
                // si todos los datos estan completos pero las pass no coincida -> aviso
                // los datos no estan completos pero las pass no coincida -> aviso
                Snackbar.make(binding.root, "Fallo en el proceso", Snackbar.LENGTH_SHORT).show()
            }

        }
    }

    // Instanciar
    private fun instancias() {
        authFirebase = FirebaseAuth.getInstance()
        // Ligo este proyecto con mi DB de Firebase
        firebaseDatabase =
            FirebaseDatabase.getInstance("https://practica1alvarodeocio-default-rtdb.europe-west1.firebasedatabase.app/")
    }
}

/* TODO: Old SignupActivity
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
*/
