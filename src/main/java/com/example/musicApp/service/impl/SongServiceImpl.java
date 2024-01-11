package com.example.musicApp.service.impl;

import com.example.musicApp.dto.SongUploadDto;
import com.example.musicApp.dto.SongListingDto;
import com.example.musicApp.enums.OrderEnum;
import com.example.musicApp.model.Artist;
import com.example.musicApp.model.Song;
import com.example.musicApp.repository.ArtistRepository;
import com.example.musicApp.repository.SongRepository;
import com.example.musicApp.service.SongService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public SongServiceImpl(SongRepository songRepository, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public Song addSong(String username, SongUploadDto details) throws IOException {
        Artist artist = artistRepository.findByUsername(username).get();

        MultipartFile imgFile = details.image();
        MultipartFile audioFile = details.audio();

        // create song id
        String songId = UUID.randomUUID().toString();

        // get reference to local folder
        String path = System.getProperty("user.dir");
        // create the new folder
        new File(path + "/songs/" + songId).mkdirs();
        // create the image and audio file
        File local_imgFile = new File(path + "/songs/" + songId + "/" + imgFile.getOriginalFilename());
        File local_audioFile = new File(path + "/songs/" + songId + "/" + audioFile.getOriginalFilename());

        // save the files to the file system
        imgFile.transferTo(local_imgFile);
        audioFile.transferTo(local_audioFile);

        // create the new song object
        Song song = Song.builder()
                .id(songId)
                .title(details.title())
                .imgUrl(local_imgFile.getAbsoluteFile().toString())
                .audioUrl(local_audioFile.getAbsoluteFile().toString())
                .author(artist)
                .downloads(0)
                .favourites(0)
                .listens(0)
                .released(new Date().getTime())
                .build();

        Set<Song> sources = new HashSet<>(songRepository.findAllById(details.source_ids().orElse(new ArrayList<>())));

        song.setSources(sources);

        return songRepository.save(song);
    }

    @Override
    public Iterable<Song> getSongs(Integer pageNo, Integer pageSize, String orderBy, OrderEnum order) {
        if (order == OrderEnum.ASC) {
            return songRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.by(orderBy).ascending()));
        } else {
            return songRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.by(orderBy).descending()));
        }
    }

    @Override
    public Song getSong(String id) {
        return songRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<SongListingDto> searchSongs(String query) { return songRepository.findAllByTitleContainingIgnoreCase(query); }
}
