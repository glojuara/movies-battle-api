package br.com.ada.moviesbattleapi.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Round {

    private Integer id;
    private Game game;
    private List<Movie> movies;

    public Round(List<Movie> movies) {
        this.movies = List.copyOf(movies);
    }

    public Round(Game game, List<Movie> movies) {
        this(movies);
        this.game = game;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Movie> getMovies() {
        return List.copyOf(movies);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
