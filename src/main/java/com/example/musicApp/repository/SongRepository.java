package com.example.musicApp.repository;

import com.example.musicApp.dto.SongListingDto;
import com.example.musicApp.model.Artist;
import com.example.musicApp.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, String> {
    List<SongListingDto> findAllByTitleContainingIgnoreCase(String query);

    Optional<Song> findByImgUrl(String imgUrl);
    Optional<Song> findByAudioUrl(String audioUrl);

    List<Song> findAllByAuthor(Artist author);
}
