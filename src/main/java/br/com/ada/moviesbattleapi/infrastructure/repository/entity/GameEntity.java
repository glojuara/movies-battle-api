package br.com.ada.moviesbattleapi.infrastructure.repository.entity;

import br.com.ada.moviesbattleapi.core.domain.Round;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GAME")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private PlayerEntity player;

    @OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST })
    @JoinColumn(name = "game_id")
    private List<RoundEntity> rounds = new ArrayList();

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public List<RoundEntity> getRounds() {
        return List.copyOf(this.rounds);
    }

    public void addRound(RoundEntity round) {
        this.rounds.add(round);
    }

    public void removeRound(RoundEntity round) {
        this.rounds.removeIf(r -> r.getId() == round.getId());
    }

    public void setRounds(List<RoundEntity> rounds) {
        this.rounds = rounds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
