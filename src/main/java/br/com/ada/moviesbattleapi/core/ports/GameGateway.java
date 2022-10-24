package br.com.ada.moviesbattleapi.core.ports;

import br.com.ada.moviesbattleapi.core.domain.Game;

public interface GameGateway {

    Game findByStatusAndPlayerUsername(String status, String username);
    Game findById(Integer id);
    Game save(Game game);

}
