package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.core.domain.enums.RoundStatus;
import br.com.ada.moviesbattleapi.core.ports.RoundGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class FindPendingRoundsUseCaseTest {


    private RoundGateway roundGateway = Mockito.mock(RoundGateway.class);
    private FindPendingRoundsUseCase findPendingRoundsUseCase = new FindPendingRoundsUseCase(roundGateway);

    @Test
    public void mustCallTheMethodsToFindPendingRounds() {

        Mockito.when(roundGateway.findByGameIdAndStatus(1, RoundStatus.PENDING.name()))
                .thenReturn(buildRoundList(6));

        findPendingRoundsUseCase.execute(1);

        Mockito.verify(roundGateway, Mockito.times(1))
                .findByGameIdAndStatus(1, RoundStatus.PENDING.name());

    }

    private List<Round> buildRoundList(Integer numberOfRounds) {
        List<Round> rounds = new ArrayList<>();
        for (int i = 0; i < numberOfRounds; i++) {
            rounds.add(new Round(List.of()));
        }
        return rounds;
    }



}
