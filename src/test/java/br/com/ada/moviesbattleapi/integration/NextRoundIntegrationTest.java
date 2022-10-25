package br.com.ada.moviesbattleapi.integration;

import br.com.ada.moviesbattleapi.MoviesBattleApiApplication;
import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.response.GameResponse;
import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.response.RoundResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {MoviesBattleApiApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class NextRoundIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @DisplayName("Must failure to get next round when the game is not started")
    @Test
    public void mustFailureIfTryStopGameThatWasNotStarted() {

        final String username = "player1";
        final String password = "pwd1";

        ResponseEntity<RoundResponse> response = template.withBasicAuth(username, password)
                .getForEntity("/game/quiz", RoundResponse.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }


    @DisplayName("Must get next round when the game is started")
    @Test
    public void mustGetNextRoundWhenTheGameIsStarted() {

        final String username = "player1";
        final String password = "pwd1";

        ResponseEntity<GameResponse> gameResponse = template.withBasicAuth(username, password)
                .getForEntity("/game/start", GameResponse.class);

        assertEquals(HttpStatus.OK, gameResponse.getStatusCode());

        ResponseEntity<RoundResponse> roundResponse = template.withBasicAuth(username, password)
                .getForEntity("/game/quiz", RoundResponse.class);

        assertEquals(HttpStatus.OK, roundResponse.getStatusCode());
        assertEquals(2, roundResponse.getBody().getMovies().size());

    }

}
