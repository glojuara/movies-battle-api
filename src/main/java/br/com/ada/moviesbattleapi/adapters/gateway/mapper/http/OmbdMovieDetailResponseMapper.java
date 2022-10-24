package br.com.ada.moviesbattleapi.adapters.gateway.mapper.http;

import br.com.ada.moviesbattleapi.core.domain.MovieDetail;
import br.com.ada.moviesbattleapi.infrastructure.http.response.ImdbMovieDetailResponse;

public class OmbdMovieDetailResponseMapper {

    public static MovieDetail map(ImdbMovieDetailResponse response) {
        Double rating = Double.valueOf(response.getImdbRating());
        Long votes = Long.parseLong(response.getImdbVotes().replaceAll(",", ""));
        MovieDetail detail = new MovieDetail();
        detail.setRating(rating);
        detail.setVotes(votes);
        return detail;
    }

}
