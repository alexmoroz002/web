package ru.webitmo.soundstats.playlists

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ru.webitmo.soundstats.playlists.entities.Playlist
import ru.webitmo.soundstats.playlists.entities.Track
import ru.webitmo.soundstats.spotify.SpotifyService
import ru.webitmo.soundstats.spotify.dto.PlaylistInfoDto
import ru.webitmo.soundstats.spotify.dto.TrackDto

@Service
class PlaylistsService(private val spotifyService: SpotifyService, private val repo: PlaylistsRepository) {
    suspend fun createWorldPlaylist() : ResponseEntity<String> {
        val user = SecurityContextHolder.getContext().authentication.name
        val topTracks = spotifyService.getWorldTop(5).items.map { it.track }
        val recs = spotifyService.getRecommendations(20, tracks = topTracks.take(1))
        val tracks = recs.tracks.map { Track(it.id) }
        val playlistDto = spotifyService.createPlaylist(user, PlaylistInfoDto("World Top Recommendations"))
        spotifyService.addItemsToPlaylist(playlistDto, recs.tracks)
        withContext(Dispatchers.IO) {
            val existingPlaylist = repo.findById(playlistDto.id)
            var playlist = Playlist(playlistDto.id, playlistDto.name, user)
            if (existingPlaylist.isPresent) {
                playlist = existingPlaylist.get()
            }
            playlist.tracks = tracks.toSet()
            repo.save(playlist)
        }
        return ResponseEntity(playlistDto.url, HttpStatus.OK)
    }

    suspend fun getPlaylists(user : String) : List<Playlist> {
        return withContext(Dispatchers.IO) {
            repo.getAllByUserId(user)
        }
    }

    suspend fun restorePlaylist(id : String) {
        val user = SecurityContextHolder.getContext().authentication.name
        val playlist = repo.findById(id)
        if (playlist.isEmpty)
            return
        val existingPlaylist = playlist.get()
        val playlistDto = spotifyService.createPlaylist(user, PlaylistInfoDto("Restored ${existingPlaylist.name}", "Restored playlist from ${existingPlaylist.date}"))
        val tracks = existingPlaylist.tracks.map { TrackDto(id = it.id) }
        spotifyService.addItemsToPlaylist(playlistDto, tracks)
    }

}