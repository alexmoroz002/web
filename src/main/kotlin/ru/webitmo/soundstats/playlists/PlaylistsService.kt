package ru.webitmo.soundstats.playlists

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import ru.webitmo.soundstats.playlists.algorithms.PlaylistCreation
import ru.webitmo.soundstats.playlists.dto.MessageResponse
import ru.webitmo.soundstats.playlists.entities.Playlist
import ru.webitmo.soundstats.playlists.entities.Track
import ru.webitmo.soundstats.spotify.SpotifyService
import ru.webitmo.soundstats.spotify.dto.PlaylistDto
import ru.webitmo.soundstats.spotify.dto.PlaylistInfoDto
import ru.webitmo.soundstats.spotify.dto.TrackDto

@Service
class PlaylistsService(private val spotifyService: SpotifyService, private val repo: PlaylistsRepository, private val algos : Map<String, PlaylistCreation>) {

    suspend fun createPlaylist(algoName : String) : ResponseEntity<MessageResponse> {
        val user = SecurityContextHolder.getContext().authentication.name

        val algorithm = algos.getOrDefault(algoName, null)
            ?: return ResponseEntity(MessageResponse(HttpStatus.NOT_FOUND.value(), "Unknown algorithm $algoName"), HttpStatus.NOT_FOUND)
        val (name, recs) = algorithm.execute()
        val tracks = recs.tracks.map { Track(it.id, it.name, it.album?.images?.first()?.url) }
        val playlistDto = spotifyService.createPlaylist(user, PlaylistInfoDto(name, "SoundStats recommendations playlist"))
        spotifyService.addItemsToPlaylist(playlistDto, recs.tracks)
        withContext(Dispatchers.IO) {
            persistPlaylist(user, playlistDto, tracks)
        }
        return ResponseEntity(MessageResponse(HttpStatus.CREATED.value(), playlistDto.url), HttpStatus.OK)
    }

    private fun persistPlaylist(user : String, playlistDto : PlaylistDto, tracks : List<Track>) {
        val existingPlaylist = repo.findById(playlistDto.id)
        var playlist = Playlist(playlistDto.id, playlistDto.name, user, playlistDto.description)
        if (existingPlaylist.isPresent) {
            playlist = existingPlaylist.get()
        }
        playlist.tracks = tracks.toSet()
        repo.save(playlist)
    }

    suspend fun getPlaylists(user : String) : List<Playlist> {
        return withContext(Dispatchers.IO) {
            repo.getAllByUserId(user)
        }
    }

    suspend fun restorePlaylist(id : String) : ResponseEntity<MessageResponse> {
        val user = SecurityContextHolder.getContext().authentication.name
        val playlist = repo.findById(id)
        if (playlist.isEmpty)
            return ResponseEntity(MessageResponse(HttpStatus.NOT_FOUND.value(), "Playlist not found"), HttpStatus.NOT_FOUND)
        val existingPlaylist = playlist.get()
        val playlistDto = spotifyService.createPlaylist(user, PlaylistInfoDto("Restored ${existingPlaylist.name}", "Restored playlist from ${existingPlaylist.date}"))
        val tracks = existingPlaylist.tracks.map { TrackDto(id = it.id) }
        spotifyService.addItemsToPlaylist(playlistDto, tracks)
        return ResponseEntity(MessageResponse(HttpStatus.CREATED.value(), "Playlist restored"), HttpStatus.CREATED)
    }
}