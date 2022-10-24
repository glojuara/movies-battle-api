package br.com.ada.moviesbattleapi.infrastructure.security;

import br.com.ada.moviesbattleapi.infrastructure.repository.PlayerRepository;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.PlayerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PlayerEntity user = playerRepository.findByUsername(username);

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("Username not found");
        }

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }
}
