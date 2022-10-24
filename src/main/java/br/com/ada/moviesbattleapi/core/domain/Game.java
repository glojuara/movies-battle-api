package br.com.ada.moviesbattleapi.core.domain;

import br.com.ada.moviesbattleapi.core.domain.builders.RoundsBuilder;
import br.com.ada.moviesbattleapi.core.domain.enums.GameStatus;
import br.com.ada.moviesbattleapi.core.domain.exception.GameException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.*;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {

    private Integer id;
    @JsonIgnore
    private Player player;
    private GameStatus gameStatus;
    @JsonIgnore
    private List<Round> rounds;

    public Game(Player player) {
        if (Objects.isNull(player)) {
            throw new GameException("The player is required");
        }
        this.player = player;
    }

    public Game(Player player, List<Round> rounds) {
        this(player);
        if (Objects.isNull(rounds)) {
            throw new GameException("The rounds is required");
        }
        this.rounds = new ArrayList(rounds);
    }

    public void buildRounds(List<Movie> movies) {
        this.rounds = new ArrayList(RoundsBuilder.buildRounds(this, movies));
    }

    public Player getPlayer() {
        return player;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void removeRound(Round round) {
        this.rounds.removeIf(r -> r.getId() == round.getId());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getUsername() {
        return this.getPlayer().getUsername();
    }
}
