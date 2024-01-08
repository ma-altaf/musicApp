package com.example.musicApp.service;

import com.example.musicApp.dto.LoginDto;
import com.example.musicApp.dto.RegisterDto;
import com.example.musicApp.model.Artist;

public interface AuthenticationService {
    Artist signUp(RegisterDto registerDto);

    Artist login(LoginDto loginDto);

    Artist updatePassword(String username, String oldPassword, String newPassword);
}
