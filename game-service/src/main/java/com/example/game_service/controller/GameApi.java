package com.example.game_service.controller;

import com.example.game_service.commons.constants.ApiPathVariables;
import com.example.game_service.commons.entities.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping(ApiPathVariables.v1_ROUTE + ApiPathVariables.GAME_ROUTE)
public interface GameApi {
    @PostMapping
    ResponseEntity<String> saveGame(@RequestBody Game game);
    @GetMapping
    ResponseEntity<List<Game>> getAll();
    @GetMapping("/{id}")
    ResponseEntity<String> getGameById(@PathVariable Long id);
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteGame(@PathVariable Long id);
    @PutMapping
    ResponseEntity<String> putGame(@RequestBody Game game);
}
