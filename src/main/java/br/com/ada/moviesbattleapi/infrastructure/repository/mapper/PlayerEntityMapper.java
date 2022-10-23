package br.com.ada.moviesbattleapi.infrastructure.repository.mapper;

import br.com.ada.moviesbattleapi.core.domain.Player;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.PlayerEntity;

public class PlayerEntityMapper {

    public static Player map(PlayerEntity entity) {
        return new Player(entity.getUsername());
    }

    public static PlayerEntity from(Player player) {

        PlayerEntity entity = new PlayerEntity();
        entity.setUsername(player.getUsername());

        return entity;
    }

}
