package com.example.musicApp.dto;

import com.example.musicApp.model.Song;

public record ArtistDto(Integer userId, String username, String imgUrl, Iterable<Song> songs) { }
