package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.*;
import br.com.ada.moviesbattleapi.core.ports.GameGateway;
import br.com.ada.moviesbattleapi.core.ports.MovieGateway;
import br.com.ada.moviesbattleapi.core.ports.PlayerGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class CreateGameUseCase {

    @Autowired
    private MovieGateway movieGateway;

    @Autowired
    private GameGateway gameGateway;

    @Autowired
    private PlayerGateway playerGateway;

    @Transactional
    public Game execute(String username) {

        Game activeGame = gameGateway.findByStatusAndPlayerUsername(GameStatus.ACTIVE.name(), username);
        if (Objects.nonNull(activeGame)) {
            return activeGame;
        }

        Player player = playerGateway.findByUsername(username);
        List<Movie> movies = movieGateway.findAll();

        Game game = new GameBuilder()
                .withPlayer(player)
                .withMovies(movies)
                .build();

        return gameGateway.save(game);

    }

}
