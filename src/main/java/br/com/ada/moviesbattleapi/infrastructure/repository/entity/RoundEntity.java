package br.com.ada.moviesbattleapi.infrastructure.repository.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ROUND")
public class RoundEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    @JsonIgnore
    private GameEntity game;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "round_movies",
            joinColumns = @JoinColumn(name = "round_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private List<MovieEntity> movies;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MovieEntity> getMovies() {
        return List.copyOf(movies);
    }

    public void setMovies(List<MovieEntity> movies) {
        this.movies = movies;
    }

    public void addMovie(MovieEntity movie) {
        this.movies.add(movie);
    }

    public void removeMovie(MovieEntity movie) {
        this.movies.removeIf(m -> m.getId() == movie.getId());
    }

    public void removeAllMovies() {
        this.movies = new ArrayList();
    }

}
