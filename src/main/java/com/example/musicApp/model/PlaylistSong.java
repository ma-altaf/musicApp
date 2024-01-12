package com.example.musicApp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "playlist_song")
public class PlaylistSong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Song song;

    private Integer position;
}
