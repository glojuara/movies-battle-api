package br.com.ada.moviesbattleapi.integration;


import br.com.ada.moviesbattleapi.MoviesBattleApiApplication;
import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.request.QuizAnswerRequest;
import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.response.GameResponse;
import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.response.MovieResponse;
import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.response.RoundResponse;
import br.com.ada.moviesbattleapi.core.domain.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureWireMock(port = 0)
@SpringBootTest(classes = {MoviesBattleApiApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RankingIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @DisplayName("Must show 2 results on ranking")
    @Test
    public void mustShowGameOverWhenPlayerWrong3Times() {

        Player player1 = new Player("player1");
        Player player2 = new Player("player2");


        final ResponseEntity<GameResponse> game1 = template.withBasicAuth(player1.getUsername(), "pwd1")
                .getForEntity("/game/start", GameResponse.class);

        getNextAndRightTheAnswer(game1.getBody(), player1.getUsername(), "pwd1");
        getNextAndRightTheAnswer(game1.getBody(), player1.getUsername(), "pwd1");
        getNextAndWrongTheAnswer(game1.getBody(), player1.getUsername(), "pwd1");
        getNextAndRightTheAnswer(game1.getBody(), player1.getUsername(), "pwd1");
        template.withBasicAuth(player1.getUsername(), "pwd1").getForEntity("/game/stop", GameResponse.class);

        final ResponseEntity<GameResponse> game2 = template.withBasicAuth(player2.getUsername(), "pwd2")
                .getForEntity("/game/start", GameResponse.class);


        getNextAndRightTheAnswer(game2.getBody(), player2.getUsername(), "pwd2");
        getNextAndWrongTheAnswer(game2.getBody(), player2.getUsername(), "pwd2");
        getNextAndWrongTheAnswer(game2.getBody(), player2.getUsername(), "pwd2");
        getNextAndWrongTheAnswer(game2.getBody(), player2.getUsername(), "pwd2");

        ResponseEntity<List> rankingResponse = template.getForEntity("/ranking", List.class);

        assertEquals(HttpStatus.OK, rankingResponse.getStatusCode());
        assertEquals(2, rankingResponse.getBody().size());

    }


    private ResponseEntity<String> getNextAndRightTheAnswer(GameResponse gameResponse, String username, String password) {
        final ResponseEntity<RoundResponse> roundResponse = template.withBasicAuth(username, password)
                .getForEntity("/game/quiz", RoundResponse.class);

        RoundResponse roundBody = roundResponse.getBody();

        assertEquals(HttpStatus.OK, roundResponse.getStatusCode());
        assertEquals(2, roundBody.getMovies().size());

        MovieResponse movie = roundBody.getMovies().stream().max(Comparator.comparing(MovieResponse::getId))
                .orElseGet(null);

        QuizAnswerRequest quizAnswerRequest = new QuizAnswerRequest();
        quizAnswerRequest.setGameId(gameResponse.getId());
        quizAnswerRequest.setRoundId(roundBody.getId());
        quizAnswerRequest.setMovieId(movie.getId());

        return template.withBasicAuth(username, password)
                .postForEntity("/game/quiz/answer", quizAnswerRequest, String.class);
    }

    private ResponseEntity<String> getNextAndWrongTheAnswer(GameResponse gameResponse, String username, String password) {
        final ResponseEntity<RoundResponse> roundResponse = template.withBasicAuth(username, password)
                .getForEntity("/game/quiz", RoundResponse.class);

        RoundResponse roundBody = roundResponse.getBody();

        assertEquals(HttpStatus.OK, roundResponse.getStatusCode());
        assertEquals(2, roundBody.getMovies().size());

        MovieResponse movie = roundBody.getMovies().stream().min(Comparator.comparing(MovieResponse::getId))
                .orElseGet(null);

        QuizAnswerRequest quizAnswerRequest = new QuizAnswerRequest();
        quizAnswerRequest.setGameId(gameResponse.getId());
        quizAnswerRequest.setRoundId(roundBody.getId());
        quizAnswerRequest.setMovieId(movie.getId());

        return template.withBasicAuth(username, password)
                .postForEntity("/game/quiz/answer", quizAnswerRequest, String.class);
    }

}
