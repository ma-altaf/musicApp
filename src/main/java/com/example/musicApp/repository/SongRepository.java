package com.example.musicApp.repository;

import com.example.musicApp.dto.SongListingDto;
import com.example.musicApp.model.Artist;
import com.example.musicApp.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, String> {
    List<SongListingDto> findAllByTitleContainingIgnoreCase(String query);

    List<Song> findAllByAuthor(Artist author);
}
