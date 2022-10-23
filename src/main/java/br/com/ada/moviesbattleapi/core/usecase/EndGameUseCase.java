package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.Game;
import br.com.ada.moviesbattleapi.core.domain.GameStatus;
import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.core.domain.exception.RoundStatus;
import br.com.ada.moviesbattleapi.core.ports.GameGateway;
import br.com.ada.moviesbattleapi.core.ports.RoundGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class EndGameUseCase {

    @Autowired
    private GameGateway gameGateway;

    @Autowired
    private RoundGateway roundGateway;

    @Transactional
    public void execute(String username) {

        Game game = gameGateway.findByStatusAndPlayerUsername(GameStatus.ACTIVE.name(), username);
        if (Objects.nonNull(game)) {
            game.setGameStatus(GameStatus.GAME_OVER);
            List<Round> pendingRounds = roundGateway.findByGameIdAndStatus(game.getId(), RoundStatus.PENDING.name());
            pendingRounds.forEach(game::removeRound);
            gameGateway.save(game);
            roundGateway.deleteAll(pendingRounds);
        }
    }

//    @Transactional
//    public void execute2(String username) {
//
//        GameEntity game = gameRepository.findByStatusAndPlayerUsername(GameStatus.ACTIVE.name(), username);
//        if (Objects.nonNull(game)) {
//            game.setStatus(GameStatus.GAME_OVER.name());
//            List<RoundEntity> pendingRounds = roundRepository.findByGameIdAndStatus(game.getId(), RoundStatus.PENDING.name());
//            pendingRounds.forEach(roundEntity -> game.removeRound(roundEntity));
//            gameRepository.save(game);
//            roundRepository.deleteAll(pendingRounds);
//        }
//    }

}
