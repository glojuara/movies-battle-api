package br.com.ada.moviesbattleapi.adapters.gateway;

import br.com.ada.moviesbattleapi.adapters.gateway.mapper.entity.PlayerEntityMapper;
import br.com.ada.moviesbattleapi.core.domain.Player;
import br.com.ada.moviesbattleapi.core.ports.PlayerGateway;
import br.com.ada.moviesbattleapi.infrastructure.repository.PlayerRepository;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.PlayerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerGatewayImpl implements PlayerGateway {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public Player findByUsername(String username) {
        PlayerEntity playerEntity = playerRepository.findByUsername(username);
        return PlayerEntityMapper.map(playerEntity);
    }


}
