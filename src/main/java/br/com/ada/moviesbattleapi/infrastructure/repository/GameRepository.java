package br.com.ada.moviesbattleapi.infrastructure.repository;

import br.com.ada.moviesbattleapi.infrastructure.repository.entity.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity, Integer> {

    GameEntity findByStatusAndPlayerUsername(String status, String username);

}
