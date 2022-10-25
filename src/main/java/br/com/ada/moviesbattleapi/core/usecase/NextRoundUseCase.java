package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.Game;
import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.core.domain.exception.GameNotStartedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class NextRoundUseCase {

    @Autowired
    private FindPendingRoundsUseCase findPendingRoundsUseCase;

    @Autowired
    private EndGameUseCase endGameUseCase;

    @Autowired
    private FindActiveGameUseCase findActiveGameUseCase;

    public Round execute(String username) throws GameNotStartedException {
        Game game = findActiveGameUseCase.execute(username);
        if (Objects.isNull(game)) {
            throw new GameNotStartedException("The game is not started yet! You can start a new game");
        }

        List<Round> rounds = findPendingRoundsUseCase.execute(game.getId());
        if (rounds.isEmpty()) {
            endGameUseCase.execute(username);
            return null;
        }
        return rounds.get(0);
    }

}
