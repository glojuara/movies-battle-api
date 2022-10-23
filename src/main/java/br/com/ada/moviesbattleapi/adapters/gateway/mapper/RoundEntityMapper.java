package br.com.ada.moviesbattleapi.adapters.gateway.mapper;

import br.com.ada.moviesbattleapi.core.domain.Movie;
import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.core.domain.exception.RoundStatus;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RoundEntityMapper {

    public static Round map(RoundEntity entity) {
        if (Objects.isNull(entity)) return null;

        List<Movie> movies = entity.getMovies().stream()
                .map(movieEntity -> MovieEntityMapper.map(movieEntity)).collect(Collectors.toList());

        Round round = new Round(movies);
        round.setId(entity.getId());
        return round;
    }

    public static RoundEntity from(Round round) {
        if (Objects.isNull(round)) return null;

        RoundEntity roundEntity = new RoundEntity();

        List<MovieEntity> movieEntities = round.getMovies().stream()
                .map(MovieEntityMapper::from).collect(Collectors.toList());

        roundEntity.setId(round.getId());
        roundEntity.setMovies(movieEntities);
        roundEntity.setStatus(RoundStatus.PENDING.name());

        return roundEntity;
    }


}
