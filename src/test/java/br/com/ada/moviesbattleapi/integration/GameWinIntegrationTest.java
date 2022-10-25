package br.com.ada.moviesbattleapi.integration;


import br.com.ada.moviesbattleapi.MoviesBattleApiApplication;
import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.request.QuizAnswerRequest;
import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.response.GameResponse;
import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.response.MovieResponse;
import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.response.RoundResponse;
import br.com.ada.moviesbattleapi.core.domain.enums.RoundStatus;
import br.com.ada.moviesbattleapi.infrastructure.repository.RoundRepository;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.RoundEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class GameWinIntegrationTest {


    @Autowired
    private TestRestTemplate template;

    @Value("${right-answer.message}")
    private String rightAnswerMessage;


    @Value("${end-game.message}")
    private String endGameMessage;

    @Autowired
    private RoundRepository roundRepository;

    @DisplayName("Must show congratulation message when have no more rounds")
    @Test
    public void mustShowCongratulationMessageWhenHaveNoMoreRounds() {

        final String username = "player1";
        final String password = "pwd1";

        final ResponseEntity<GameResponse> gameResponse = template.withBasicAuth(username, password)
                .getForEntity("/game/start", GameResponse.class);

        assertEquals(HttpStatus.OK, gameResponse.getStatusCode());


        GameResponse gameBody = gameResponse.getBody();
        Integer gameId = gameBody.getId();

        List<RoundEntity> rounds = roundRepository.findByGameIdAndStatus(gameId, RoundStatus.PENDING.name());

        for (int i = 0; i < rounds.size(); i++) {
            ResponseEntity<String> successMessage = getNextAndRightTheAnswer(gameBody, username, password);
            assertEquals(HttpStatus.OK, successMessage.getStatusCode());
            assertEquals(rightAnswerMessage, successMessage.getBody());
        }

        final ResponseEntity<String> roundResponse = template.withBasicAuth(username, password)
                .getForEntity("/game/quiz", String.class);

        assertEquals(HttpStatus.OK, roundResponse.getStatusCode());
        assertEquals(endGameMessage, roundResponse.getBody());




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


}
