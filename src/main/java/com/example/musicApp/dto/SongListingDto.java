package com.example.musicApp.dto;

import com.example.musicApp.model.Artist;

public record SongListingDto(String id, String title, Artist author, String imgUrl) { }
