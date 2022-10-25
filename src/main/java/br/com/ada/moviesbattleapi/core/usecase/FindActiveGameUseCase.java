package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.Game;
import br.com.ada.moviesbattleapi.core.domain.enums.GameStatus;
import br.com.ada.moviesbattleapi.core.ports.GameGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindActiveGameUseCase {

    private GameGateway gameGateway;

    public FindActiveGameUseCase(GameGateway gameGateway) {
        this.gameGateway = gameGateway;
    }

    public Game execute(String username) {
        Game game = gameGateway.findByStatusAndPlayerUsername(GameStatus.ACTIVE.name(), username);
        return game;
    }

}
