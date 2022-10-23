package br.com.ada.moviesbattleapi.adapters.gateway.mapper;

import br.com.ada.moviesbattleapi.core.domain.Movie;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.MovieEntity;

import java.util.Objects;

public class MovieEntityMapper {

    public static Movie map(MovieEntity entity) {
        if (Objects.isNull(entity)) return null;

        return new Movie(
                entity.getId(),
                entity.getTitle(),
                entity.getPartner(),
                entity.getPartnerId()
        );
    }

    public static MovieEntity from(Movie movie) {
        if (Objects.isNull(movie)) return null;

        MovieEntity entity = new MovieEntity();
        entity.setId(movie.getId());
        entity.setTitle(movie.getTitle());
        entity.setId(movie.getId());
        entity.setPartner(movie.getPartner());
        entity.setPartnerId(movie.getPartnerId());
        return entity;
    }

}
