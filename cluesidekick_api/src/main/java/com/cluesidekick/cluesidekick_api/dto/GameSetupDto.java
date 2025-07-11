package com.cluesidekick.cluesidekick_api.dto;

import java.util.List;

/**
 * GameSetupDto represents the data transfer object for setting up a game in the
 * Clue Sidekick application.
 * It contains lists of suspects, weapons, rooms, players, and the current
 * user's cards.
 */
public class GameSetupDto {
    private List<String> suspects;
    private List<String> weapons;
    private List<String> rooms;
    private List<String> players;
    private String currentUser;
    private List<String> myCards;
    private List<String> publicCards;

    /**
     * Default constructor for GameSetupDto.
     * This constructor is used for serialization/deserialization purposes.
     */
    public GameSetupDto() {
    }

    /**
     * Constructor for GameSetupDto.
     * Initializes the game setup with the provided lists of suspects, weapons,
     * rooms, players,
     * current user, and the player's cards.
     * 
     * @param suspects    the list of suspects in the game
     * @param weapons     the list of weapons in the game
     * @param rooms       the list of rooms in the game
     * @param players     the list of players in the game
     * @param currentUser the name of the current user
     * @param myCards     the list of cards held by the current user
     * @param publicCards the list of public cards that are shared among players
     */
    public GameSetupDto(List<String> suspects, List<String> weapons, List<String> rooms,
            List<String> players, String currentUser, List<String> myCards,
            List<String> publicCards) {
        this.suspects = suspects;
        this.weapons = weapons;
        this.rooms = rooms;
        this.players = players;
        this.currentUser = currentUser;
        this.myCards = myCards;
        this.publicCards = publicCards;
    }

    /**
     * Gets the list of suspects in the game.
     * 
     * @return the list of suspects
     */
    public List<String> getSuspects() {
        return suspects;
    }

    /**
     * Sets the list of suspects in the game.
     * 
     * @param suspects the list of suspects to set
     */
    public void setSuspects(List<String> suspects) {
        this.suspects = suspects;
    }

    /**
     * Gets the list of weapons in the game.
     * 
     * @return the list of weapons
     */
    public List<String> getWeapons() {
        return weapons;
    }

    /**
     * Sets the list of weapons in the game.
     * 
     * @param weapons the list of weapons to set
     */
    public void setWeapons(List<String> weapons) {
        this.weapons = weapons;
    }

    /**
     * Gets the list of rooms in the game.
     * 
     * @return the list of rooms
     */
    public List<String> getRooms() {
        return rooms;
    }

    /**
     * Sets the list of rooms in the game.
     * 
     * @param rooms the list of rooms to set
     */
    public void setRooms(List<String> rooms) {
        this.rooms = rooms;
    }

    /**
     * Gets the list of players in the game.
     * 
     * @return the list of players
     */
    public List<String> getPlayers() {
        return players;
    }

    /**
     * Sets the list of players in the game.
     * 
     * @param players the list of players to set
     */
    public void setPlayers(List<String> players) {
        this.players = players;
    }

    /**
     * Gets the name of the current user.
     * 
     * @return the name of the current user
     */
    public String getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the name of the current user.
     * 
     * @param currentUser the name of the current user
     */
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Gets the list of cards held by the current user.
     * 
     * @return the list of cards held by the current user
     */
    public List<String> getMyCards() {
        return myCards;
    }

    /**
     * Sets the list of cards held by the current user.
     * 
     * @param myCards the list of cards to set for the current user
     */
    public void setMyCards(List<String> myCards) {
        this.myCards = myCards;
    }

    /**
     * Gets the list of public cards that are shared among players.
     * 
     * @return the list of public cards
     */
    public List<String> getPublicCards() {
        return publicCards;
    }

    /**
     * Sets the list of public cards that are shared among players.
     * 
     * @param publicCards the list of public cards to set
     */
    public void setPublicCards(List<String> publicCards) {
        this.publicCards = publicCards;
    }
}