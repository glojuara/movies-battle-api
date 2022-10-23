package br.com.ada.moviesbattleapi.adapters.gateway.mapper;

import br.com.ada.moviesbattleapi.core.domain.Game;
import br.com.ada.moviesbattleapi.core.domain.GameStatus;
import br.com.ada.moviesbattleapi.core.domain.Player;
import br.com.ada.moviesbattleapi.core.domain.Round;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.GameEntity;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.RoundEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GameEntityMapper {

    public static Game map(GameEntity entity) {
        if (Objects.isNull(entity)) return null;
        List<Round> rounds = entity.getRounds().stream().map(RoundEntityMapper::map).collect(Collectors.toList());
        Player player = PlayerEntityMapper.map(entity.getPlayer());
        Game game = new Game(player, rounds);
        game.setId(entity.getId());
        game.setGameStatus(GameStatus.valueOf(entity.getStatus()));
        return game;
    }

    public static GameEntity from(Game game) {
        if (Objects.isNull(game)) return null;

        GameEntity gameEntity = new GameEntity();

        List<RoundEntity> roundEntities = game.getRounds().stream().map(RoundEntityMapper::from)
                .collect(Collectors.toList());

        gameEntity.setId(game.getId());
        gameEntity.setRounds(roundEntities);
        gameEntity.setPlayer(PlayerEntityMapper.from(game.getPlayer()));
        roundEntities.forEach(roundEntity -> roundEntity.setGame(gameEntity));
        gameEntity.setStatus(game.getGameStatus().name());

        return gameEntity;
    }

}
