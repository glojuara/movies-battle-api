package br.com.ada.moviesbattleapi.adapters.entrypoint.rest;

import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.response.RankingResponse;
import br.com.ada.moviesbattleapi.core.domain.Ranking;
import br.com.ada.moviesbattleapi.core.usecase.FindRankingUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("ranking")
public class RankingController {

    @Autowired
    private FindRankingUseCase findRankingUseCase;

    @GetMapping
    public ResponseEntity getRanking() {
        List<Ranking> ranking = findRankingUseCase.execute();
        return ResponseEntity.ok(ranking.stream().map(RankingResponse::from).collect(Collectors.toList()));
    }

}
