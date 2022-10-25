package br.com.ada.moviesbattleapi.adapters.entrypoint.rest;

import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.request.QuizAnswerRequest;
import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.response.RoundResponse;
import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.core.domain.exception.GameNotStartedException;
import br.com.ada.moviesbattleapi.core.domain.exception.GameOverException;
import br.com.ada.moviesbattleapi.core.domain.exception.WrongAnswerException;
import br.com.ada.moviesbattleapi.core.usecase.NextRoundUseCase;
import br.com.ada.moviesbattleapi.core.usecase.RoundAnswerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${wrong-answer.message}")
    private String wrongAnswerMessage;

    @Value("${right-answer.message}")
    private String rightAnswerMessage;

    @Value("${game-over.message}")
    private String gameOverMessage;

    @Value("${end-game.message}")
    private String endGameMessage;

    @GetMapping
    public ResponseEntity nextRound(Authentication authentication) throws GameNotStartedException {
        final Round round = nextRoundUseCase.execute(authentication.getName());
        if (Objects.isNull(round)) return ResponseEntity.ok(endGameMessage);
        return ResponseEntity.ok(RoundResponse.from(round));
    }

    @PostMapping("/answer")
    public ResponseEntity answer(@RequestBody @Valid QuizAnswerRequest request) {
        try {
            roundAnswerUseCase.execute(request.getGameId(), request.getRoundId(), request.getMovieId());
            return ResponseEntity.ok(rightAnswerMessage);
        } catch (WrongAnswerException e) {
            return ResponseEntity.ok(wrongAnswerMessage);
        } catch (GameOverException e) {
            return ResponseEntity.ok(gameOverMessage);
        }
    }

}
