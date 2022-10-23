package br.com.ada.moviesbattleapi.infrastructure.repository.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RoundMovieKey implements Serializable {

    @Column(name = "round_id")
    private Integer roundId;
    @Column(name = "movie_id")
    private Integer movieId;

    public Integer getRoundId() {
        return roundId;
    }

    public void setRoundId(Integer roundId) {
        this.roundId = roundId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
}
