package br.com.ada.moviesbattleapi.core.ports;

import br.com.ada.moviesbattleapi.core.domain.Ranking;

import java.util.List;

public interface RankingGateway {

    Ranking save(Ranking ranking);
    List<Ranking> findRanking();

}
