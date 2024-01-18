package com.example.musicApp.service;

import com.example.musicApp.dto.LoginDto;
import com.example.musicApp.dto.RegisterDto;
import com.example.musicApp.dto.TokenDto;
import com.example.musicApp.model.Artist;

import java.security.Principal;

public interface AuthenticationService {
    TokenDto<Artist> signUp(RegisterDto registerDto);

    TokenDto<Artist> login(LoginDto loginDto);

    Artist updatePassword(String username, String oldPassword, String newPassword);

    Artist getCurrentUser(Principal principal);
}
