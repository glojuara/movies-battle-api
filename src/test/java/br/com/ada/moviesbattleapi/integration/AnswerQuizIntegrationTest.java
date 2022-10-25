package br.com.ada.moviesbattleapi.integration;

import br.com.ada.moviesbattleapi.MoviesBattleApiApplication;
import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.request.QuizAnswerRequest;
import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.response.GameResponse;
import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.response.MovieResponse;
import br.com.ada.moviesbattleapi.adapters.entrypoint.rest.response.RoundResponse;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureWireMock(port = 0)
@SpringBootTest(classes = {MoviesBattleApiApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnswerQuizIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    @Value("${right-answer.message}")
    private String rightAnswerMessage;

    @Value("${wrong-answer.message}")
    private String wrongAnswerMessage;

    @Value("${game-over.message}")
    private String gameOverMessage;

    @DisplayName("Must response with the right answer")
    @Test
    public void mustResponseWithRightAnswer() {

        final String username = "player1";
        final String password = "pwd1";

        final ResponseEntity<GameResponse> gameResponse = template.withBasicAuth(username, password)
                .getForEntity("/game/start", GameResponse.class);

        assertEquals(HttpStatus.OK, gameResponse.getStatusCode());

        final ResponseEntity<RoundResponse> roundResponse = template.withBasicAuth(username, password)
                .getForEntity("/game/quiz", RoundResponse.class);

        RoundResponse roundBody = roundResponse.getBody();

        assertEquals(HttpStatus.OK, roundResponse.getStatusCode());
        assertEquals(2, roundBody.getMovies().size());

        MovieResponse movie = roundBody.getMovies().stream().max(Comparator.comparing(MovieResponse::getId))
                .orElseGet(null);

        QuizAnswerRequest quizAnswerRequest = new QuizAnswerRequest();
        quizAnswerRequest.setGameId(gameResponse.getBody().getId());
        quizAnswerRequest.setRoundId(roundBody.getId());
        quizAnswerRequest.setMovieId(movie.getId());

        ResponseEntity<String> quizAnswerResponse = template.withBasicAuth(username, password)
                .postForEntity("/game/quiz/answer", quizAnswerRequest, String.class);

        assertEquals(HttpStatus.OK, quizAnswerResponse.getStatusCode());
        assertEquals(rightAnswerMessage, quizAnswerResponse.getBody());

    }

    @DisplayName("Must fail when the gameId is invalid")
    @Test
    public void mustFailWhenGameIdIsInvalid() {

        final String username = "player1";
        final String password = "pwd1";

        final ResponseEntity<GameResponse> gameResponse = template.withBasicAuth(username, password)
                .getForEntity("/game/start", GameResponse.class);

        assertEquals(HttpStatus.OK, gameResponse.getStatusCode());

        final ResponseEntity<RoundResponse> roundResponse = template.withBasicAuth(username, password)
                .getForEntity("/game/quiz", RoundResponse.class);

        RoundResponse roundBody = roundResponse.getBody();

        assertEquals(HttpStatus.OK, roundResponse.getStatusCode());
        assertEquals(2, roundBody.getMovies().size());

        MovieResponse movie = roundBody.getMovies().stream().max(Comparator.comparing(MovieResponse::getId))
                .orElseGet(null);

        QuizAnswerRequest quizAnswerRequest = new QuizAnswerRequest();
        quizAnswerRequest.setGameId(gameResponse.getBody().getId() + 1);
        quizAnswerRequest.setRoundId(roundBody.getId());
        quizAnswerRequest.setMovieId(movie.getId());

        ResponseEntity<String> quizAnswerResponse = template.withBasicAuth(username, password)
                .postForEntity("/game/quiz/answer", quizAnswerRequest, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, quizAnswerResponse.getStatusCode());

    }

    @DisplayName("Must fail when the roundId is invalid")
    @Test
    public void mustFailWhenRoundIdIsInvalid() {

        final String username = "player1";
        final String password = "pwd1";

        final ResponseEntity<GameResponse> gameResponse = template.withBasicAuth(username, password)
                .getForEntity("/game/start", GameResponse.class);

        assertEquals(HttpStatus.OK, gameResponse.getStatusCode());

        final ResponseEntity<RoundResponse> roundResponse = template.withBasicAuth(username, password)
                .getForEntity("/game/quiz", RoundResponse.class);

        RoundResponse roundBody = roundResponse.getBody();

        assertEquals(HttpStatus.OK, roundResponse.getStatusCode());
        assertEquals(2, roundBody.getMovies().size());

        MovieResponse movie = roundBody.getMovies().stream().max(Comparator.comparing(MovieResponse::getId))
                .orElseGet(null);

        QuizAnswerRequest quizAnswerRequest = new QuizAnswerRequest();
        quizAnswerRequest.setGameId(gameResponse.getBody().getId());
        quizAnswerRequest.setRoundId(roundBody.getId() + 1);
        quizAnswerRequest.setMovieId(movie.getId());

        ResponseEntity<String> quizAnswerResponse = template.withBasicAuth(username, password)
                .postForEntity("/game/quiz/answer", quizAnswerRequest, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, quizAnswerResponse.getStatusCode());

    }


    @DisplayName("Must fail when the movieId is invalid")
    @Test
    public void mustFailWhenMovieIdIsInvalid() {

        final String username = "player1";
        final String password = "pwd1";

        final ResponseEntity<GameResponse> gameResponse = template.withBasicAuth(username, password)
                .getForEntity("/game/start", GameResponse.class);

        assertEquals(HttpStatus.OK, gameResponse.getStatusCode());

        final ResponseEntity<RoundResponse> roundResponse = template.withBasicAuth(username, password)
                .getForEntity("/game/quiz", RoundResponse.class);

        RoundResponse roundBody = roundResponse.getBody();

        assertEquals(HttpStatus.OK, roundResponse.getStatusCode());
        assertEquals(2, roundBody.getMovies().size());

        MovieResponse movie = roundBody.getMovies().stream().max(Comparator.comparing(MovieResponse::getId))
                .orElseGet(null);

        QuizAnswerRequest quizAnswerRequest = new QuizAnswerRequest();
        quizAnswerRequest.setGameId(gameResponse.getBody().getId());
        quizAnswerRequest.setRoundId(roundBody.getId());
        quizAnswerRequest.setMovieId(movie.getId() + 1);

        ResponseEntity<String> quizAnswerResponse = template.withBasicAuth(username, password)
                .postForEntity("/game/quiz/answer", quizAnswerRequest, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, quizAnswerResponse.getStatusCode());

    }

    @DisplayName("Must response with the wrong answer")
    @Test
    public void mustResponseWithWrongAnswer() {

        final String username = "player1";
        final String password = "pwd1";

        final ResponseEntity<GameResponse> gameResponse = template.withBasicAuth(username, password)
                .getForEntity("/game/start", GameResponse.class);

        assertEquals(HttpStatus.OK, gameResponse.getStatusCode());

        ResponseEntity<String> quizAnswerResponse = getNextAndWrongTheAnswer(gameResponse.getBody(), username, password);
        assertEquals(HttpStatus.OK, quizAnswerResponse.getStatusCode());
        assertEquals(wrongAnswerMessage, quizAnswerResponse.getBody());

    }

    @DisplayName("Must show game over when player wrong 3 times")
    @Test
    public void mustShowGameOverWhenPlayerWrong3Times() {

        final String username = "player1";
        final String password = "pwd1";

        final ResponseEntity<GameResponse> gameResponse = template.withBasicAuth(username, password)
                .getForEntity("/game/start", GameResponse.class);

        assertEquals(HttpStatus.OK, gameResponse.getStatusCode());

        ResponseEntity<String> error1 = getNextAndWrongTheAnswer(gameResponse.getBody(), username, password);
        assertEquals(HttpStatus.OK, error1.getStatusCode());
        assertEquals(wrongAnswerMessage, error1.getBody());

        ResponseEntity<String> error2 = getNextAndWrongTheAnswer(gameResponse.getBody(), username, password);
        assertEquals(HttpStatus.OK, error2.getStatusCode());
        assertEquals(wrongAnswerMessage, error2.getBody());

        ResponseEntity<String> error3 = getNextAndWrongTheAnswer(gameResponse.getBody(), username, password);
        assertEquals(HttpStatus.OK, error3.getStatusCode());
        assertEquals(gameOverMessage, error3.getBody());
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
