package com.example.musicApp.service;

import com.example.musicApp.dto.SongUploadDto;
import com.example.musicApp.dto.SongListingDto;
import com.example.musicApp.enums.OrderEnum;
import com.example.musicApp.model.Song;

import java.io.IOException;

public interface SongService {
    Song addSong(String username, SongUploadDto songUploadDto) throws IOException;

    Iterable<Song> getSongs(Integer pageNo, Integer pageSize, String orderBy, OrderEnum order);

    Song getSongByImgUrl(String imgUrl);
    Song getSongByAudioUrl(String audioUrl);
    Song getSong(String id);

    Iterable<SongListingDto> searchSongs(String query);

}
