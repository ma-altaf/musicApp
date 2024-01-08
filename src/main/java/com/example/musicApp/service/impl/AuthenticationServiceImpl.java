package com.example.musicApp.service.impl;

import com.example.musicApp.dto.LoginDto;
import com.example.musicApp.dto.RegisterDto;
import com.example.musicApp.model.Artist;
import com.example.musicApp.model.Role;
import com.example.musicApp.repository.ArtistRepository;
import com.example.musicApp.repository.RoleRepository;
import com.example.musicApp.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final ArtistRepository artistRepository;
    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, ArtistRepository artistRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.artistRepository = artistRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public Artist signUp(RegisterDto registerDto) {
        if (artistRepository.existsByUsername(registerDto.username())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Artist artist = new Artist();

        artist.setUsername(registerDto.username());
        artist.setPassword(passwordEncoder.encode(registerDto.password()));

        // set the role (setting everyone to user)
        Role roles = roleRepository.findByName("USER").get();
        artist.setRoles(Set.of(roles));

        return artistRepository.save(artist);
    }

    @Override
    public Artist login(LoginDto loginDto) {
        System.out.println("before auth");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password())
        );

        System.out.println("after auth");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("getting user");
        // artist details not found
        if (!artistRepository.existsByUsername(loginDto.username())) throw new UsernameNotFoundException("Artist does not exists.");
        System.out.println("returning");
        return artistRepository.findByUsername(loginDto.username()).get();
    }

    @Override
    public Artist updatePassword(String username, String oldPassword, String newPassword) {
        // user not found
        if (artistRepository.existsByUsername(username)) throw new UsernameNotFoundException("Artist does not exists.");
        Artist artist = artistRepository.findByUsername(username).get();

        // incorrect old password
        if (!oldPassword.equals(artist.getPassword())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        // update password
        artist.setPassword(newPassword);
        artistRepository.save(artist);

        return artist;
    }
}
