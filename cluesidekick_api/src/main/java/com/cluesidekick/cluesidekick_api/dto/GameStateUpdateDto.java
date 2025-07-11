package com.cluesidekick.cluesidekick_api.dto;

import java.util.List;

/**
 * GameStateUpdateDto represents the data transfer object for updating the game
 * state
 * in the Clue Sidekick application. It contains lists of cards that are
 * definitely held
 * by players and the active players in the game.
 */
public class GameStateUpdateDto {
    private List<String> definitelyHeldCards;
    private List<String> activePlayers;

    /**
     * Default constructor for GameStateUpdateDto.
     * This constructor is used for serialization/deserialization purposes.
     */
    public GameStateUpdateDto() {
    }

    /**
     * Constructor for GameStateUpdateDto.
     * Initializes the game state update with the provided lists of definitely held
     * cards
     * and active players.
     * 
     * @param definitelyHeldCards the list of cards that are definitely held by
     *                            players
     * @param activePlayers       the list of active players in the game
     */
    public GameStateUpdateDto(List<String> definitelyHeldCards, List<String> activePlayers) {
        this.definitelyHeldCards = definitelyHeldCards;
        this.activePlayers = activePlayers;
    }

    /**
     * Gets the list of cards that are definitely held by players.
     * 
     * @return the list of definitely held cards
     */
    public List<String> getDefinitelyHeldCards() {
        return definitelyHeldCards;
    }

    /**
     * Sets the list of cards that are definitely held by players.
     * 
     * @param definitelyHeldCards the list of cards to set
     */
    public void setDefinitelyHeldCards(List<String> definitelyHeldCards) {
        this.definitelyHeldCards = definitelyHeldCards;
    }

    /**
     * Gets the list of active players in the game.
     * 
     * @return the list of active players
     */
    public List<String> getActivePlayers() {
        return activePlayers;
    }

    /**
     * Sets the list of active players in the game.
     * 
     * @param activePlayers the list of active players to set
     */
    public void setActivePlayers(List<String> activePlayers) {
        this.activePlayers = activePlayers;
    }
}