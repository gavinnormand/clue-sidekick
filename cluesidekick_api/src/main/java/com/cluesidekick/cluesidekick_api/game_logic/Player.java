package com.cluesidekick.cluesidekick_api.game_logic;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents a player in the ClueGame.
 */
public class Player {

    private final String name;
    private ArrayList<ACard> definitelyHave;
    private ArrayList<ACard> definitelyDontHave;
    private ArrayList<Conditional> conditionals;

    /**
     * Constructor to create a player with a given name.
     *
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.definitelyHave = new ArrayList<>();
        this.definitelyDontHave = new ArrayList<>();
        this.conditionals = new ArrayList<>();
    }

    /**
     * Constructor to create a player with a given name and a list of cards they
     * definitely have.
     *
     * @param name           The name of the player.
     * @param definitelyHave The list of cards that the player definitely has.
     */
    public Player(String name, ArrayList<ACard> definitelyHave) {
        this.name = name;
        this.definitelyHave = definitelyHave;
        this.definitelyDontHave = new ArrayList<>();
        this.conditionals = new ArrayList<>();
    }

    /**
     * Determines if the user is this Player.
     * 
     * @return true if the player is "Me", false otherwise.
     */
    public boolean isMe() {
        return this.name.equals("Me");
    }

    /**
     * Returns the cards that the player definitely does have.
     * 
     * @return A collection of ACard objects that the player definitely has.
     */
    public Collection<ACard> getDefinitelyHave() {
        return this.definitelyHave;
    }

    /**
     * Adds a card to the player's definitely have list.
     *
     * @param card The card to add.
     */
    public void updateDefinitelyHave(ACard card) {
        if (!this.definitelyHave.contains(card)) {
            this.definitelyHave.add(card);
        }
    }

    /**
     * Adds a list of cards to the player's definitely have list.
     *
     * @param card The list of cards to add, as an ArrayList of ACard.
     */
    public void updateDefinitelyHave(ArrayList<ACard> cards) {
        for (ACard card : cards) {
            this.updateDefinitelyHave(card);
        }
    }

    /**
     * Updates the player's list of cards that they definitely don't have based on
     * the provided guess.
     * 
     * @param card The card to add to the definitely don't have list.
     */
    public void updateDefinitelyDontHave(ACard card) {
        if (!this.definitelyDontHave.contains(card)) {
            this.definitelyDontHave.add(card);
        }
    }

    /**
     * Updates the player's list of cards that they definitely don't have based on
     * the provided guess.
     *
     * @param cards The list of cards to add to the definitely don't have list.
     */
    public void updateDefinitelyDontHave(ArrayList<ACard> cards) {
        for (ACard card : cards) {
            this.updateDefinitelyDontHave(card);
        }
    }

    /**
     * Method to add conditional to a player
     */
    public void addConditional(Conditional cond) {
        this.conditionals.add(cond);
    }

    /**
     * Attempts to resolve the player's conditionals and returns a list of
     * cards that the player can definitely have based on the resolved conditionals.
     * 
     * @return An ArrayList of ACard objects that the player definitely has
     */
    public ArrayList<ACard> attemptToResolveConditionals() {
        ArrayList<ACard> resolvedCards = new ArrayList<>();
        ArrayList<Conditional> resolvedConditionals = new ArrayList<>();
        for (Conditional cond : this.conditionals) {
            ACard resolvedCard = cond.resolve(this.definitelyDontHave);
            if (resolvedCard != null) {
                resolvedCards.add(resolvedCard);
                resolvedConditionals.add(cond);
            }
        }
        this.conditionals.removeAll(resolvedConditionals);
        return resolvedCards;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Player))
            return false;
        Player other = (Player) obj;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}