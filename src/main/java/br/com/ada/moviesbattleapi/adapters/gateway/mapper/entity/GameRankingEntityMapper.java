package br.com.ada.moviesbattleapi.adapters.gateway.mapper.entity;

import br.com.ada.moviesbattleapi.core.domain.*;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.GameRankingEntity;
import java.util.Objects;

public class GameRankingEntityMapper {

    public static Ranking map(GameRankingEntity entity) {
        if (Objects.isNull(entity)) return null;
        Ranking ranking = new Ranking();
        ranking.setId(entity.getId());
        ranking.setGame(GameEntityMapper.map(entity.getGame()));
        ranking.setScore(entity.getScore());
        return ranking;
    }

    public static GameRankingEntity from(Ranking ranking) {
        if (Objects.isNull(ranking)) return null;
        GameRankingEntity entity = new GameRankingEntity();
        entity.setId(ranking.getId());
        entity.setGame(GameEntityMapper.from(ranking.getGame()));
        entity.setScore(ranking.getScore());
        return entity;
    }

}
