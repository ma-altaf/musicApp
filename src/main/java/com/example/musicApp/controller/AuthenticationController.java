package com.example.musicApp.controller;

import com.example.musicApp.dto.LoginDto;
import com.example.musicApp.dto.RegisterDto;
import com.example.musicApp.dto.TokenDto;
import com.example.musicApp.dto.UpdatePasswordDto;
import com.example.musicApp.model.Artist;
import com.example.musicApp.service.ArtistService;
import com.example.musicApp.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final ArtistService artistService;

    public AuthenticationController(AuthenticationService authenticationService, ArtistService artistService) {
        this.authenticationService = authenticationService;
        this.artistService = artistService;
    }

    @PostMapping("/updateImg")
    public Artist updateImg(
            Principal principal,
            @RequestParam("imgFile") MultipartFile imgFile) throws IOException {
        return artistService.updateImg(principal.getName(), imgFile);
    }

    @GetMapping("/currentArtist")
    public Artist currentArtist(Principal principal) {
        return authenticationService.getCurrentUser(principal);
    }

    @PostMapping("/register")
    public TokenDto<Artist> createArtist(@RequestBody RegisterDto registerDto) {
        return authenticationService.signUp(registerDto);
    }

    @PostMapping("/login")
    public TokenDto<Artist> loginArtist(@RequestBody LoginDto loginDto) {
        return authenticationService.login(loginDto);
    }

    @PostMapping("/updatePassword")
    public Artist updatePassword(
            Principal principal,
            @RequestBody UpdatePasswordDto updatePasswordDto) {
        System.out.println("\n\n\n attempting update");
        return authenticationService.updatePassword(
                principal.getName(),
                updatePasswordDto.oldPassword(),
                updatePasswordDto.newPassword());
    }
}
