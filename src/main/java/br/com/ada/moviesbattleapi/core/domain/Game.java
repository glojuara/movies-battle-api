package br.com.ada.moviesbattleapi.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.*;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {

    private Integer id;
    private Player player;
    private List<Round> rounds;
    private Iterator<Round> roundIterator;

    public Game(Player player) {
        if (Objects.isNull(player)) {
            throw new GameException("The player is required");
        }
        this.player = player;
    }

    public Game(Player player, List<Round> rounds) {
        this(player);
        if (Objects.isNull(rounds) || rounds.isEmpty()) {
            throw new GameException("The rounds is required");
        }
        this.rounds = List.copyOf(rounds);
        this.roundIterator = this.rounds.iterator();
    }

    public Round nextRound() {
        return roundIterator.hasNext() ? roundIterator.next() : null;
    }

    public void buildRounds(List<Movie> movies) {
        List<Round> rounds = List.copyOf(RoundsBuilder.buildRounds(this, movies));
        this.rounds = rounds;
        this.roundIterator = this.rounds.iterator();
    }

    public Player getPlayer() {
        return player;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
