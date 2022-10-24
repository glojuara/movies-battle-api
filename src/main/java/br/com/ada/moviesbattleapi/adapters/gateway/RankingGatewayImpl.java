package br.com.ada.moviesbattleapi.adapters.gateway;

import br.com.ada.moviesbattleapi.adapters.gateway.mapper.entity.GameRankingEntityMapper;
import br.com.ada.moviesbattleapi.core.domain.Ranking;
import br.com.ada.moviesbattleapi.core.ports.RankingGateway;
import br.com.ada.moviesbattleapi.infrastructure.repository.GameRankingRepository;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.GameRankingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankingGatewayImpl implements RankingGateway {

    @Autowired
    private GameRankingRepository gameRankingRepository;

    @Override
    public Ranking save(Ranking ranking) {
        GameRankingEntity entity = GameRankingEntityMapper.from(ranking);
        return GameRankingEntityMapper.map(gameRankingRepository.save(entity));
    }

    @Override
    public List<Ranking> findRanking() {
        return gameRankingRepository.findAll(Sort.by(Sort.Direction.DESC, "score"))
                .stream().map(GameRankingEntityMapper::map)
                .collect(Collectors.toList());
    }
}
