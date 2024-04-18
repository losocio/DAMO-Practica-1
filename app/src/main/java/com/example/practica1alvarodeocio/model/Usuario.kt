package com.example.practica1alvarodeocio.model

import java.io.Serializable

class Usuario(
    var nombre: String,
    var correo: String,
    var pass: String
) : Serializable {
}