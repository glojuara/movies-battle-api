package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.Ranking;
import br.com.ada.moviesbattleapi.core.ports.RankingGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FindRankingUseCase {

    private RankingGateway rankingGateway;

    public FindRankingUseCase(RankingGateway rankingGateway) {
        this.rankingGateway = rankingGateway;
    }

    public List<Ranking> execute() {
        return this.rankingGateway.findRanking();
    }

}
