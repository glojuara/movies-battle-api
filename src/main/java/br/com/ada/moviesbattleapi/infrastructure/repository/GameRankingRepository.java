package br.com.ada.moviesbattleapi.infrastructure.repository;

import br.com.ada.moviesbattleapi.infrastructure.repository.entity.GameRankingEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GameRankingRepository extends JpaRepository<GameRankingEntity, Integer> {

}
