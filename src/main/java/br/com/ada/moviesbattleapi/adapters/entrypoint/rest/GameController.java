package br.com.ada.moviesbattleapi.adapters.entrypoint.rest;

import br.com.ada.moviesbattleapi.core.domain.Game;
import br.com.ada.moviesbattleapi.core.usecase.CreateGameUseCase;
import br.com.ada.moviesbattleapi.core.usecase.EndGameUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("game")
public class GameController {


    @Autowired
    private CreateGameUseCase createGameUseCase;
    @Autowired
    private EndGameUseCase endGameUseCase;

    @GetMapping("/start/{username}")
    public ResponseEntity<Game> newGame(@PathVariable String username) {
        Game game = createGameUseCase.execute(username);
        return ResponseEntity.ok(game);
    }

    @GetMapping("/stop/{username}")
    public void endGame(@PathVariable String username) {
        endGameUseCase.execute(username);
    }


}
