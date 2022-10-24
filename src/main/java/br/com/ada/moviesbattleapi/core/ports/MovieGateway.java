package br.com.ada.moviesbattleapi.core.ports;

import br.com.ada.moviesbattleapi.core.domain.Movie;
import br.com.ada.moviesbattleapi.core.domain.MovieDetail;

import java.util.List;

public interface MovieGateway {

    List<Movie> findAll();

    Movie findById(final Integer movieId);

    MovieDetail findMovieDetailById(final Integer moveId);

}
