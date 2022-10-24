package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.Game;
import br.com.ada.moviesbattleapi.core.domain.exception.GameAlreadyStartedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class FindActiveGameOrCreateUseCase {

    @Autowired
    private FindActiveGameUseCase findActiveGameUseCase;

    @Autowired
    private CreateGameUseCase createGameUseCase;

    @Transactional
    public Game execute(String username) throws GameAlreadyStartedException {
        Game activeGame = findActiveGameUseCase.execute(username);
        if (Objects.nonNull(activeGame)) {
            throw new GameAlreadyStartedException("The game already started, please follow to the quiz");
        }
        return createGameUseCase.execute(username);
    }

}
