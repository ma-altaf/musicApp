package com.example.musicApp.service.impl;

import com.example.musicApp.model.Artist;
import com.example.musicApp.model.Playlist;
import com.example.musicApp.model.PlaylistSong;
import com.example.musicApp.model.Song;
import com.example.musicApp.repository.ArtistRepository;
import com.example.musicApp.repository.PlaylistRepository;
import com.example.musicApp.repository.SongRepository;
import com.example.musicApp.service.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistRepository playlistRepository;
    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository, ArtistRepository artistRepository, SongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
    }


    @Override
    public Playlist addPlaylist(String ownerName, String name) {
        Artist artist = artistRepository.findByUsername(ownerName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Playlist playlist = new Playlist();
        playlist.setTitle(name);

        artist.getPlaylists().add(playlist);

        artistRepository.save(artist);

        return playlist;
    }

    @Override
    public Iterable<Playlist> getArtistPlaylists(String username) {
        Artist artist = artistRepository.findByUsername(username).orElse(null);

        if (artist == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return artist.getPlaylists();
    }

    @Override
    public Playlist addSongToPlaylist(Integer playlistId, String songId) {
        // get the song
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // add song to playlist
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        PlaylistSong playlistSong = new PlaylistSong();
        playlistSong.setSong(song);
        playlistSong.setPosition(playlist.getSongs().size());

        playlist.getSongs().add(playlistSong);

        return playlistRepository.save(playlist);
    }

    @Override
    public Playlist getPlaylist(Integer id) {
        return playlistRepository.findById(id).orElse(null);
    }
}
