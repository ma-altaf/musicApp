package com.example.musicApp.controller;

import com.example.musicApp.dto.SongUploadDto;
import com.example.musicApp.dto.SongListingDto;
import com.example.musicApp.enums.OrderEnum;
import com.example.musicApp.model.Song;
import com.example.musicApp.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping(value = "/song", method = {RequestMethod.GET})
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @PostMapping("/add")
    public Song addSong(@ModelAttribute SongUploadDto songUploadDto, Principal principal) throws IOException {
        return songService.addSong(principal.getName(), songUploadDto);
    }

    @GetMapping("/get")
    public Iterable<Song> getSongs(
            @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "" + Integer.MAX_VALUE) Integer pageSize,
            @RequestParam(value = "orderBy", defaultValue = "released") String orderBy,
            @RequestParam(value = "order", defaultValue = "ASC") OrderEnum order
    ) {
        return songService.getSongs(pageNo, pageSize, orderBy, order);
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
