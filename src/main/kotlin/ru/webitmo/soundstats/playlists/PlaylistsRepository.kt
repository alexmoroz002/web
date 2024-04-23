package ru.webitmo.soundstats.playlists

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.webitmo.soundstats.playlists.entities.Playlist

@Repository
interface PlaylistsRepository : JpaRepository<Playlist, String> {
    @Query("select p from Playlist p where p.userId=?1")
    fun getAllByUserId(user : String) : List<Playlist>
}