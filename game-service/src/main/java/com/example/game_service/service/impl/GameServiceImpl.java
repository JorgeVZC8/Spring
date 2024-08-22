package com.example.game_service.service.impl;

import com.example.game_service.commons.entities.Game;
import com.example.game_service.commons.exceptions.GameException;
import com.example.game_service.repository.GameRepository;
import com.example.game_service.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game saveGame (Game gameRequest){
        return gameRepository.save(gameRequest);
    }

    public List<Game> getAllGames (){
        return gameRepository.findAll();
    }

    public Game getGameById(Long id){
        return this.gameRepository.findById(id)
                .orElseThrow(() -> new GameException(HttpStatus.NOT_FOUND, "Error, finding game"));
    }

    public void deleteGame(Long id){
        gameRepository.deleteById(id);
    }

    public Game putGame(Long id, Game requestBody){
        return Optional.of(requestBody)
                .map(game -> {
                    game.setId(id);
                    return gameRepository.save(game);
                })
                .orElseThrow(() -> new GameException(HttpStatus.NOT_FOUND, "Error, finding game"));
        }
}
