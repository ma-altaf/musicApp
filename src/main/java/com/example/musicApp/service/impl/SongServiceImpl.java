package com.example.musicApp.service.impl;

import com.example.musicApp.model.Song;
import com.example.musicApp.repository.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class SongServiceImpl implements com.example.musicApp.service.SongService {
    private final SongRepository songRepository;

    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public Song addSong(String title, MultipartFile imgFile, MultipartFile audioFile) throws IOException {
        // create song id
        String songId = UUID.randomUUID().toString();

        // get reference to local folder
        String path = System.getProperty("user.dir");
        // create the new folder
        new File(path + "/songs/" + songId).mkdirs();
        // create the image and audio file
        File local_imgFile = new File(path + "/songs/" + songId + "/" + imgFile.getOriginalFilename());
        File local_audioFile = new File(path + "/songs/" + songId + "/" + audioFile.getOriginalFilename());

        // create the new song object
        Song song = Song.builder()
                .id(songId)
                .title(title)
                .imgUrl(local_imgFile.getAbsoluteFile().toString())
                .audioUrl(local_audioFile.getAbsoluteFile().toString())
                .downloads(0)
                .favourites(0)
                .listens(0)
                .build();

        // save the files to the file system
        imgFile.transferTo(local_imgFile);
        audioFile.transferTo(local_audioFile);

        return songRepository.save(song);
    }

    @Override
    public Iterable<Song> getSongs() {
        return songRepository.findAll();
    }

    @Override
    public Song getSong(Integer id) {
        return songRepository.findById(id).orElse(null);
    }
}
