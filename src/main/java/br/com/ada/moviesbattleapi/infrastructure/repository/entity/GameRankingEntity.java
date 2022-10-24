package br.com.ada.moviesbattleapi.infrastructure.repository.entity;

import javax.persistence.*;

@Entity
@Table(name = "GAME_RANKING")
public class GameRankingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Double score;

    @OneToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private GameEntity game;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }
}
