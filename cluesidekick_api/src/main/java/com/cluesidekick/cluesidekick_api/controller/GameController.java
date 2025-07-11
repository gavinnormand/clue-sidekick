package com.cluesidekick.cluesidekick_api.controller;

import com.cluesidekick.cluesidekick_api.dto.*;
import com.cluesidekick.cluesidekick_api.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * GameController handles the game-related endpoints for the Clue Sidekick
 * application.
 * It provides endpoints to initialize a game, process guesses, and reveal
 * player cards.
 */
@RestController
@Service
@CrossOrigin(origins = { "https://cluesidekick.vercel.app" })
public class GameController {

    private final GameService gameService;

    /**
     * Constructor for GameController.
     * 
     * @param gameService The service that handles game logic and operations.
     */
    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Initializes a new game with the provided setup details.
     * 
     * @param gameSetup The setup details for the game, including player names and
     *                  game settings.
     * @return ResponseEntity containing the initial held cards for the game.
     */
    @PostMapping("/initialize-game")
    public ResponseEntity<HeldCardsDto> initializeGame(@RequestBody GameSetupDto gameSetup) {
        return ResponseEntity.ok(gameService.initializeGame(gameSetup));
    }

    /**
     * Processes a player's guess in the game.
     * 
     * @param guess The guess made by the player, including the suspect, weapon, and
     *              room.
     * @return ResponseEntity containing the updated held cards after processing the
     *         guess.
     */
    @PostMapping("/process-guess")
    public ResponseEntity<HeldCardsDto> processGuess(@RequestBody GuessDto guess) {
        return ResponseEntity.ok(gameService.processGuess(guess));
    }

    /**
     * Reveals the cards held by players that were not guessed correctly.
     * 
     * @param failedPlayerCards The details of the player cards that were not
     *                          guessed correctly, including the player ID and the
     *                          cards.
     * @return ResponseEntity containing the updated game state after revealing the
     *         player cards.
     */
    @PostMapping("/reveal-player-cards")
    public ResponseEntity<GameStateUpdateDto> revealPlayerCards(@RequestBody FailedPlayerCardsDto failedPlayerCards) {
        return ResponseEntity.ok(gameService.revealPlayerCards(failedPlayerCards));
    }
}