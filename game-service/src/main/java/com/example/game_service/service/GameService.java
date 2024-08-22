package com.example.game_service.service;

import com.example.game_service.commons.entities.Game;

import java.util.List;

public interface GameService {

    Game saveGame(Game gameRequest);
    List<Game> getAllGames();
    Game getGameById(Long id);
    void deleteGame(Long id);
}
