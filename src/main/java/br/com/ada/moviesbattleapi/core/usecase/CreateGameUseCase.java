package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.Game;
import br.com.ada.moviesbattleapi.core.domain.builders.GameBuilder;
import br.com.ada.moviesbattleapi.core.domain.Movie;
import br.com.ada.moviesbattleapi.core.domain.Player;
import br.com.ada.moviesbattleapi.core.domain.exception.GameAlreadyStartedException;
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

    private FindActiveGameUseCase findActiveGameUseCase;
    private PlayerGateway playerGateway;
    private MovieGateway movieGateway;
    private GameGateway gameGateway;

    public CreateGameUseCase(FindActiveGameUseCase findActiveGameUseCase,
                             PlayerGateway playerGateway, MovieGateway movieGateway, GameGateway gameGateway) {
        this.findActiveGameUseCase = findActiveGameUseCase;
        this.playerGateway = playerGateway;
        this.movieGateway = movieGateway;
        this.gameGateway = gameGateway;
    }

    @Transactional
    public Game execute(String username) {

        Game activeGame = findActiveGameUseCase.execute(username);
        if (Objects.nonNull(activeGame)) {
            throw new GameAlreadyStartedException("The game already started, please follow to the quiz");
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
