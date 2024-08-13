package com.example.game_service.service.impl;

import com.example.game_service.commons.entities.Game;
import com.example.game_service.commons.exceptions.GameException;
import com.example.game_service.repository.GameRepository;
import com.example.game_service.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game saveGame (Game gameRequest){
        Game newGame = gameRepository.save(gameRequest);
        return newGame;
    }

    public List<Game> getAllGames (){
        List<Game> games = gameRepository.findAll();
        return games;
    }

    public Game getGameById(Long id){
        return this.gameRepository.findById(id)
                .orElseThrow(() -> new GameException(HttpStatus.NOT_FOUND, "Error, finding game"));
    }

    public Game deleteGame(Long id){
        Game game = this.gameRepository.findById(id)
                .orElseThrow(() -> new GameException(HttpStatus.NOT_FOUND,"Error, finding game" ));
        gameRepository.deleteById(id);
        return game;
    }

    public Game putGame(Game requestBody){
        Game game = gameRepository.findById(requestBody.getId())
                .orElseThrow(() -> new GameException(HttpStatus.NOT_FOUND,"Error, finding game" ));
        gameRepository.save(game);
        return game;
        }
}
