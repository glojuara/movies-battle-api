package br.com.ada.moviesbattleapi.infrastructure.repository;

import br.com.ada.moviesbattleapi.infrastructure.repository.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer> {

    PlayerEntity findByUsername(String username);

}
