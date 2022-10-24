package br.com.ada.moviesbattleapi.adapters.entrypoint.rest;

import br.com.ada.moviesbattleapi.core.domain.Game;
import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.core.domain.exception.*;
import br.com.ada.moviesbattleapi.core.usecase.FindActiveGameOrCreateUseCase;
import br.com.ada.moviesbattleapi.core.usecase.EndGameUseCase;
import br.com.ada.moviesbattleapi.core.usecase.NextRoundUseCase;
import br.com.ada.moviesbattleapi.core.usecase.RoundAnswerUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("game")
public class GameController {


    @Autowired
    private FindActiveGameOrCreateUseCase findActiveGameOrCreateUseCase;

    @Autowired
    private EndGameUseCase endGameUseCase;

    @Autowired
    private NextRoundUseCase nextRoundUseCase;

    @Autowired
    private RoundAnswerUseCase roundAnswerUseCase;

    @GetMapping("/start/{username}")
    public ResponseEntity<Game> start(@PathVariable final String username) throws GameAlreadyStartedException {
        final Game game = findActiveGameOrCreateUseCase.execute(username);
        return ResponseEntity.ok(game);
    }


    @GetMapping("/stop/{username}")
    public void stop(@PathVariable final String username) throws GameAlreadyFinishedException {
        endGameUseCase.execute(username);
    }

    @GetMapping("/quiz/{username}")
    public ResponseEntity<Round> nextRound(@PathVariable final String username) throws GameNotStartedException {
        final Round round = nextRoundUseCase.execute(username);
        if (Objects.isNull(round)) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(round);
    }

    @GetMapping("/answer/{gameId}/{roundId}/{movieId}")
    public ResponseEntity answer(@PathVariable final Integer gameId, @PathVariable final Integer roundId,
                                 @PathVariable Integer movieId) {
        try {
            roundAnswerUseCase.execute(gameId, roundId, movieId);
            return ResponseEntity.ok("Parabéns, você acertou! Vamos para a proxima rodada.");
        } catch (WrongAnswerException e) {
            return ResponseEntity.ok("Ooooops, você errou! Não desanime, contineu tentando.");
        } catch (GameOverException e) {
            return ResponseEntity.ok("Oh não, você perdeu! Que tal começar uma nova partida?");
        }
    }


}
