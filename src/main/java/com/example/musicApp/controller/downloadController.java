package com.example.musicApp.controller;

import com.example.musicApp.model.Artist;
import com.example.musicApp.model.Song;
import com.example.musicApp.service.ArtistService;
import com.example.musicApp.service.SongService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("download")
public class downloadController {
    private final ArtistService artistService;
    private final SongService songService;

    public downloadController(ArtistService artistService, SongService songService) {
        this.artistService = artistService;
        this.songService = songService;
    }

    @GetMapping("artist/img/**")
    public ResponseEntity<byte[]> getArtistImgFile(HttpServletRequest request) throws IOException {
        String url = request.getRequestURL().toString().replaceAll("%20", " ");

        Artist artist = artistService.getArtistByImgUrl(url);
        File imgFile = new File(artist.getLocalImgUrl());

        return ResponseEntity.ok(Files.readAllBytes(imgFile.toPath()));
    }

    @GetMapping("song/img/**")
    public ResponseEntity<byte[]> getSongImgFile(HttpServletRequest request) throws IOException {
        String url = request.getRequestURL().toString().replaceAll("%20", " ");

        Song song = songService.getSongByImgUrl(url);
        File imgFile = new File(song.getLocalImgUrl());

        return ResponseEntity.ok(Files.readAllBytes(imgFile.toPath()));
    }

    @GetMapping("song/audio/**")
    public ResponseEntity<byte[]> getSongAudioFile(HttpServletRequest request) throws IOException {
        String url = request.getRequestURL().toString().replaceAll("%20", " ");

        Song song = songService.getSongByAudioUrl(url);
        File audioFile = new File(song.getLocalAudioUrl());

        // increment the listen counter
        songService.incrementListen(song);

        return ResponseEntity.ok(Files.readAllBytes(audioFile.toPath()));
    }
}
