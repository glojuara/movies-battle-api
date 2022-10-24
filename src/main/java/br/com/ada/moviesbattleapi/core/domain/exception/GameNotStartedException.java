package br.com.ada.moviesbattleapi.core.domain.exception;

public class GameNotStartedException extends RuntimeException {

    public GameNotStartedException(String message) {
        super(message);
    }

}
