package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.Ranking;
import br.com.ada.moviesbattleapi.core.ports.RankingGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindRankingUseCase {

    @Autowired
    private RankingGateway rankingGateway;

    public List<Ranking> execute() {
        return this.rankingGateway.findRanking();
    }

}
