package com.example.musicApp.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Embeddable
public class ArtistSongId implements Serializable {
    private Artist artist;
    private Song song;
}
