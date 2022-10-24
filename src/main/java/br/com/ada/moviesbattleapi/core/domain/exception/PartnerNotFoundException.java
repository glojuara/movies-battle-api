package br.com.ada.moviesbattleapi.core.domain.exception;

public class PartnerNotFoundException extends RuntimeException {

    public PartnerNotFoundException(String message) {
        super(message);
    }

}
