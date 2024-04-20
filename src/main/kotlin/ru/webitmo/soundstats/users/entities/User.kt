package ru.webitmo.soundstats.users.entities

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "users")
open class User(
    @Id
    open var spotifyId : String,
    open var country : String,
    open var isExplicitFiltered : Boolean,
    open var userName : String? = null,
    open var avatarUrl : String? = null,
    open var product : String? = null,
) : Serializable