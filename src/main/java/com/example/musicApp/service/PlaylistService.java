package com.example.musicApp.service;

import com.example.musicApp.dto.PlaylistDto;
import com.example.musicApp.enums.OrderEnum;
import com.example.musicApp.model.Playlist;
import com.example.musicApp.model.Song;

public interface PlaylistService {
    Playlist addPlaylist(String ownerName, String name);
    Playlist getPlaylist(Integer id);
    Iterable<PlaylistDto> getArtistPlaylists(String username);
    Playlist addSongToPlaylist(Integer playlistId, String songId);
}
