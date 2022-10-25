package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.Ranking;
import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.core.ports.RankingGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FindRankingUseCaseTest {

    private RankingGateway rankingGateway = Mockito.mock(RankingGateway.class);
    private FindRankingUseCase findRankingUseCase = new FindRankingUseCase(rankingGateway);

    @Test
    public void mustReturnTheRanking() {

        Mockito.when(rankingGateway.findRanking()).thenReturn(buildRanking(5));

        findRankingUseCase.execute();

        Mockito.verify(rankingGateway, Mockito.times(1)).findRanking();

    }

    private List<Ranking> buildRanking(Integer numberOfGames) {
        List<Ranking> rankings = new ArrayList<>();
        for (int i = 0; i < numberOfGames; i++) {
            rankings.add(new Ranking());
        }
        return rankings;
    }

}
