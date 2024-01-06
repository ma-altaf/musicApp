package com.example.musicApp.repository;

import com.example.musicApp.model.Artist;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Artist, Integer> { }
