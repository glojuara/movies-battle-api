package br.com.ada.moviesbattleapi.infrastructure.repository.seed;

import br.com.ada.moviesbattleapi.infrastructure.repository.PlayerRepository;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.PlayerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserSeed {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        playerRepository.save(newPlayer("player1", "pwd1"));
        playerRepository.save(newPlayer("player2", "pwd2"));
        playerRepository.save(newPlayer("player3", "pwd3"));
    }

    public PlayerEntity newPlayer(String username, String password) {
        PlayerEntity player = new PlayerEntity();
        player.setUsername(username);
        player.setPassword(passwordEncoder.encode(password));
        return player;
    }


}
