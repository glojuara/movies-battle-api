package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.enums.GameStatus;
import br.com.ada.moviesbattleapi.core.ports.GameGateway;
import br.com.ada.moviesbattleapi.core.usecase.FindActiveGameUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FindActiveGameUseCaseTest {

    private GameGateway gameGateway = Mockito.mock(GameGateway.class);
    private FindActiveGameUseCase findActiveGameUseCase = new FindActiveGameUseCase(gameGateway);

    @Test
    public void mustCallMethodsToVerifyActiveGame() {

        Mockito.when(gameGateway.findByStatusAndPlayerUsername(GameStatus.ACTIVE.name(), "player1"))
                .thenReturn(null);

        findActiveGameUseCase.execute("player1");

        Mockito.verify(gameGateway, Mockito.times(1))
                .findByStatusAndPlayerUsername(GameStatus.ACTIVE.name(), "player1");

    }


}
