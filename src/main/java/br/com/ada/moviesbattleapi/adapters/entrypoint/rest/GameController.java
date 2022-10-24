package br.com.ada.moviesbattleapi.adapters.entrypoint.rest;

import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.request.QuizAnswerRequest;
import br.com.ada.moviesbattleapi.core.domain.Game;
import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.core.domain.exception.*;
import br.com.ada.moviesbattleapi.core.usecase.FindActiveGameOrCreateUseCase;
import br.com.ada.moviesbattleapi.core.usecase.EndGameUseCase;
import br.com.ada.moviesbattleapi.core.usecase.NextRoundUseCase;
import br.com.ada.moviesbattleapi.core.usecase.RoundAnswerUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.Objects;

@RestController
@RequestMapping("game")
public class GameController {


    @Autowired
    private FindActiveGameOrCreateUseCase findActiveGameOrCreateUseCase;

    @Autowired
    private EndGameUseCase endGameUseCase;

    @GetMapping("/start")
    public ResponseEntity<Game> start(final Authentication authentication) throws GameAlreadyStartedException {
        final Game game = findActiveGameOrCreateUseCase.execute(authentication.getName());
        return ResponseEntity.ok(game);
    }


    @GetMapping("/stop")
    public void stop(final Authentication authentication) throws GameAlreadyFinishedException {
        endGameUseCase.execute(authentication.getName());
    }

}
