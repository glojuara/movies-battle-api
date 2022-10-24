package br.com.ada.moviesbattleapi.core.domain.exception;

public class WrongAnswerException extends RuntimeException {

    public WrongAnswerException(String message) {
        super(message);
    }

}
