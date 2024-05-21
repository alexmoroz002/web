package ru.webitmo.soundstats.playlists

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.webitmo.soundstats.playlists.entities.Track

@Repository
interface TracksRepository : JpaRepository<Track, String> {
}