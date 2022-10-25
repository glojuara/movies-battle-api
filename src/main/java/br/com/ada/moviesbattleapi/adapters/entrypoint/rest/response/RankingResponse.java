package br.com.ada.moviesbattleapi.adapters.entrypoint.rest.response;

import br.com.ada.moviesbattleapi.core.domain.Ranking;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RankingResponse {

    private String username;
    private Double score;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public static RankingResponse from(Ranking ranking) {
        RankingResponse rankingResponse = new RankingResponse();
        rankingResponse.setUsername(ranking.getUsername());
        rankingResponse.setScore(ranking.getScore());
        return rankingResponse;
    }


}
