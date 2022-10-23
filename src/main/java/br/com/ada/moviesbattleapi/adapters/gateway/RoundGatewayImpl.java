package br.com.ada.moviesbattleapi.adapters.gateway;

import br.com.ada.moviesbattleapi.adapters.gateway.mapper.RoundEntityMapper;
import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.core.ports.RoundGateway;
import br.com.ada.moviesbattleapi.infrastructure.repository.RoundRepository;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.RoundEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoundGatewayImpl implements RoundGateway {

    @Autowired
    private RoundRepository roundRepository;

    @Override
    public List<Round> findByGameIdAndStatus(Integer gameId, String status) {
        List<RoundEntity> roundEntities = roundRepository.findByGameIdAndStatus(gameId, status);
        return roundEntities.stream().map(RoundEntityMapper::map).collect(Collectors.toList());
    }

    @Override
    public void deleteAll(List<Round> rounds) {
        List<RoundEntity> roundEntities = rounds.stream().map(RoundEntityMapper::from).collect(Collectors.toList());
        roundRepository.deleteAll(roundEntities);
    }
}
