package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.core.domain.enums.RoundStatus;
import br.com.ada.moviesbattleapi.core.ports.RoundGateway;
import br.com.ada.moviesbattleapi.core.usecase.CalculateGameScoreUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class CalculateGameScoreUseCaseTest {

    private RoundGateway roundGateway = Mockito.mock(RoundGateway.class);
    private CalculateGameScoreUseCase calculateGameScoreUseCase = new CalculateGameScoreUseCase(roundGateway);

    @Test
    public void mustCalculateTheGameScore() {

        Mockito.when(roundGateway.findByGameIdAndStatus(1, RoundStatus.SUCCESS.name()))
                .thenReturn(buildRoundList(2));

        Mockito.when(roundGateway.findByGameIdAndStatus(1, RoundStatus.ERROR.name()))
                .thenReturn(buildRoundList(3));

        Double score = calculateGameScoreUseCase.execute(1);

        assertEquals(80.0, score);

    }

    private List<Round> buildRoundList(Integer numberOfRounds) {
        List<Round> rounds = new ArrayList<>();
        for (int i = 0; i < numberOfRounds; i++) {
            rounds.add(new Round(List.of()));
        }
        return rounds;
    }


}
