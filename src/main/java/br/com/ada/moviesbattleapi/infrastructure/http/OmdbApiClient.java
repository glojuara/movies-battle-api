package br.com.ada.moviesbattleapi.infrastructure.http;

import br.com.ada.moviesbattleapi.infrastructure.http.response.ImdbMovieDetailResponse;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "omdbapiClient", url = "${integration.omdbapi.url}")
public interface OmdbApiClient {

    @GetMapping(consumes = "application/json")
    ImdbMovieDetailResponse findMovieDetail(@RequestParam("apikey") String apiKey, @RequestParam("i") String movieId);

}
