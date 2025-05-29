package com.cluesidekick.cluesidekick_api.game_logic;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This class represents a player in the ClueGame.
 */
public class Player {
    
    String name;
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
     * Constructor to create a player with a given name and a list of cards they definitely have.
     *
     * @param name The name of the player.
     * @param definitleyHave The list of cards that the player definitely has.
     */
    public Player(String name, ArrayList<ACard> definitleyHave) {
        this.name = name;
        this.definitleyHave = definitleyHave;
        this.definitleyDontHave = new ArrayList<>();
        this.conditionals = new ArrayList<>();
    }

    /**
     * Updates the player's list of cards that they definitely don't have based on the provided guess.
     *
     * @return The name of the player.
     */
    public void updateDefinitleyDontHave(ArrayList<ACard> cards) {
        this.definitleyDontHave.addAll(cards);
    }

    /**
     * Method to add condtional to a player
     */
    public void addConditional(Conditional cond) {
        this.conditionals.add(cond);
    }
}