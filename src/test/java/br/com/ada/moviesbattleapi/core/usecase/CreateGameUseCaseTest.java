package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.Game;
import br.com.ada.moviesbattleapi.core.domain.Movie;
import br.com.ada.moviesbattleapi.core.domain.Player;
import br.com.ada.moviesbattleapi.core.domain.exception.GameAlreadyStartedException;
import br.com.ada.moviesbattleapi.core.ports.GameGateway;
import br.com.ada.moviesbattleapi.core.ports.MovieGateway;
import br.com.ada.moviesbattleapi.core.ports.PlayerGateway;
import br.com.ada.moviesbattleapi.core.usecase.CreateGameUseCase;
import br.com.ada.moviesbattleapi.core.usecase.FindActiveGameUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CreateGameUseCaseTest {

    private FindActiveGameUseCase findActiveGameUseCase = Mockito.mock(FindActiveGameUseCase.class);
    private PlayerGateway playerGateway = Mockito.mock(PlayerGateway.class);
    private MovieGateway movieGateway = Mockito.mock(MovieGateway.class);
    private GameGateway gameGateway =  Mockito.mock(GameGateway.class);
    private CreateGameUseCase createGameUseCase = new CreateGameUseCase(findActiveGameUseCase, playerGateway,
            movieGateway, gameGateway);


    @Test
    public void mustThrowGameAlreadyStartedException() {
        Mockito.when(findActiveGameUseCase.execute("player1"))
                .thenReturn(new Game(new Player("player1")));

        assertThrows(GameAlreadyStartedException.class, () -> {
            createGameUseCase.execute("player1");
        });
        Mockito.verify(findActiveGameUseCase, Mockito.times(1)).execute(Mockito.any());
    }

    @Test
    public void mustCallTheMethodsToCreateNewGame() {

        Player player1 = new Player("player1");

        Mockito.when(findActiveGameUseCase.execute(player1.getUsername())).thenReturn(null);
        Mockito.when(playerGateway.findByUsername(player1.getUsername())).thenReturn(player1);
        Mockito.when(movieGateway.findAll()).thenReturn(buildListOfMovies(4));
        Mockito.when(gameGateway.save(Mockito.any())).thenReturn(new Game(player1));

        createGameUseCase.execute("player1");

        Mockito.verify(findActiveGameUseCase, Mockito.times(1)).execute(Mockito.any());
        Mockito.verify(playerGateway, Mockito.times(1)).findByUsername(Mockito.any());
        Mockito.verify(movieGateway, Mockito.times(1)).findAll();
        Mockito.verify(gameGateway, Mockito.times(1)).save(Mockito.any());

    }


    private List<Movie> buildListOfMovies(int quantityOfMovies) {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0, id = 1; i < quantityOfMovies; i++, id++) {
            Movie movie = new Movie(id, MessageFormat.format("Movie {0}", id), "mock", MessageFormat.format("mock{0}", id));
            movies.add(movie);
        }
        return movies;
    }

}
