package br.com.ada.moviesbattleapi.core.domain.exception;

public class GameAlreadyStartedException extends RuntimeException {

    public GameAlreadyStartedException(String message) {
        super(message);
    }

}
