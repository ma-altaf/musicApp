package com.example.musicApp.repository;

import com.example.musicApp.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Integer> { }
