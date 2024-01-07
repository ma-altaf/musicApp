package com.example.musicApp.controller;

import com.example.musicApp.dto.SongUploadDto;
import com.example.musicApp.dto.SongListingDto;
import com.example.musicApp.model.Song;
import com.example.musicApp.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/song")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping("/add")
    public Song addSong(@RequestBody SongUploadDto songUploadDto) throws IOException {
        return songService.addSong(songUploadDto);
    }

    @GetMapping("/get")
    public Iterable<Song> getSongs() {
        return songService.getSongs();
    }

    @GetMapping("/get/{id}")
    public Song getSongById(@PathVariable String id) {
        Song song = songService.getSong(id);
        if (song == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return song;
    }

    @GetMapping("/search/{query}")
    public Iterable<SongListingDto> searchSongs(@PathVariable String query) {
        return songService.searchSongs(query);
    }
}
