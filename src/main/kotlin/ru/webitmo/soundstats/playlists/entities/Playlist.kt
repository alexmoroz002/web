package ru.webitmo.soundstats.playlists.entities

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "playlists")
data class Playlist (
    @Id
    var id : String,
    var name : String,
    @Column(name = "user_id")
    var userId : String,
    var description : String = "",
    var date : LocalDateTime = LocalDateTime.now(),
    @ManyToMany(cascade = [CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST])
    @JoinTable(name = "playlists_tracks", joinColumns = [JoinColumn(name = "playlist_id")],
        inverseJoinColumns = [JoinColumn(name = "track_id")])
    var tracks : Set<Track> = emptySet(),
) {
    val formattedDate : String get() {
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.YYYY")
        return date.format(formatter)
    }
}