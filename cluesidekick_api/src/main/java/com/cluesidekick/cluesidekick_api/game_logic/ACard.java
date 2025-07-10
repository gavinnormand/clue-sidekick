package com.cluesidekick.cluesidekick_api.game_logic;

/**
 * This class represents a card in the ClueGame.
 */
public abstract class ACard {

    private final String name;

    /**
     * Constructor to create a card with a given name.
     *
     * @param name The name of the card.
     */
    public ACard(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the card.
     * 
     * @return The name of the card, as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Determines if this ACard is equal to another object.
     * 
     * @param obj The object to compare with.
     * @return true if the object is an ACard with the same name, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ACard))
            return false;
        ACard other = (ACard) obj;
        return name.equals(other.name);
    }

    /**
     * Determines the hash code for this ACard.
     * 
     * @return The hash code based on the card's name.
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Returns a string representation of the ACard.
     * 
     * @return A string that includes the card's name.
     */
    @Override
    public String toString() {
        return "ACard{" +
                "name='" + name + '\'' +
                '}';
    }

}
