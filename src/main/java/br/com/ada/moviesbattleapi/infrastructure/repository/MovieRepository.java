package br.com.ada.moviesbattleapi.infrastructure.repository;

import br.com.ada.moviesbattleapi.infrastructure.repository.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
}
