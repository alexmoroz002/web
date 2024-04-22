package ru.webitmo.soundstats.playlists.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.time.Instant
import java.util.Date

@Entity
@Table(name = "playlists")
data class Playlist (
    @Id
    var id : String,
    var name : String,
    @Column(name = "user_id")
    var userId : String,
    var description : String = "",
    var date : Date = Date.from(Instant.now()),
    @ManyToMany(cascade = [CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST])
    @JoinTable(name = "playlists_tracks", joinColumns = [JoinColumn(name = "playlist_id")],
        inverseJoinColumns = [JoinColumn(name = "track_id")])
    var tracks : Set<Track> = emptySet(),
)