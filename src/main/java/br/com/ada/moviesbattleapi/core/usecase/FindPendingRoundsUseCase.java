package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.core.domain.enums.RoundStatus;
import br.com.ada.moviesbattleapi.core.ports.RoundGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class FindPendingRoundsUseCase {

    @Autowired
    private RoundGateway roundGateway;

    public List<Round> execute(Integer gameId) {
        List<Round> rounds = roundGateway.findByGameIdAndStatus(gameId, RoundStatus.PENDING.name());
        return rounds;
    }

}
