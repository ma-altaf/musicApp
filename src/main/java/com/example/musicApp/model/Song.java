package com.example.musicApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "song")
public class Song {
    @Id
    private String id;
    private String title;
    private String imgUrl;
    private String audioUrl;
    private Integer favourites = 0; // how many users have liked the song
    private Integer downloads = 0; // how many times the song have been downloaded
    private Integer listens = 0; // how many times the song has been played

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Song> sources;

    @OneToMany(mappedBy = "song")
    private Set<ArtistSong> artistSongs;
}
