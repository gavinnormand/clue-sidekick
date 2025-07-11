// dto/HeldCardsDto.java
package com.cluesidekick.cluesidekick_api.dto;

import java.util.List;

/**
 * Data Transfer Object representing the cards that are definitely held by a
 * player.
 */
public class HeldCardsDto {
    private List<String> definitelyHeldCards;

    /**
     * Default constructor for HeldCardsDto.
     */
    public HeldCardsDto() {
    }

    /**
     * Constructs a HeldCardsDto with the specified list of definitely held cards.
     *
     * @param definitelyHeldCards the list of cards that are definitely held
     */
    public HeldCardsDto(List<String> definitelyHeldCards) {
        this.definitelyHeldCards = definitelyHeldCards;
    }

    /**
     * Gets the list of cards that are definitely held.
     *
     * @return the list of definitely held cards
     */
    public List<String> getDefinitelyHeldCards() {
        return definitelyHeldCards;
    }

    /**
     * Sets the list of cards that are definitely held.
     *
     * @param definitelyHeldCards the list of definitely held cards
     */
    public void setDefinitelyHeldCards(List<String> definitelyHeldCards) {
        this.definitelyHeldCards = definitelyHeldCards;
    }
}