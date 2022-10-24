package br.com.ada.moviesbattleapi.core.domain.builders;

import br.com.ada.moviesbattleapi.core.domain.Game;
import br.com.ada.moviesbattleapi.core.domain.Movie;
import br.com.ada.moviesbattleapi.core.domain.Round;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RoundsBuilder {

    public static List<Round> buildRounds(Game game, List<Movie> movies) {
        List<Round> rounds = new ArrayList<>();
        for (int i = 0; i < movies.size() -1 ; i++) {
            for (int j = i + 1; j < movies.size(); j++) {
                List<Movie> pairs = Arrays.asList(movies.get(i), movies.get(j));
                Collections.shuffle(pairs);
                rounds.add(new Round(game, pairs));
            }
        }
        Collections.shuffle(rounds);
        return List.copyOf(rounds);
    }

}
