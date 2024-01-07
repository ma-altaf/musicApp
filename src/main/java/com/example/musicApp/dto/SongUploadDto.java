package com.example.musicApp.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.Optional;

public record SongUploadDto(String title,
                            MultipartFile image,
                            MultipartFile audio,
                            Optional<Collection<String>> source_ids,
                            Integer author_id) { }
