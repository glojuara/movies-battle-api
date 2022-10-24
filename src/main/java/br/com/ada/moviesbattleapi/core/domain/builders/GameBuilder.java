package br.com.ada.moviesbattleapi.core.domain.builders;

import br.com.ada.moviesbattleapi.core.domain.Game;
import br.com.ada.moviesbattleapi.core.domain.Movie;
import br.com.ada.moviesbattleapi.core.domain.Player;
import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.core.domain.enums.GameStatus;

import java.util.List;
import java.util.Objects;

public class GameBuilder {

    private Game game;
    private Player player;
    private List<Movie> movies = List.of();
    private List<Round> rounds = List.of();

    public GameBuilder withPlayer(Player player) {
        this.player = player;
        return this;
    }

    public GameBuilder withMovies(List<Movie> movies) {
        if (Objects.isNull(movies)) {
            this.movies = List.of();
        } else {
            this.movies = List.copyOf(movies);
        }
        return this;
    }

    public GameBuilder withRounds(List<Round> rounds) {
        if (Objects.isNull(rounds)) {
            this.rounds = List.of();
        } else {
            this.rounds = List.copyOf(rounds);
        }
        return this;
    }

    public Game build() {
        if (this.rounds.isEmpty()) {
            game = new Game(this.player);
            game.buildRounds(this.movies);
        } else {
            game = new Game(this.player, this.rounds);
        }
        game.setGameStatus(GameStatus.ACTIVE);
        return game;
    }

}
