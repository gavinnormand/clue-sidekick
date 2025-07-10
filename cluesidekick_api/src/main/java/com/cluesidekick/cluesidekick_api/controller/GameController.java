package com.cluesidekick.cluesidekick_api.controller;

import com.cluesidekick.cluesidekick_api.dto.*;
import com.cluesidekick.cluesidekick_api.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/initialize-game")
    public ResponseEntity<HeldCardsDto> initializeGame(@RequestBody GameSetupDto gameSetup) {
        return ResponseEntity.ok(gameService.initializeGame(gameSetup));
    }

    @PostMapping("/process-guess")
    public ResponseEntity<HeldCardsDto> processGuess(@RequestBody GuessDto guess) {
        return ResponseEntity.ok(gameService.processGuess(guess));
    }

    @PostMapping("/reveal-player-cards")
    public ResponseEntity<GameStateUpdateDto> revealPlayerCards(@RequestBody FailedPlayerCardsDto failedPlayerCards) {
        return ResponseEntity.ok(gameService.revealPlayerCards(failedPlayerCards));
    }
}