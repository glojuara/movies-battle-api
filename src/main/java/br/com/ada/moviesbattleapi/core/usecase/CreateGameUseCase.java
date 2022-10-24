package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.Game;
import br.com.ada.moviesbattleapi.core.domain.builders.GameBuilder;
import br.com.ada.moviesbattleapi.core.domain.Movie;
import br.com.ada.moviesbattleapi.core.domain.Player;
import br.com.ada.moviesbattleapi.core.ports.GameGateway;
import br.com.ada.moviesbattleapi.core.ports.MovieGateway;
import br.com.ada.moviesbattleapi.core.ports.PlayerGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CreateGameUseCase {

    @Autowired
    private PlayerGateway playerGateway;

    @Autowired
    private MovieGateway movieGateway;

    @Autowired
    private GameGateway gameGateway;

    @Transactional
    public Game execute(String username) {

        Player player = playerGateway.findByUsername(username);
        List<Movie> movies = movieGateway.findAll();

        Game game = new GameBuilder()
                .withPlayer(player)
                .withMovies(movies)
                .build();

        return gameGateway.save(game);
    }

}
