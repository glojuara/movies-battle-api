package br.com.ada.moviesbattleapi.core.usecase;

import br.com.ada.moviesbattleapi.core.domain.*;
import br.com.ada.moviesbattleapi.infrastructure.repository.GameRepository;
import br.com.ada.moviesbattleapi.infrastructure.repository.MovieRepository;
import br.com.ada.moviesbattleapi.infrastructure.repository.PlayerRepository;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.GameEntity;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.MovieEntity;
import br.com.ada.moviesbattleapi.infrastructure.repository.entity.PlayerEntity;
import br.com.ada.moviesbattleapi.infrastructure.repository.mapper.GameEntityMapper;
import br.com.ada.moviesbattleapi.infrastructure.repository.mapper.MovieEntityMapper;
import br.com.ada.moviesbattleapi.infrastructure.repository.mapper.PlayerEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CreateGameUseCase {

    @Autowired
    private FindMoviesUseCase findMoviesUseCase;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Transactional
    public Game execute(String username) {

        GameEntity activeGameSession = gameRepository.findByStatusAndPlayerUsername("ACTIVE", username);
        if (Objects.nonNull(activeGameSession)) {
            return GameEntityMapper.map(activeGameSession);
        }

        PlayerEntity playerEntity = playerRepository.findByUsername(username);
        Player player = PlayerEntityMapper.map(playerEntity);

        List<Movie> movies = findMoviesUseCase.execute();

        Game game = new Game(player);
        game.buildRounds(movies);

        GameEntity gameEntity = GameEntityMapper.from(game);
        gameEntity.setPlayer(playerEntity);

        GameEntity savedEntity = gameRepository.save(gameEntity);
        return GameEntityMapper.map(savedEntity);
    }


}
