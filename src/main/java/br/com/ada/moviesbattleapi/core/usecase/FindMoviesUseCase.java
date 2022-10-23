package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.Movie;
import br.com.ada.moviesbattleapi.core.ports.MovieGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindMoviesUseCase {

//    @Autowired
//    private MovieRepository movieRepository;

    @Autowired
    private MovieGateway movieGateway;

    public List<Movie> execute() {
//        List<MovieEntity> movies = movieRepository.findAll();
//        return movies.stream().map(MovieEntityMapper::map).collect(Collectors.toList());
        return movieGateway.findAll();
    }

}
