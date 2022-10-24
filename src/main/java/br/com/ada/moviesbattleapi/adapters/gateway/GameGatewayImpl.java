package br.com.ada.moviesbattleapi.adapters.gateway;

import br.com.ada.moviesbattleapi.core.domain.Game;
import br.com.ada.moviesbattleapi.core.ports.GameGateway;
import br.com.ada.moviesbattleapi.infrastructure.repository.GameRepository;
import br.com.ada.moviesbattleapi.infrastructure.repository.PlayerRepository;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.GameEntity;
import br.com.ada.moviesbattleapi.adapters.gateway.mapper.entity.GameEntityMapper;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.PlayerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameGatewayImpl implements GameGateway {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public Game findByStatusAndPlayerUsername(String status, String username) {
        GameEntity gameEntity = gameRepository.findFirstByStatusAndPlayerUsername(status, username);
        return GameEntityMapper.map(gameEntity);
    }

    @Override
    public Game findById(Integer id) {
        GameEntity gameEntity = gameRepository.findById(id).orElse(null);
        return GameEntityMapper.map(gameEntity);
    }

    @Override
    public Game save(Game game) {
        PlayerEntity playerEntity = playerRepository.findByUsername(game.getPlayer().getUsername());
        GameEntity gameEntity = GameEntityMapper.from(game);
        gameEntity.setPlayer(playerEntity);
        return GameEntityMapper.map(gameRepository.save(gameEntity));
    }
}
