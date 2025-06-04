package com.cluesidekick.cluesidekick_api.game_logic;

import java.util.ArrayList;

/**
 * This class represents a player in the ClueGame.
 */
public class Player {

    private final String name;
    private ArrayList<ACard> definitleyHave;
    private ArrayList<ACard> definitleyDontHave;
    private ArrayList<Conditional> conditionals;

    /**
     * Constructor to create a player with a given name.
     *
     * @param name The name of the player.
     */
    public Player(String name) {
        this.name = name;
        this.definitleyHave = new ArrayList<>();
        this.definitleyDontHave = new ArrayList<>();
        this.conditionals = new ArrayList<>();
    }

    /**
     * Constructor to create a player with a given name and a list of cards they
     * definitely have.
     *
     * @param name           The name of the player.
     * @param definitleyHave The list of cards that the player definitely has.
     */
    public Player(String name, ArrayList<ACard> definitleyHave) {
        this.name = name;
        this.definitleyHave = definitleyHave;
        this.definitleyDontHave = new ArrayList<>();
        this.conditionals = new ArrayList<>();
    }

    /**
     * Adds a card to the player's definitely have list.
     *
     * @param card The card to add.
     */
    public void updateDefinitleyHave(ACard card) {
        if (!this.definitleyHave.contains(card)) {
            this.definitleyHave.add(card);
        }
    }

    /**
     * Updates the player's list of cards that they definitely don't have based on
     * the provided guess.
     * 
     * @param card The card to add to the definitely don't have list.
     */
    public void updateDefinitleyDontHave(ACard card) {
        if (!this.definitleyDontHave.contains(card)) {
            this.definitleyDontHave.add(card);
        }
    }

    /** 
     * Updates the player's list of cards that they definitely don't have based on
     * the provided guess.
     *
     * @param cards The list of cards to add to the definitely don't have list.
     */
    public void updateDefinitleyDontHave(ArrayList<ACard> cards) {
        for (ACard card : cards) {
            if (!this.definitleyDontHave.contains(card)) {
                this.definitleyDontHave.add(card);
            }
        }
    }

    /**
     * Method to add condtional to a player
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
            ACard resolvedCard = cond.resolve(this.definitleyDontHave);
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