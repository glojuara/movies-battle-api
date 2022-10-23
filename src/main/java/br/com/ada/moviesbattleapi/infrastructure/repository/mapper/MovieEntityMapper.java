package br.com.ada.moviesbattleapi.infrastructure.repository.mapper;

import br.com.ada.moviesbattleapi.core.domain.Movie;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.MovieEntity;

public class MovieEntityMapper {

    public static Movie map(MovieEntity entity) {
        return new Movie(
                entity.getId(),
                entity.getTitle(),
                entity.getPartner(),
                entity.getPartnerId()
        );
    }

    public static MovieEntity from(Movie movie) {
        MovieEntity entity = new MovieEntity();
        entity.setTitle(movie.getTitle());
        entity.setId(movie.getId());
        entity.setPartner(movie.getPartner());
        entity.setPartnerId(movie.getPartnerId());
        return entity;
    }

}
