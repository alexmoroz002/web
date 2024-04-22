package ru.webitmo.soundstats.playlists

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import ru.webitmo.soundstats.playlists.entities.Playlist
import ru.webitmo.soundstats.playlists.entities.Track
import ru.webitmo.soundstats.spotify.SpotifyService

@Service
class PlaylistsService(private val spotifyService: SpotifyService, private val repo: PlaylistsRepository) {
    suspend fun createWorldPlaylist(id : String) : String {
        val topTracks = spotifyService.getWorldTop(5).items.map { it.track }
        val recs = spotifyService.getRecommendations(20, tracks = topTracks.take(1))
        val tracks = recs.tracks.map { Track(it.id) }
        withContext(Dispatchers.IO) {
            val existingPlaylist = repo.findById(id)
            var playlist = Playlist(id, "12")
            if (!existingPlaylist.isEmpty) {
                playlist = existingPlaylist.get()
            }
            playlist.tracks = tracks.toSet()
            repo.save(playlist)
        }
        return "A"
    }


}