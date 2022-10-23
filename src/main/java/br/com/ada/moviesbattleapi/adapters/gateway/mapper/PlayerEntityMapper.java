package br.com.ada.moviesbattleapi.adapters.gateway.mapper;

import br.com.ada.moviesbattleapi.core.domain.Player;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.PlayerEntity;

import java.util.Objects;

public class PlayerEntityMapper {

    public static Player map(PlayerEntity entity) {
        if (Objects.isNull(entity)) return null;
        Player player = new Player(entity.getUsername());
        player.setId(entity.getId());
        return player;
    }

    public static PlayerEntity from(Player player) {
        if (Objects.isNull(player)) return null;
        PlayerEntity entity = new PlayerEntity();
        entity.setId(player.getId());
        entity.setUsername(player.getUsername());
        return entity;
    }

}
