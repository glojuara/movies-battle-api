package br.com.ada.moviesbattleapi.core.ports;

import br.com.ada.moviesbattleapi.core.domain.Round;

import java.util.List;

public interface RoundGateway {

    List<Round> findByGameIdAndStatus(Integer gameId, String status);
    void deleteAll(List<Round> rounds);

}
