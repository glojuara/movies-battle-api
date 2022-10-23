package br.com.ada.moviesbattleapi.core.ports;

import br.com.ada.moviesbattleapi.core.domain.Player;

public interface PlayerGateway {

    Player findByUsername(String username);

}
