package ru.webitmo.soundstats

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Id
    var spotifyId : String,
    var country : String,
    var isExplicit : Boolean,
    var accessToken : String,
    var refreshToken : String,
    var name : String? = null,
    var avatarUrl : String? = null,
    var product : String? = null,
)