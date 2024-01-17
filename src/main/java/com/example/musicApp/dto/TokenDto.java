package com.example.musicApp.dto;

public record TokenDto<T>(T body, String token) {
}
