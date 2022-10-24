package br.com.ada.moviesbattleapi.adapters.entrypoint.rest;

import br.com.ada.moviesbattleapi.core.domain.Ranking;
import br.com.ada.moviesbattleapi.core.ports.RankingGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("ranking")
public class RankingController {

    @Autowired
    private RankingGateway rankingGateway;

    @GetMapping
    public ResponseEntity getRanking() {
        List<Ranking> ranking = rankingGateway.findRanking();
        return ResponseEntity.ok(ranking);
    }

}
