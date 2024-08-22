package com.example.game_service.controller.impl;

import com.example.game_service.commons.entities.Game;
import com.example.game_service.controller.GameApi;
import com.example.game_service.service.impl.GameServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GameController implements GameApi {

    private final GameServiceImpl gameService;

    public GameController(GameServiceImpl gameService) {
        this.gameService = gameService;
    }

    public ResponseEntity<String> saveGame(@RequestBody Game requestBody){
        Game game = this.gameService.saveGame(requestBody);
        return ResponseEntity.ok("The game: "+ game.toString() +" has been successfully saved");
    }

    public ResponseEntity<List<Game>> getAll(){
        List<Game> games= this.gameService.getAllGames();
        return ResponseEntity.ok(games);
    }

    public ResponseEntity<String> getGameById(@PathVariable Long id){
        Game game= this.gameService.getGameById(id);
        return ResponseEntity.ok(game.getName() + ": /n" + game.toString());
    }

    public ResponseEntity<Game> deleteGame(@PathVariable Long id){
        this.gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Game> putGame(@PathVariable Long id, @RequestBody Game requestBody){
        this.gameService.putGame(id, requestBody);
        return ResponseEntity.noContent().build();
    }

}
