package com.example.musicApp.controller;

import com.example.musicApp.model.Artist;
import com.example.musicApp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public Artist createUser(@RequestBody Artist artist) {
        return userService.signUp(artist);
    }

    @PostMapping("/updateImg/{id}")
    public ResponseEntity<byte[]> updateImg(@PathVariable Integer id, @RequestParam("imgFile")MultipartFile imgFile) throws IOException {
        return userService.updateImg(id, imgFile);
    }
}
