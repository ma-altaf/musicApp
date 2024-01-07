package com.example.musicApp.controller;

import com.example.musicApp.dto.ArtistDto;
import com.example.musicApp.dto.ArtistListingDto;
import com.example.musicApp.enums.OrderEnum;
import com.example.musicApp.model.Artist;
import com.example.musicApp.service.ArtistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @PostMapping("/create")
    public Artist createArtist(@RequestBody Artist artist) {
        return artistService.signUp(artist);
    }

    @PostMapping("/login")
    public Artist loginArtist(@RequestBody Artist artist) {
        return artistService.login(artist);
    }

    @PostMapping("/updatePassword")
    public Artist updatePassword(
            @RequestParam("username") String username,
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword) {
        return artistService.updatePassword(username, oldPassword, newPassword);
    }

    @PostMapping("/updateImg/{id}")
    public ResponseEntity<byte[]> updateImg(
            @PathVariable Integer id,
            @RequestParam("imgFile")MultipartFile imgFile) throws IOException {
        return artistService.updateImg(id, imgFile);
    }

    @GetMapping("/get")
    public Iterable<Artist> getArtists(
            @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "" + Integer.MAX_VALUE) Integer pageSize,
            @RequestParam(value = "orderBy", defaultValue = "username") String orderBy,
            @RequestParam(value = "order", defaultValue = "ASC") OrderEnum order
    ) {
        return artistService.getArtists(pageNo, pageSize, orderBy, order);
    }

    @GetMapping("/get/{id}")
    public ArtistDto getArtist(@PathVariable Integer id) {
        return artistService.getArtistById(id);
    }

    @PostMapping("/search/{query}")
    public Iterable<ArtistListingDto> searchArtist(@PathVariable String query) {
        return artistService.searchArtists(query);
    }

}
