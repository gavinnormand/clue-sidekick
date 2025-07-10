package com.cluesidekick.cluesidekick_api.game_logic;

import java.util.ArrayList;

/**
 * This class represents the main game logic for the ClueSidekick application.
 */
public class ClueGame {

    private ArrayList<Player> players;
    private ArrayList<ACard> allCards;
    private ArrayList<ACard> publicCards;

    /**
     * Constructor to create a ClueGame with a list of players, number of players,
     * and all cards.
     *
     * @param players  The list of players in the game.
     * @param allCards The list of all cards in the game.
     */
    public ClueGame(ArrayList<Player> players, ArrayList<ACard> allCards, ArrayList<ACard> publicCards,
            ArrayList<ACard> myCards) {
        this.players = players;
        this.allCards = allCards;
        this.publicCards = publicCards;

        for (Player player : players) {
            if (player.isMe()) {
                player.updateDefinitelyHave(myCards);
                for (ACard card : allCards) {
                    if (!myCards.contains(card)) {
                        player.updateDefinitelyDontHave(card);
                    }
                }
            } else {
                player.updateDefinitelyDontHave(myCards);
            }
        }

        for (Player player : players) {
            player.updateDefinitelyDontHave(publicCards);
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

    /**
     * Gets all cards that are definitely held by any player in the game.
     * This method iterates through all players and collects cards that they have
     * marked as definitely held.
     * 
     * @return An ArrayList of ACard objects that are definitely held by players.
     */
    public ArrayList<ACard> getAllDefinitelyHeldCards() {
        ArrayList<ACard> definitelyHeldCards = new ArrayList<>(this.publicCards);
        for (Player player : players) {
            definitelyHeldCards.addAll(player.getDefinitelyHave());
        }

        addOtherCardsWhenSolutionFound(definitelyHeldCards);

        System.out.println("Definitely held cards: " + definitelyHeldCards);

        return definitelyHeldCards;
    }

    /**
     * When exactly one card of a type is not held, all OTHER cards of that type
     * must be marked as held (since one is in solution, all others must be held).
     * 
     * @param definitelyHeldCards - The list of cards that are definitely held by
     *                            players.
     */
    private void addOtherCardsWhenSolutionFound(ArrayList<ACard> definitelyHeldCards) {
        ArrayList<ACard> allSuspects = new ArrayList<>();
        ArrayList<ACard> allWeapons = new ArrayList<>();
        ArrayList<ACard> allRooms = new ArrayList<>();

        for (ACard card : allCards) {
            if (card instanceof Suspect) {
                allSuspects.add(card);
            } else if (card instanceof Weapon) {
                allWeapons.add(card);
            } else if (card instanceof Room) {
                allRooms.add(card);
            }
        }

        // First, find cards that nobody has (solution cards)
        ArrayList<ACard> solutionCards = findSolutionCards();

        // Now check each category
        addOthersIfSolutionFound(allSuspects, solutionCards, definitelyHeldCards);
        addOthersIfSolutionFound(allWeapons, solutionCards, definitelyHeldCards);
        addOthersIfSolutionFound(allRooms, solutionCards, definitelyHeldCards);
    }

    /**
     * Find cards that no player has (these must be in the solution).
     * 
     * @return An ArrayList of ACard objects that are solution cards.
     */
    private ArrayList<ACard> findSolutionCards() {
        ArrayList<ACard> solutionCards = new ArrayList<>();

        for (ACard card : allCards) {
            boolean isHeldByAnyone = false;

            if (publicCards.contains(card)) {
                isHeldByAnyone = true;
            }

            if (!isHeldByAnyone) {
                for (Player player : players) {
                    if (player.getDefinitelyHave().contains(card)) {
                        isHeldByAnyone = true;
                        break;
                    }
                }
            }

            if (!isHeldByAnyone) {
                boolean allPlayersDontHave = true;
                for (Player player : players) {
                    if (!player.getDefinitelyDontHave().contains(card)) {
                        allPlayersDontHave = false;
                        break;
                    }
                }

                if (allPlayersDontHave) {
                    solutionCards.add(card);
                    System.out.println("Solution card found: " + card.getName());
                }
            }
        }

        return solutionCards;
    }

    /**
     * If we found a solution card of this type, add all OTHER cards
     * of this type to the definitely held list.
     */
    private void addOthersIfSolutionFound(ArrayList<ACard> cardsOfType,
            ArrayList<ACard> solutionCards,
            ArrayList<ACard> definitelyHeldCards) {
        // Check if any card of this type is in the solution
        ACard solutionCardOfType = null;
        for (ACard card : cardsOfType) {
            if (solutionCards.contains(card)) {
                solutionCardOfType = card;
                break;
            }
        }

        // If we found a solution card of this type, all other cards of this type must
        // be held
        if (solutionCardOfType != null) {
            for (ACard card : cardsOfType) {
                if (!card.equals(solutionCardOfType) && !definitelyHeldCards.contains(card)) {
                    definitelyHeldCards.add(card);
                    System.out.println("Adding " + card.getName() + " to definitely held (solution found)");
                }
            }
        }
    }

    /**
     * Gets the list of players in the game.
     * 
     * @return An ArrayList of Player objects representing the players in the game.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Gets all cards in the game.
     * 
     * @return An ArrayList of ACard objects representing all cards in the game.
     */
    public ArrayList<ACard> getAllCards() {
        return allCards;
    }

    /**
     * Eliminates a player from the game, removing them from the rotation and
     * making all their cards public knowledge.
     * This method also updates all remaining players to know that they do not have
     * the eliminated player's cards.
     * 
     * @param player - The player to be eliminated from the game.
     */
    public void eliminatePlayer(Player player) {
        this.players.remove(player);

        ArrayList<ACard> playerCards = new ArrayList<>(player.getDefinitelyHave());
        this.publicCards.addAll(playerCards);

        for (Player remainingPlayer : this.players) {
            remainingPlayer.updateDefinitelyDontHave(playerCards);
        }

        this.updateUntilStable();
    }
}