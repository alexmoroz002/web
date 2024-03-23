package ru.webitmo.soundstats

class User (
    val dbId : Int = 1,
    val id : String = "",
    val name : String = "",
    val market : String = "",
    val imageUrl : String? = "",
    val accessToken : String = "",
    val refreshToken : String = ""
)