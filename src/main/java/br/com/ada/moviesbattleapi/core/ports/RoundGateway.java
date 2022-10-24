package br.com.ada.moviesbattleapi.core.ports;

import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.core.domain.enums.RoundStatus;

import java.util.List;

public interface RoundGateway {

    List<Round> findByGameIdAndStatus(Integer gameId, String status);
    Round findRoundById(Integer roundId);
    Round updateStatusById(Integer roundId, RoundStatus status);
    void deleteAll(List<Round> rounds);

}
