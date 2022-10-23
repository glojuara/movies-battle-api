package br.com.ada.moviesbattleapi.infrastructure.repository;

import br.com.ada.moviesbattleapi.infrastructure.repository.entity.RoundMovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundMoviesRepository extends JpaRepository<RoundMovieEntity, Integer> {

    void deleteByRoundId(Integer roundId);

}
