package com.cluesidekick.cluesidekick_api.game_logic;

import java.util.ArrayList;

/**
 * This class represents the main game logic for the ClueSidekick application.
 */
public class ClueGame {
    
    private ArrayList<Player> players;
    private int numPlayers;
    private ArrayList<ACard> allCards;

    /**
     * Constructor to create a ClueGame with a list of players, number of players, and all cards.
     *
     * @param players The list of players in the game.
     * @param numPlayers The number of players in the game.
     * @param allCards The list of all cards in the game.
     */
    public ClueGame(ArrayList<Player> players, int numPlayers, ArrayList<ACard> allCards) {
        this.players = players;
        this.numPlayers = numPlayers;
        this.allCards = allCards;
    }
}
