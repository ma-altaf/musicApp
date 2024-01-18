package com.example.musicApp.controller;

import com.example.musicApp.dto.PlaylistDto;
import com.example.musicApp.model.Playlist;
import com.example.musicApp.service.PlaylistService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/playlist", method = {RequestMethod.GET})
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping("/add")
    private Playlist addPlaylist(Principal principal, @RequestBody PlaylistDto playlistDto) {
        return playlistService.addPlaylist(principal.getName(), playlistDto.title());
    }

    @GetMapping("/get/{id}")
    private Playlist getPlaylist(@PathVariable Integer id) {
        return playlistService.getPlaylist(id);
    }

    @GetMapping("/get")
    private Iterable<PlaylistDto> getArtistPlaylist(Principal principal) {
        return playlistService.getArtistPlaylists(principal.getName());
    }

    @PostMapping("/addSong/{playlistId}/{songId}")
    private Playlist addSongToPlaylist(@PathVariable Integer playlistId, @PathVariable String songId){
        return playlistService.addSongToPlaylist(playlistId, songId);
    }
}
