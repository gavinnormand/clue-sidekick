package com.cluesidekick.cluesidekick_api.game_logic;

import java.util.ArrayList;

/**
 * This class represents a conditional probability in the ClueGame.
 */
public class Conditional {
    
    // The list of cards that the player definitley has at least one of
    ArrayList<ACard> possibleCards;

    public Conditional(ArrayList<ACard> possibleCards) {
        this.possibleCards = possibleCards;
    }
}
