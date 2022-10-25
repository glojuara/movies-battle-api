package br.com.ada.moviesbattleapi.adapters.entrypoint.rest;

import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.response.GameResponse;
import br.com.ada.moviesbattleapi.core.domain.Game;
import br.com.ada.moviesbattleapi.core.domain.exception.*;
import br.com.ada.moviesbattleapi.core.usecase.CreateGameUseCase;
import br.com.ada.moviesbattleapi.core.usecase.EndGameUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("game")
public class GameController {

    @Autowired
    private CreateGameUseCase createGameUseCase;

    @Autowired
    private EndGameUseCase endGameUseCase;

    @GetMapping("/start")
    public ResponseEntity<GameResponse> start(final Authentication authentication) throws GameAlreadyStartedException {
        final Game game = createGameUseCase.execute(authentication.getName());
        return ResponseEntity.ok(GameResponse.from(game));
    }

    @GetMapping("/stop")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void stop(final Authentication authentication) {
        endGameUseCase.execute(authentication.getName());
    }

}
