package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.Movie;
import br.com.ada.moviesbattleapi.infrastructure.repository.MovieRepository;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.MovieEntity;
import br.com.ada.moviesbattleapi.infrastructure.repository.mapper.MovieEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindMoviesUseCase {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> execute() {
        List<MovieEntity> movies = movieRepository.findAll();
        return movies.stream().map(MovieEntityMapper::map).collect(Collectors.toList());
    }

}
