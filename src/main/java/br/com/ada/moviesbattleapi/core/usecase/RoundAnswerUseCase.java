package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.*;
import br.com.ada.moviesbattleapi.core.domain.enums.RoundStatus;
import br.com.ada.moviesbattleapi.core.domain.exception.GameException;
import br.com.ada.moviesbattleapi.core.domain.exception.GameNotFoundException;
import br.com.ada.moviesbattleapi.core.domain.exception.GameOverException;
import br.com.ada.moviesbattleapi.core.domain.exception.WrongAnswerException;
import br.com.ada.moviesbattleapi.core.ports.GameGateway;
import br.com.ada.moviesbattleapi.core.ports.MovieGateway;
import br.com.ada.moviesbattleapi.core.ports.RoundGateway;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RoundAnswerUseCase {

    private RoundGateway roundGateway;
    private GameGateway gameGateway;
    private MovieGateway movieGateway;
    private EndGameUseCase endGameUseCase;
    private FindPendingRoundsUseCase findPendingRoundsUseCase;

    public RoundAnswerUseCase(RoundGateway roundGateway, GameGateway gameGateway, MovieGateway movieGateway,
                              EndGameUseCase endGameUseCase, FindPendingRoundsUseCase findPendingRoundsUseCase) {
        this.roundGateway = roundGateway;
        this.gameGateway = gameGateway;
        this.movieGateway = movieGateway;
        this.endGameUseCase = endGameUseCase;
        this.findPendingRoundsUseCase = findPendingRoundsUseCase;
    }

    public void execute(Integer gameId, Integer roundId, Integer movieId) {

        Game game = gameGateway.findById(gameId);

        if (Objects.isNull(game)) {
            String errorMessage = MessageFormat.format("Game not found: {0}", gameId);
            throw new GameNotFoundException(errorMessage);
        }

        List<Round> rounds = findPendingRoundsUseCase.execute(gameId);
        Round round = rounds.get(0);

        if (round.getId() != roundId) {
            String errorMessage = MessageFormat.format("The roundId {0} is not valid for the gameId {1}", roundId, gameId);
            throw new GameException(errorMessage);
        }


        List<Movie> movies = round.getMovies();
        if (!movies.stream().anyMatch(m-> m.getId() == movieId)) {
            String errorMessage = MessageFormat.format("The moveId {0} is not valid for the roundId {1}", movieId, roundId);
            throw new GameException(errorMessage);
        }

        Player player = game.getPlayer();
        List<MovieDetail> movieDetailList = movies.stream()
                .map(movie -> movieGateway.findMovieDetailById(movie.getId())).collect(Collectors.toList());

        MovieDetail highestScoringMovie = movieDetailList.stream()
                .max(Comparator.comparing(MovieDetail::getScore)).get();

        if  (highestScoringMovie.getId() != movieId) {
            roundGateway.updateStatusById(roundId, RoundStatus.ERROR);

            Long errors = roundGateway.findByGameIdAndStatus(gameId, RoundStatus.ERROR.name()).stream().count();
            if (errors >= 3) {
                endGameUseCase.execute(player.getUsername());
                throw new GameOverException("Game Over!");
            }

            throw new WrongAnswerException("The wrong movie was chosen");
        }
        roundGateway.updateStatusById(roundId, RoundStatus.SUCCESS);
    }

}
