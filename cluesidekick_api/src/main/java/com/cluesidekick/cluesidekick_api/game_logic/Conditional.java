package com.cluesidekick.cluesidekick_api.game_logic;

import java.util.ArrayList;

/**
 * This class represents a conditional probability in the ClueGame.
 */
public class Conditional {

    // The list of cards that the player definitely has at least one of
    ArrayList<ACard> possibleCards;

    public Conditional(ArrayList<ACard> possibleCards) {
        this.possibleCards = possibleCards;
    }

    /**
     * Returns the card that is definitely held by the player based on the
     * conditional and the cards that the player definitely does not have.
     * 
     * @param definitelyDontHave - The list of cards that the player definitely does
     *                           not have.
     * @return The card that is definitely held by the player, or errors if not
     *         resolved.
     */
    public ACard resolve(ArrayList<ACard> definitelyDontHave) {
        ArrayList<ACard> possibleCards = new ArrayList<>(this.possibleCards);
        possibleCards.removeAll(definitelyDontHave);
        if (possibleCards.size() != 1) {
            return null;
        } else {
            return possibleCards.get(0);
        }

    }
}