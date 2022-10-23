package br.com.ada.moviesbattleapi.infrastructure.repository.mapper;

import br.com.ada.moviesbattleapi.core.domain.Game;
import br.com.ada.moviesbattleapi.core.domain.Player;
import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.GameEntity;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.PlayerEntity;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.RoundEntity;

import java.util.List;
import java.util.stream.Collectors;

public class GameEntityMapper {

    public static Game map(GameEntity entity) {
        List<Round> rounds = entity.getRounds().stream().map(RoundEntityMapper::map).collect(Collectors.toList());
        Player player = PlayerEntityMapper.map(entity.getPlayer());
        Game game = new Game(player, rounds);
        game.setId(entity.getId());
        return game;
    }

    public static GameEntity from(Game game) {
        GameEntity gameEntity = new GameEntity();

        List<RoundEntity> roundEntities = game.getRounds().stream().map(RoundEntityMapper::from)
                .collect(Collectors.toList());

        gameEntity.setRounds(roundEntities);
        roundEntities.forEach(roundEntity -> roundEntity.setGame(gameEntity));
        gameEntity.setStatus("ACTIVE");

        return gameEntity;
    }

}
