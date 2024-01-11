package com.example.musicApp.controller;

import com.example.musicApp.dto.LoginDto;
import com.example.musicApp.dto.RegisterDto;
import com.example.musicApp.model.Artist;
import com.example.musicApp.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public Artist createArtist(@RequestBody RegisterDto registerDto) {
        return authenticationService.signUp(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Artist> loginArtist(@RequestBody LoginDto loginDto) {
        return authenticationService.login(loginDto);
    }

    @PostMapping("/updatePassword")
    public Artist updatePassword(
            @RequestParam("username") String username,
            @RequestParam("oldPassword") String oldPassword,
            @RequestParam("newPassword") String newPassword) {
        return authenticationService.updatePassword(username, oldPassword, newPassword);
    }
}
