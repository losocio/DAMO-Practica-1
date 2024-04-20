package com.example.practica1alvarodeocio.model

import org.w3c.dom.ProcessingInstruction

class Receta (
    var id: Int,
    var name: String,
    var image: String,
    var difficulty: String,
    //var caloriesPerServing: Int,
    var rating: Double,
    //var mealType: String,
    var ingredients: String,
    var instructions: String
) { }