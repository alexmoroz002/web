package ru.webitmo.soundstats.playlists.entities

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "tracks")
data class Track (
    @Id
    var id : String,
    var name : String,
    var cover : String?
)