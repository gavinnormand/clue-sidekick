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
     * Constructor to create a ClueGame with a list of players, number of players,
     * and all cards.
     *
     * @param players    The list of players in the game.
     * @param numPlayers The number of players in the game.
     * @param allCards   The list of all cards in the game.
     */
    public ClueGame(ArrayList<Player> players, int numPlayers, ArrayList<ACard> allCards) {
        this.players = players;
        this.numPlayers = numPlayers;
        this.allCards = allCards;
    }

    /**
     * Handles a player's guess in the game that noone says yes to
     *
     * @param player The player making the guess.
     * @param guess  The guess made by the player, which includes a suspect, weapon,
     *               and room.
     */
    void makeGuess(Player guesser, Guess guess) {
        for (int i = 1; i < this.players.size(); i++) {
            int index = (this.players.indexOf(guesser) + i) % this.players.size();
            this.players.get(index).updateDefinitleyDontHave(guess.getCards());
        }
    }

    /**
     * Handles a player's guess in the game that the second given player says yes to
     *
     * @param guesser The player making the guess.
     * @param guess  The guess made by the player, which includes a suspect, weapon,
     *               and room.
     * @param responder The player who responds to the guess
     */
    void makeGuess(Player guesser, Guess guess, Player responder) {
        for (int i = 1; i <= this.playersBetween(guesser, responder); i++) {
            int index = (this.players.indexOf(guesser) + i) % this.players.size();
            System.out.println(this.players.get(index));
        }
    }

    /**
     * Determine how many players are between the guesser and responder
     * @param guesser - The player making the guess
     * @param responder - The player responding to the guess
     * @return The number of players between the guesser and responder
     */
    private int playersBetween(Player guesser, Player responder) {
        for (int i = 0; i < this.players.size(); i++) {
            int index = (this.players.indexOf(guesser) + i) % this.players.size();
            if (this.players.get(index).equals(responder)) {
                return i - 1;
            }
        }
        throw new IllegalArgumentException("Responder is not a player in the game");
    }
}