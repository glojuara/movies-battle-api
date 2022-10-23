package br.com.ada.moviesbattleapi.infrastructure.repository.mapper;

import br.com.ada.moviesbattleapi.core.domain.Movie;
import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class RoundEntityMapper {

    public static Round map(RoundEntity entity) {
        List<Movie> movies = entity.getMovies().stream()
                .map(movieEntity -> MovieEntityMapper.map(movieEntity)).collect(Collectors.toList());

        return new Round(movies);
    }

    public static RoundEntity from(Round round) {
        RoundEntity roundEntity = new RoundEntity();

        List<MovieEntity> movieEntities = round.getMovies().stream()
                .map(MovieEntityMapper::from).collect(Collectors.toList());

//        List<RoundMovieEntity> roundMovieEntities = movieEntities.stream().map(movieEntity -> {
//            RoundMovieEntity roundMovie = new RoundMovieEntity();
//            RoundMovieKey roundMovieKey = new RoundMovieKey();
//            roundMovieKey.setMovieId(movieEntity.getId());
//            roundMovieKey.setRoundId(roundEntity.getId());
//            roundMovie.setId(roundMovieKey);
//            roundMovie.setRound(roundEntity);
//            roundMovie.setMovie(movieEntity);
//            return roundMovie;
//        }).collect(Collectors.toList());
//        roundEntity.setMovies(roundMovieEntities);

        roundEntity.setMovies(movieEntities);
        roundEntity.setStatus("PENDING");

        return roundEntity;
    }


}
