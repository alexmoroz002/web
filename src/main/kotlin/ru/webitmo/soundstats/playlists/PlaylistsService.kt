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
import ru.webitmo.soundstats.spotify.dto.PlaylistDto
import ru.webitmo.soundstats.spotify.dto.PlaylistInfoDto
import ru.webitmo.soundstats.spotify.dto.TrackDto
import ru.webitmo.soundstats.statistics.StatisticsService

@Service
class PlaylistsService(private val spotifyService: SpotifyService, private val repo: PlaylistsRepository) {
    suspend fun createWorldTopPlaylist() : ResponseEntity<String> {
        val user = SecurityContextHolder.getContext().authentication.name
        val topTracks = spotifyService.getWorldTop(20).items.map { it.track }
        val recs = spotifyService.getRecommendations(20, tracks = topTracks.shuffled().take(5))
        val tracks = recs.tracks.map { Track(it.id) }
        val playlistDto = spotifyService.createPlaylist(user, PlaylistInfoDto("World Top", "SoundStats recommendations playlist"))
        spotifyService.addItemsToPlaylist(playlistDto, recs.tracks)
        withContext(Dispatchers.IO) {
            persistPlaylist(user, playlistDto, tracks)
        }
        return ResponseEntity(playlistDto.url, HttpStatus.OK)
    }

    suspend fun createFeaturesPlaylist() : ResponseEntity<String> {
        val user = SecurityContextHolder.getContext().authentication.name
        val userTopTracks = spotifyService.getUserTopTracks(50).items
        val tracksFeatures = spotifyService.getAverageFeatures(userTopTracks)
        val recs = spotifyService.getRecommendations(20, tracks = userTopTracks.take(20).shuffled().take(5), features = tracksFeatures)
        val tracks = recs.tracks.map { Track(it.id) }
        val playlistDto = spotifyService.createPlaylist(user, PlaylistInfoDto("Personal Top", "SoundStats recommendations playlist"))
        spotifyService.addItemsToPlaylist(playlistDto, recs.tracks)
        withContext(Dispatchers.IO) {
            persistPlaylist(user, playlistDto, tracks)
        }
        return ResponseEntity(playlistDto.url, HttpStatus.OK)
    }

    suspend fun createArtistsPlaylist() : ResponseEntity<String> {
        val user = SecurityContextHolder.getContext().authentication.name
        val topArtists = spotifyService.getUserTopArtists(10).items.shuffled().take(5)
        val recs = spotifyService.getRecommendations(20, artists = topArtists)
        val tracks = recs.tracks.map { Track(it.id) }
        val playlistDto = spotifyService.createPlaylist(user, PlaylistInfoDto("Artists Top", "SoundStats recommendations playlist"))
        spotifyService.addItemsToPlaylist(playlistDto, recs.tracks)
        withContext(Dispatchers.IO) {
            persistPlaylist(user, playlistDto, tracks)
        }
        return ResponseEntity(playlistDto.url, HttpStatus.OK)
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