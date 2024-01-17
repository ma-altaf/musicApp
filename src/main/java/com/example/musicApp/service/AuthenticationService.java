package com.example.musicApp.service;

import com.example.musicApp.dto.LoginDto;
import com.example.musicApp.dto.RegisterDto;
import com.example.musicApp.dto.TokenDto;
import com.example.musicApp.model.Artist;

public interface AuthenticationService {
    TokenDto<Artist> signUp(RegisterDto registerDto);

    TokenDto<Artist> login(LoginDto loginDto);

    Artist updatePassword(String username, String oldPassword, String newPassword);
}
