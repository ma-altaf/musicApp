package com.example.musicApp.repository;

import com.example.musicApp.model.Song;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<Song, Integer> {}
