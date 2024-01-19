package com.example.musicApp.repository;

import com.example.musicApp.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Integer> {
    Optional<Artist> findByUsername(String username);
    Optional<Artist> findByImgUrl(String imgUrl);
    Boolean existsByUsername(String username);
    List<Artist> findAllByUsernameContainingIgnoreCase(String username);
}
