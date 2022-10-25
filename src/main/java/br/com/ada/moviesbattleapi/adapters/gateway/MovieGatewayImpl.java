package br.com.ada.moviesbattleapi.adapters.gateway;

import br.com.ada.moviesbattleapi.adapters.gateway.mapper.entity.MovieEntityMapper;
import br.com.ada.moviesbattleapi.adapters.gateway.mapper.http.OmbdMovieDetailResponseMapper;
import br.com.ada.moviesbattleapi.core.domain.Movie;
import br.com.ada.moviesbattleapi.core.domain.MovieDetail;
import br.com.ada.moviesbattleapi.core.domain.enums.Partner;
import br.com.ada.moviesbattleapi.core.domain.exception.PartnerNotFoundException;
import br.com.ada.moviesbattleapi.core.ports.MovieGateway;
import br.com.ada.moviesbattleapi.infrastructure.http.OmdbApiClient;
import br.com.ada.moviesbattleapi.infrastructure.http.response.ImdbMovieDetailResponse;
import br.com.ada.moviesbattleapi.infrastructure.repository.MovieRepository;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieGatewayImpl implements MovieGateway {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private OmdbApiClient omdbApiClient;

    @Value("${integration.omdbapi.apikey}")
    private String apiKey;

    @Override
    public List<Movie> findAll() {
        List<MovieEntity> movieEntities = movieRepository.findAll();
        return movieEntities.stream().map(MovieEntityMapper::map).collect(Collectors.toList());
    }

    @Override
    public Movie findById(Integer movieId) {
        MovieEntity movieEntity = movieRepository.findById(movieId).orElse(null);
        return MovieEntityMapper.map(movieEntity);
    }

    @Override
    public MovieDetail findMovieDetailById(final Integer movieId) {
        Movie movie = findById(movieId);

        Partner partner = Arrays.stream(Partner.values())
                .filter(p -> p.getPartnerName().equals(movie.getPartner()))
                .findFirst().orElse(null);


        switch (partner) {

            case OMDB:
                ImdbMovieDetailResponse movieDetailResponse = omdbApiClient.findMovieDetail(apiKey, movie.getPartnerId());
                MovieDetail movieDetail = OmbdMovieDetailResponseMapper.map(movieDetailResponse);
                movieDetail.setId(movieId);
                movieDetail.setPartner(movie.getPartner());
                movieDetail.setPartnerId(movie.getPartnerId());
                return movieDetail;
            default: 
                throw new PartnerNotFoundException("Partner not found");

        }
    }

}
