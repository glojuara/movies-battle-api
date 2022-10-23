package br.com.ada.moviesbattleapi.infrastructure.repository.entity;

import javax.persistence.*;

@Entity
@Table(name = "ROUND_MOVIES")
public class RoundMovieEntity {

    @EmbeddedId
    private RoundMovieKey id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("round_id")
    private RoundEntity round;

    @ManyToOne
    @MapsId("movie_id")
    private MovieEntity movie;

    public RoundMovieKey getId() {
        return id;
    }

    public void setId(RoundMovieKey id) {
        this.id = id;
    }

    public RoundEntity getRound() {
        return round;
    }

    public void setRound(RoundEntity round) {
        this.round = round;
    }

    public MovieEntity getMovie() {
        return movie;
    }

    public void setMovie(MovieEntity movie) {
        this.movie = movie;
    }
}
