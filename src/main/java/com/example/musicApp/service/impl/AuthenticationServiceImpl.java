package com.example.musicApp.service.impl;

import com.example.musicApp.config.security.JwtGenerator;
import com.example.musicApp.dto.LoginDto;
import com.example.musicApp.dto.RegisterDto;
import com.example.musicApp.dto.TokenDto;
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
    private final JwtGenerator jwtGenerator;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, ArtistRepository artistRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, JwtGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.artistRepository = artistRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public TokenDto<Artist> signUp(RegisterDto registerDto) {
        if (artistRepository.existsByUsername(registerDto.username())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        Artist artist = new Artist();

        artist.setUsername(registerDto.username());
        artist.setPassword(passwordEncoder.encode(registerDto.password()));

        // set the role (setting everyone to user)
        Role roles = roleRepository.findByName("USER").get();
        artist.setRoles(Set.of(roles));

        artistRepository.save(artist);

        return login(new LoginDto(registerDto.username(), registerDto.password()));
    }

    @Override
    public TokenDto<Artist> login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // generate token
        String token = jwtGenerator.generateToken(authentication);
        Artist artist = artistRepository.findByUsername(loginDto.username()).get();

        return new TokenDto<>(artist, token);
    }

    @Override
    public Artist updatePassword(String username, String oldPassword, String newPassword) {
        // user not found
        if (!artistRepository.existsByUsername(username)) throw new UsernameNotFoundException("Artist does not exists.");
        Artist artist = artistRepository.findByUsername(username).get();

        // incorrect old password
        if (!passwordEncoder.matches(oldPassword, artist.getPassword())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        // update password
        artist.setPassword(newPassword);
        artistRepository.save(artist);

        return artist;
    }
}
