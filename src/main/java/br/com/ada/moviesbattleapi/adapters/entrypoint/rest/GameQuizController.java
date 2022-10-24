package br.com.ada.moviesbattleapi.adapters.entrypoint.rest;

import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.request.QuizAnswerRequest;
import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.core.domain.exception.GameNotStartedException;
import br.com.ada.moviesbattleapi.core.domain.exception.GameOverException;
import br.com.ada.moviesbattleapi.core.domain.exception.WrongAnswerException;
import br.com.ada.moviesbattleapi.core.usecase.NextRoundUseCase;
import br.com.ada.moviesbattleapi.core.usecase.RoundAnswerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("game/quiz")
public class GameQuizController {

    @Autowired
    private NextRoundUseCase nextRoundUseCase;

    @Autowired
    private RoundAnswerUseCase roundAnswerUseCase;

    @GetMapping("/quiz")
    public ResponseEntity<Round> nextRound(Authentication authentication) throws GameNotStartedException {
        final Round round = nextRoundUseCase.execute(authentication.getName());
        if (Objects.isNull(round)) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(round);
    }

    @PostMapping("quiz/answer")
    public ResponseEntity answer(@RequestBody @Valid QuizAnswerRequest request) {
        try {
            roundAnswerUseCase.execute(request.getGameId(), request.getRoundId(), request.getMovieId());
            return ResponseEntity.ok("Parabéns, você acertou! Vamos para a proxima rodada.");
        } catch (WrongAnswerException e) {
            return ResponseEntity.ok("Ooooops, você errou! Não desanime, contineu tentando.");
        } catch (GameOverException e) {
            return ResponseEntity.ok("Oh não, você perdeu! Que tal começar uma nova partida?");
        }
    }

}
