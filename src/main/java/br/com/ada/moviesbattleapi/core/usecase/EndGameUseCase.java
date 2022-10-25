package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.Game;
import br.com.ada.moviesbattleapi.core.domain.enums.GameStatus;
import br.com.ada.moviesbattleapi.core.domain.Ranking;
import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.core.domain.exception.GameNotStartedException;
import br.com.ada.moviesbattleapi.core.ports.GameGateway;
import br.com.ada.moviesbattleapi.core.ports.RankingGateway;
import br.com.ada.moviesbattleapi.core.ports.RoundGateway;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class EndGameUseCase {

    private FindActiveGameUseCase findActiveGameUseCase;
    private FindPendingRoundsUseCase findPendingRoundsUseCase;
    private RoundGateway roundGateway;
    private GameGateway gameGateway;
    private RankingGateway rankingGateway;
    private CalculateGameScoreUseCase calculateGameScoreUseCase;

    public EndGameUseCase(FindActiveGameUseCase findActiveGameUseCase,
                          FindPendingRoundsUseCase findPendingRoundsUseCase, RoundGateway roundGateway,
                          GameGateway gameGateway, RankingGateway rankingGateway,
                          CalculateGameScoreUseCase calculateGameScoreUseCase) {
        this.findActiveGameUseCase = findActiveGameUseCase;
        this.findPendingRoundsUseCase = findPendingRoundsUseCase;
        this.roundGateway = roundGateway;
        this.gameGateway = gameGateway;
        this.rankingGateway = rankingGateway;
        this.calculateGameScoreUseCase = calculateGameScoreUseCase;
    }

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
