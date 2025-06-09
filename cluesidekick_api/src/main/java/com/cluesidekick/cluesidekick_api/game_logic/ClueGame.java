package com.cluesidekick.cluesidekick_api.game_logic;

import java.util.ArrayList;

/**
 * This class represents the main game logic for the ClueSidekick application.
 */
public class ClueGame {

    private ArrayList<Player> players;
    private ArrayList<ACard> allCards;

    /**
     * Constructor to create a ClueGame with a list of players, number of players,
     * and all cards.
     *
     * @param players  The list of players in the game.
     * @param allCards The list of all cards in the game.
     */
    public ClueGame(ArrayList<Player> players, ArrayList<ACard> allCards, ArrayList<ACard> publicCards, ArrayList<ACard> myCards) {
        this.players = players;
        this.allCards = allCards;
        for (Player player : players) {
            player.updateDefinitelyDontHave(allCards);
            if (player.isMe()) {
                player.updateDefinitelyHave(myCards);
            } 
            else {
                player.updateDefinitelyDontHave(myCards);
            }
        }
    }

    /**
     * Handles a player's guess in the game that no one says yes to
     *
     * @param player The player making the guess.
     * @param guess  The guess made by the player, which includes a suspect, weapon,
     *               and room.
     */
    public void makeGuess(Player guesser, Guess guess) {
        for (int i = 1; i < this.players.size(); i++) {
            int index = (this.players.indexOf(guesser) + i) % this.players.size();

            this.players.get(index).updateDefinitelyDontHave(guess.getCards());
        }
        this.updateUntilStable();
    }

    /**
     * Handles a player's guess in the game that the second given player says yes to
     *
     * @param guesser   The player making the guess.
     * @param guess     The guess made by the player, which includes a suspect,
     *                  weapon,
     *                  and room.
     * @param responder The player who responds to the guess
     */
    public void makeGuess(Player guesser, Guess guess, Player responder) {
        for (int i = 1; i <= this.playersBetween(guesser, responder); i++) {
            int index = (this.players.indexOf(guesser) + i) % this.players.size();

            this.players.get(index).updateDefinitelyDontHave(guess.getCards());
        }
        responder.addConditional(new Conditional(guess.getCards()));
        this.updateUntilStable();
    }

    /**
     * Marks a player as having a specific card, and updates all other players to
     * not have that card.
     *
     * @param player The player who has the card.
     * @param card   The card that the player has.
     */
    public void markPlayerHasCard(Player player, ACard card) {
        player.updateDefinitelyHave(card);
        for (Player otherPlayer : players) {
            if (!otherPlayer.equals(player)) {
                otherPlayer.updateDefinitelyDontHave(card);
            }
        }
    }

    /**
     * Handles a player's guess in the game that the second given player says yes
     * to, when you are the guesser
     *
     * @param guesser   The player making the guess, which is yourself.
     * @param guess     The guess made by the player, which includes a suspect,
     *                  weapon,
     *                  and room.
     * @param responder The player who responds to the guess
     */
    public void makeGuess(Player guesser, Guess guess, Player responder, ACard shown) {
        for (int i = 1; i <= this.playersBetween(guesser, responder); i++) {
            int index = (this.players.indexOf(guesser) + i) % this.players.size();

            this.players.get(index).updateDefinitelyDontHave(guess.getCards());
        }
        this.markPlayerHasCard(responder, shown);
        this.updateUntilStable();
    }

    /**
     * Determine how many players are between the guesser and responder
     * 
     * @param guesser   - The player making the guess
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

    /**
     * Performs a single update pass for all players, resolving any conditionals
     * they may have.
     *
     * @return true if any changes were made, false otherwise.
     */
    private boolean performSingleUpdatePass() {
        boolean anyChanges = false;

        for (Player player : players) {
            ArrayList<ACard> resolved = player.attemptToResolveConditionals();
            for (ACard card : resolved) {
                anyChanges = true;
                markPlayerHasCard(player, card);
            }
        }

        return anyChanges;
    }

    /**
     * Updates the game state until no more changes can be made.
     * This method will repeatedly call performSingleUpdatePass until it returns
     * false, indicating that no further updates are possible.
     */
    public void updateUntilStable() {
        while (performSingleUpdatePass()) {
            // Keep updating until no more changes
        }
    }

    public ArrayList<ACard> getAllDefinitelyHeldCards() {
        ArrayList<ACard> definitelyHeldCards = new ArrayList<>();
        for (Player player : players) {
            definitelyHeldCards.addAll(player.getDefinitelyHave());
        }
        return definitelyHeldCards;
    }
}