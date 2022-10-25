package br.com.ada.moviesbattleapi.integration;

import br.com.ada.moviesbattleapi.MoviesBattleApiApplication;
import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.response.GameResponse;
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
public class StartStopIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @DisplayName("Must create the game and end successfully")
    @Test
    public void mustCreateTheGameAndEndSuccessfully() {

        final String username = "player1";
        final String password = "pwd1";

        ResponseEntity<GameResponse> gameResponse = template.withBasicAuth(username, password)
                .getForEntity("/game/start", GameResponse.class);

        assertEquals(HttpStatus.OK, gameResponse.getStatusCode());

        ResponseEntity result = template.withBasicAuth(username, password)
                .getForEntity("/game/stop", String.class);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

    }

    @DisplayName("Must failure if try create a game more than one time")
    @Test
    public void mustFailureIfTryCreateGameMoreThanOneTime() {

        final String username = "player1";
        final String password = "pwd1";

        template.withBasicAuth(username, password)
                .getForEntity("/game/start", GameResponse.class);

        ResponseEntity<GameResponse> result = template.withBasicAuth(username, password)
                .getForEntity("/game/start", GameResponse.class);


        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());

    }


    @DisplayName("Must failure if try stop a game that was not started")
    @Test
    public void mustFailureIfTryStopGameThatWasNotStarted() {

        final String username = "player2";
        final String password = "pwd2";

        ResponseEntity<String> response = template.withBasicAuth(username, password)
                .getForEntity("/game/stop", String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

}
