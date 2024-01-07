package com.example.musicApp.service;

import com.example.musicApp.dto.SongUploadDto;
import com.example.musicApp.dto.SongListingDto;
import com.example.musicApp.model.Song;

import java.io.IOException;

public interface SongService {
    Song addSong(SongUploadDto songUploadDto) throws IOException;

    Iterable<Song> getSongs();

    Song getSong(String id);

    Iterable<SongListingDto> searchSongs(String query);

}
