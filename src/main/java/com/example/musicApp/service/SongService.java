package com.example.musicApp.service;

import com.example.musicApp.model.Song;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SongService {
    Song addSong(String title, MultipartFile imgFile, MultipartFile audioFile) throws IOException;

    Iterable<Song> getSongs();

    Song getSong(Integer id);
}
