package edu.northeastern.cs5200.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs5200.entities.Playlist;
import edu.northeastern.cs5200.entities.Song;
import edu.northeastern.cs5200.repositories.PlaylistRepository;
import edu.northeastern.cs5200.repositories.SongRepository;

@RestController
public class PlaylistService {
	
	@Autowired
	private PlaylistRepository playlistRepository;
	
	@Autowired
	private SongRepository songRepository;
	
	@PostMapping("/api/playlist")
	public Playlist createPlaylist(@RequestBody Playlist playlist) {
		return playlistRepository.save(playlist);
		
	}
	
	@GetMapping("/api/playlist/{id}")
	public Playlist findPlaylistById(@PathVariable("id") int id) {
		Optional<Playlist> playlist =  playlistRepository.findById(id);
		if(playlist != null) {
			return playlist.get();
		}
		else {
			return null;
		}
	}
	
	@GetMapping("/api/playlist")
	public List<Playlist> findAllPlaylists(){
		return (List<Playlist>) playlistRepository.findAll();
	}

	@PutMapping("/api/playlist/{playlistId}/song/{songId}")
	public Playlist addSongToPlaylist(@PathVariable("playlistId") int playlistId, @PathVariable("songId") int songId) {
		
		Playlist playlist = playlistRepository.findById(playlistId).get();
		Song song = songRepository.findById(songId).get();
		if(playlist != null && song != null) {
			playlist.addSongToPlaylist(song);
			return playlistRepository.save(playlist);
		}
		System.out.println("Either playlist or song is NULL");
		return null;
		
	}
	
	@DeleteMapping("/api/playlist/{playlistId}/song/{songId}")
	public Playlist removeSongFromPlaylist(@PathVariable("playlistId") int playlistId, @PathVariable("songId") int songId) {
		
		Playlist playlist = playlistRepository.findById(playlistId).get();
		Song song = songRepository.findById(songId).get();
		if(playlist != null && song != null) {
			playlist.removeSongFromPlaylist(song);
			return playlistRepository.save(playlist);
		}
		System.out.println("Either playlist or song is NULL");
		return null;
	}
}