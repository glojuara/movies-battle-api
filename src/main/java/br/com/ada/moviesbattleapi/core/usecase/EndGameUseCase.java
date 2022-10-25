package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.Game;
import br.com.ada.moviesbattleapi.core.domain.enums.GameStatus;
import br.com.ada.moviesbattleapi.core.domain.Ranking;
import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.core.domain.exception.GameNotStartedException;
import br.com.ada.moviesbattleapi.core.ports.GameGateway;
import br.com.ada.moviesbattleapi.core.ports.RankingGateway;
import br.com.ada.moviesbattleapi.core.ports.RoundGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class EndGameUseCase {

    @Autowired
    private FindActiveGameUseCase findActiveGameUseCase;

    @Autowired
    private FindPendingRoundsUseCase findPendingRoundsUseCase;

    @Autowired
    private RoundGateway roundGateway;

    @Autowired
    private GameGateway gameGateway;

    @Autowired
    private RankingGateway rankingGateway;

    @Autowired
    private CalculateGameScoreUseCase calculateGameScoreUseCase;

    @Transactional
    public void execute(String username) {

        Game game = findActiveGameUseCase.execute(username);
        if (Objects.isNull(game)) {
            throw new GameNotStartedException("The game is not started yet! You can start a new game");
        }
        if (Objects.nonNull(game)) {
            game.setGameStatus(GameStatus.GAME_OVER);
            List<Round> pendingRounds = findPendingRoundsUseCase.execute(game.getId());
            pendingRounds.forEach(game::removeRound);
            gameGateway.save(game);
            roundGateway.deleteAll(pendingRounds);

            Double score = calculateGameScoreUseCase.execute(game.getId());
            Ranking ranking = new Ranking();
            ranking.setGame(game);
            ranking.setScore(score);
            rankingGateway.save(ranking);
        }
    }

}
