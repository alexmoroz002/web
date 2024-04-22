package ru.webitmo.soundstats.playlists

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ru.webitmo.soundstats.playlists.entities.Playlist
import ru.webitmo.soundstats.playlists.entities.Track
import ru.webitmo.soundstats.spotify.SpotifyService
import ru.webitmo.soundstats.spotify.dto.PlaylistInfoDto
import java.time.LocalDate

@Service
class PlaylistsService(private val spotifyService: SpotifyService, private val repo: PlaylistsRepository) {
    suspend fun createWorldPlaylist() : String {
        val user = SecurityContextHolder.getContext().authentication.name
        val topTracks = spotifyService.getWorldTop(5).items.map { it.track }
        val recs = spotifyService.getRecommendations(20, tracks = topTracks.take(1))
        val tracks = recs.tracks.map { Track(it.id) }
        val playlistDto = spotifyService.createPlaylist(user, PlaylistInfoDto(LocalDate.now().toString()))
        spotifyService.addItemsToPlaylist(playlistDto, recs.tracks)
        withContext(Dispatchers.IO) {
            val existingPlaylist = repo.findById(playlistDto.id)
            var playlist = Playlist(playlistDto.id, playlistDto.name, "42add")
            if (existingPlaylist.isPresent) {
                playlist = existingPlaylist.get()
            }
            playlist.tracks = tracks.toSet()
            repo.save(playlist)
        }
        return "A"
    }


}