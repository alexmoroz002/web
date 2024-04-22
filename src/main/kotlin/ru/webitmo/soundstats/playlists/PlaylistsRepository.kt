package ru.webitmo.soundstats.playlists

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.webitmo.soundstats.playlists.entities.Playlist

@Repository
interface PlaylistsRepository : JpaRepository<Playlist, String> {
}