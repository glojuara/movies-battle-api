package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.infrastructure.repository.GameRepository;
import br.com.ada.moviesbattleapi.infrastructure.repository.RoundMoviesRepository;
import br.com.ada.moviesbattleapi.infrastructure.repository.RoundRepository;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.GameEntity;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.RoundEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class EndGameUseCase {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private RoundMoviesRepository roundMoviesRepository;

    @Transactional
    public void execute(String username) {

//        GameEntity gameEntity = gameRepository.findById(1).get();
//
//        RoundEntity round = roundRepository.findById(2).get();
//        gameEntity.removeRound(round);
//
//        gameRepository.save(gameEntity);
//        roundRepository.deleteById(2);


        GameEntity game = gameRepository.findByStatusAndPlayerUsername("ACTIVE", username);
        if (Objects.nonNull(game)) {
            game.setStatus("GAME_OVER");
            List<RoundEntity> pendingRounds = roundRepository.findByGameIdAndStatus(game.getId(), "PENDING");


            pendingRounds.forEach(roundEntity -> {
                game.removeRound(roundEntity);
//                roundMoviesRepository.deleteByRoundId(roundEntity.getId());
            });
            gameRepository.save(game);
            roundRepository.deleteAll(pendingRounds);
        }
    }

}
