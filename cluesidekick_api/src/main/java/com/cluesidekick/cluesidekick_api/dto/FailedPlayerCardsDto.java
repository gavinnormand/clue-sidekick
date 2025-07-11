package com.cluesidekick.cluesidekick_api.dto;

import java.util.List;

/**
 * FailedPlayerCardsDto represents the data transfer object for player cards
 * that were not guessed correctly.
 */
public class FailedPlayerCardsDto {
    private String playerName;
    private List<String> cards;

    /**
     * Default constructor for FailedPlayerCardsDto.
     * This constructor is used for serialization/deserialization purposes.
     */
    public FailedPlayerCardsDto() {
    }

    /**
     * Constructor for FailedPlayerCardsDto.
     * 
     * @param playerName the name of the player whose cards are being represented
     * @param cards      the list of cards held by the player that were not guessed
     *                   correctly
     */
    public FailedPlayerCardsDto(String playerName, List<String> cards) {
        this.playerName = playerName;
        this.cards = cards;
    }

    /**
     * Gets the name of the player whose cards are being represented.
     * 
     * @return the name of the player
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the name of the player whose cards are being represented.
     * 
     * @param playerName the name of the player
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Gets the list of cards held by the player that were not guessed correctly.
     * 
     * @return the list of cards
     */
    public List<String> getCards() {
        return cards;
    }

    /**
     * Sets the list of cards held by the player that were not guessed correctly.
     * 
     * @param cards the list of cards to set
     */
    public void setCards(List<String> cards) {
        this.cards = cards;
    }
}