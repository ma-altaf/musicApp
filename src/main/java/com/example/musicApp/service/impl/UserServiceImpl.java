package com.example.musicApp.service.impl;

import com.example.musicApp.model.Artist;
import com.example.musicApp.repository.UserRepository;
import com.example.musicApp.service.UserService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Artist signUp(Artist artist) {
        return userRepository.save(artist);
    }

    @Override
    public ResponseEntity<byte[]> updateImg(Integer user_id, MultipartFile imgFile) throws IOException {
        // ensure the new imageFile is not null
        if (imgFile == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // get user
        Artist artist = userRepository.findById(user_id).orElse(null);
        if (artist == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // save file
        // get reference to local folder
        String path = System.getProperty("user.dir");
        // create the new folder
        new File(path + "/profileImg/" + user_id).mkdirs();

        // save the file to the local file system
        File local_imgFile = new File(path + "/profileImg/" + user_id + "/" + imgFile.getOriginalFilename());

        imgFile.transferTo(local_imgFile);

        // update the user imageUrl
        artist.setImgUrl(local_imgFile.getAbsoluteFile().toString());

        // update the user record
        userRepository.save(artist);

        HttpHeaders headers = new HttpHeaders();

        if (imgFile.getContentType() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        headers.setContentType(MediaType.valueOf(imgFile.getContentType()));
        ContentDisposition build = ContentDisposition
                .builder("attachment")
                .filename(imgFile.getOriginalFilename())
                .build();
        headers.setContentDisposition(build);

        return new ResponseEntity<>(imgFile.getBytes(), headers, HttpStatus.OK);
    }
}
