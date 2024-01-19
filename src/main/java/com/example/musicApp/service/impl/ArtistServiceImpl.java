package com.example.musicApp.service.impl;

import com.example.musicApp.dto.ArtistDto;
import com.example.musicApp.dto.ArtistListingDto;
import com.example.musicApp.enums.OrderEnum;
import com.example.musicApp.model.Artist;
import com.example.musicApp.model.Song;
import com.example.musicApp.repository.ArtistRepository;
import com.example.musicApp.repository.SongRepository;
import com.example.musicApp.service.ArtistService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository, SongRepository songRepository) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
    }

    @Override
    public Artist updateImg(String username, MultipartFile imgFile) throws IOException {
        // ensure the new imageFile is not null
        if (imgFile == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // get user
        Artist artist = artistRepository.findByUsername(username).orElse(null);
        if (artist == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // save file
        // get reference to local folder
        String path = System.getProperty("user.dir");
        // create the new folder
        new File(path + "/profileImg/" + artist.getId()).mkdirs();
        String imgFileExtension = imgFile.getOriginalFilename().substring(imgFile.getOriginalFilename().lastIndexOf("."));

        // save the file to the local file system
        File local_imgFile = new File(path + "/profileImg/" + artist.getId() + "/" + artist.getId() + "-profileImg" + imgFileExtension);

        imgFile.transferTo(local_imgFile);

        // update the user imageUrl
        artist.setLocalImgUrl(local_imgFile.getAbsoluteFile().toString());
        artist.setImgUrl("http://localhost:8080/download/artist/img/" + + artist.getId() + "-profileImg" + imgFileExtension);

        // update the user record
        artist = artistRepository.save(artist);

        return artist;
    }

    @Override
    public List<ArtistListingDto> searchArtists(String query) {
        return artistRepository
                .findAllByUsernameContainingIgnoreCase(query)
                .stream()
                .map(artist -> new ArtistListingDto(
                        artist.getId(),
                        artist.getUsername(),
                        artist.getImgUrl()))
                .collect(Collectors.toList());
    }

    @Override
    public ArtistDto getArtistById(Integer id) {
        Artist artist = artistRepository.findById(id).orElse(null);

        if (artist == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Iterable<Song> songs = songRepository.findAllByAuthor(artist);

        return new ArtistDto(artist.getId(), artist.getUsername(), artist.getImgUrl(), songs);
    }

    @Override
    public Artist getArtistByImgUrl(String imgUrl) {
        return artistRepository.findByImgUrl(imgUrl).orElse(null);
    }

    @Override
    public Iterable<Artist> getArtists(Integer pageNo, Integer pageSize, String orderBy, OrderEnum order) {
        if (order == OrderEnum.ASC) {
            return artistRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.by(orderBy).ascending()));
        } else {
            return artistRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.by(orderBy).descending()));
        }
    }
}
