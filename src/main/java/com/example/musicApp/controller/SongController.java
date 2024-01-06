package com.example.musicApp.controller;

import com.example.musicApp.model.Song;
import com.example.musicApp.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public Song addSong(@RequestPart("title") String title,
                        @RequestPart("imgFile")MultipartFile image,
                        @RequestPart("audioFile")MultipartFile audio) throws IOException {
        return songService.addSong(title, image, audio);
    }

    @GetMapping("/get")
    public Iterable<Song> getSong() {
        return songService.getSongs();
    }

    @GetMapping("/get/{id}")
    public Song getSongById(@PathVariable Integer id) {
        Song song = songService.getSong(id);
        if (song == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return song;
    }
}
