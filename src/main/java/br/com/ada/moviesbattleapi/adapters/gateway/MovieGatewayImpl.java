package br.com.ada.moviesbattleapi.adapters.gateway;

import br.com.ada.moviesbattleapi.adapters.gateway.mapper.MovieEntityMapper;
import br.com.ada.moviesbattleapi.core.domain.Movie;
import br.com.ada.moviesbattleapi.core.ports.MovieGateway;
import br.com.ada.moviesbattleapi.infrastructure.repository.MovieRepository;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieGatewayImpl implements MovieGateway {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> findAll() {
        List<MovieEntity> movieEntities = movieRepository.findAll();
        return movieEntities.stream().map(MovieEntityMapper::map).collect(Collectors.toList());
    }
}
