package com.example.musicApp.controller;

import com.example.musicApp.dto.LoginDto;
import com.example.musicApp.dto.RegisterDto;
import com.example.musicApp.dto.TokenDto;
import com.example.musicApp.dto.UpdatePasswordDto;
import com.example.musicApp.model.Artist;
import com.example.musicApp.service.AuthenticationService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
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
