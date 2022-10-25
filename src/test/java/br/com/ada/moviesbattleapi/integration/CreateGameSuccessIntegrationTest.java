package br.com.ada.moviesbattleapi.integration;

import br.com.ada.moviesbattleapi.MoviesBattleApiApplication;
import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.response.GameResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {MoviesBattleApiApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class CreateGameSuccessIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @DisplayName("Must create a new game session for user")
    @ParameterizedTest
    @CsvSource(
            value = {
                    "player1:pwd1",
                    "player2:pwd2",
                    "player3:pwd3"
            },
            delimiter = ':'
    )
    public void mustCreateNewGameForPlayer(String username, String password)  {
        ResponseEntity<GameResponse> result = template.withBasicAuth(username, password)
                .getForEntity("/game/start", GameResponse.class);

        GameResponse body = result.getBody();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(username, body.getUsername());
    }

}
