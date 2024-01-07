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
import java.nio.file.Files;
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
    public Artist signUp(Artist artist) {
        if (artistRepository.findByUsername(artist.getUsername()).isPresent()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        return artistRepository.save(artist);
    }

    @Override
    public Artist login(Artist artist) {
        Artist artistDb = artistRepository.findByUsername(artist.getUsername()).orElse(null);

        // user not found
        if (artistDb == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // incorrect password
        if (!artistDb.getPassword().equals(artist.getPassword())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return artistDb;
    }

    @Override
    public Artist updatePassword(String username, String oldPassword, String newPassword) {
        Artist artist = artistRepository.findByUsername(username).orElse(null);

        // user not found
        if (artist == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // incorrect old password
        if (!oldPassword.equals(artist.getPassword())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        // update password
        artist.setPassword(newPassword);
        artistRepository.save(artist);

        return artist;
    }

    @Override
    public ResponseEntity<byte[]> updateImg(Integer user_id, MultipartFile imgFile) throws IOException {
        // ensure the new imageFile is not null
        if (imgFile == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // get user
        Artist artist = artistRepository.findById(user_id).orElse(null);
        if (artist == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // save file
        // get reference to local folder
        String path = System.getProperty("user.dir");
        // create the new folder
        new File(path + "/profileImg/" + user_id).mkdirs();

        // save the file to the local file system
        File local_imgFile = new File(path + "/profileImg/" + user_id + "/" + imgFile.getOriginalFilename());

        imgFile.transferTo(local_imgFile);

        // update the user imageUrl
        artist.setImgUrl(local_imgFile.getAbsoluteFile().toString());

        // update the user record
        artistRepository.save(artist);

        HttpHeaders headers = new HttpHeaders();

        if (imgFile.getContentType() == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        headers.setContentType(MediaType.valueOf(imgFile.getContentType()));
        ContentDisposition build = ContentDisposition
                .builder("attachment")
                .filename(imgFile.getOriginalFilename())
                .build();
        headers.setContentDisposition(build);

        return new ResponseEntity<>(Files.readAllBytes(local_imgFile.toPath()), headers, HttpStatus.OK);
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
    public Iterable<Artist> getArtists(Integer pageNo, Integer pageSize, String orderBy, OrderEnum order) {
        if (order == OrderEnum.ASC) {
            return artistRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.by(orderBy).ascending()));
        } else {
            return artistRepository.findAll(PageRequest.of(pageNo, pageSize, Sort.by(orderBy).descending()));
        }
    }
}
