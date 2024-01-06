package com.example.musicApp.service;

import com.example.musicApp.model.Artist;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    Artist signUp(Artist artist);

    ResponseEntity<byte[]> updateImg(Integer user_id, MultipartFile imgFile) throws IOException;
}
