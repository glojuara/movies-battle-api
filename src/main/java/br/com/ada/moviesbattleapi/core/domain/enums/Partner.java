package br.com.ada.moviesbattleapi.core.domain.enums;

public enum Partner {
    OMDB("omdbapi");

    private final String partnerName;

    Partner(final String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerName() {
        return partnerName;
    }
}
