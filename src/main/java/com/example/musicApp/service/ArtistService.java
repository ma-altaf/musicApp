package com.example.musicApp.service;

import com.example.musicApp.dto.ArtistDto;
import com.example.musicApp.dto.ArtistListingDto;
import com.example.musicApp.enums.OrderEnum;
import com.example.musicApp.model.Artist;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ArtistService {

    Artist updateImg(String username, MultipartFile imgFile) throws IOException;

    Iterable<ArtistListingDto> searchArtists(String query);

    ArtistDto getArtistById(Integer id);

    Iterable<Artist> getArtists(Integer pageNo, Integer pageSize, String orderBy, OrderEnum order);
}
