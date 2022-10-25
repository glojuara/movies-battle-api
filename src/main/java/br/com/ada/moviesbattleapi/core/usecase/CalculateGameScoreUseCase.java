package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.enums.RoundStatus;
import br.com.ada.moviesbattleapi.core.ports.RoundGateway;
import org.springframework.stereotype.Service;


@Service
public class CalculateGameScoreUseCase {

    private RoundGateway roundGateway;

    public CalculateGameScoreUseCase(RoundGateway roundGateway) {
        this.roundGateway = roundGateway;
    }

    public Double execute(final Integer gameId) {
        Integer success = roundGateway.findByGameIdAndStatus(gameId, RoundStatus.SUCCESS.name()).size();
        Integer errors = roundGateway.findByGameIdAndStatus(gameId, RoundStatus.ERROR.name()).size();
        double responses = success.doubleValue() + errors.doubleValue();
        if (responses == 0) return 0.0;
        double rate = success / responses * 100;
        return success * rate;
    }


}
