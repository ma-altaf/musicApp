package com.example.musicApp.controller;

import com.example.musicApp.dto.ArtistDto;
import com.example.musicApp.dto.ArtistListingDto;
import com.example.musicApp.enums.OrderEnum;
import com.example.musicApp.model.Artist;
import com.example.musicApp.service.ArtistService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/artist", method = {RequestMethod.GET})
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
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
