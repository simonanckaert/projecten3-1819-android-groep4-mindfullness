package com.groep4.mindfulness.model

data class Sessie(val naam: String, val beschrijving: String, val oefeningen: ArrayList<Oefening>, val vergrendeld: Boolean)
data class Oefening(val naam: String, val beschrijving: String)
data class Message(val titel: String, val onderwerp: String, val afzender: String, val  tekst : String)
