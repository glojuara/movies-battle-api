package br.com.ada.moviesbattleapi.adapters.entrypoint.rest.response;

import br.com.ada.moviesbattleapi.core.domain.Round;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;
import java.util.stream.Collectors;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RoundResponse {

    private Integer id;
    private List<MovieResponse> movies;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieResponse> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieResponse> movies) {
        this.movies = movies;
    }

    public static RoundResponse from(Round round) {
        List<MovieResponse> movies = round.getMovies().stream().map(MovieResponse::from).collect(Collectors.toList());
        RoundResponse roundResponse = new RoundResponse();
        roundResponse.setId(round.getId());
        roundResponse.setMovies(movies);
        return roundResponse;
    }

}
