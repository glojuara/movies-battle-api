package br.com.ada.moviesbattleapi.core.ports;

import br.com.ada.moviesbattleapi.core.domain.Movie;

import java.util.List;

public interface MovieGateway {

    List<Movie> findAll();

}
