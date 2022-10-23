package br.com.ada.moviesbattleapi.infrastructure.repository;

import br.com.ada.moviesbattleapi.infrastructure.repository.entity.RoundEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoundRepository extends JpaRepository<RoundEntity, Integer> {

    List<RoundEntity> findByGameIdAndStatus(Integer gameId, String status);

}
