package com.example.musicApp.config;

import com.example.musicApp.model.Artist;
import com.example.musicApp.model.Role;
import com.example.musicApp.repository.ArtistRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private ArtistRepository artistRepository;

    public CustomUserDetailService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Artist artist = artistRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Artist does not exists"));
        return new User(artist.getUsername(), artist.getPassword(), mapRolesToAuthorities(artist.getRoles()));
    }

    private Set<GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toSet());
    }
}
