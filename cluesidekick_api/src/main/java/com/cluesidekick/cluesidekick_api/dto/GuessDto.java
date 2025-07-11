package com.cluesidekick.cluesidekick_api.dto;

/**
 * Data Transfer Object representing a guess in the Clue Sidekick application.
 * Contains information about the player, suspect, weapon, room, who showed a
 * card,
 * which card was shown, and whether the guess was final or correct.
 */
public class GuessDto {
    private String player;
    private String suspect;
    private String weapon;
    private String room;
    private String showedBy;
    private String cardShown;
    private Boolean isFinalGuess;
    private Boolean wasCorrect;

    /**
     * Default constructor for GuessDto.
     */
    public GuessDto() {
    }

    /**
     * Constructs a GuessDto with the specified player, suspect, weapon, room,
     * showedBy, and cardShown.
     *
     * @param player    the player making the guess
     * @param suspect   the suspect guessed
     * @param weapon    the weapon guessed
     * @param room      the room guessed
     * @param showedBy  the player who showed a card (if any)
     * @param cardShown the card that was shown (if any)
     */
    public GuessDto(String player, String suspect, String weapon, String room,
            String showedBy, String cardShown) {
        this.player = player;
        this.suspect = suspect;
        this.weapon = weapon;
        this.room = room;
        this.showedBy = showedBy;
        this.cardShown = cardShown;
    }

    /**
     * Gets the player who made the guess.
     *
     * @return the player's name
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Sets the player who made the guess.
     *
     * @param player the player's name
     */
    public void setPlayer(String player) {
        this.player = player;
    }

    /**
     * Gets the suspect guessed.
     *
     * @return the suspect's name
     */
    public String getSuspect() {
        return suspect;
    }

    /**
     * Sets the suspect guessed.
     *
     * @param suspect the suspect's name
     */
    public void setSuspect(String suspect) {
        this.suspect = suspect;
    }

    /**
     * Gets the weapon guessed.
     *
     * @return the weapon's name
     */
    public String getWeapon() {
        return weapon;
    }

    /**
     * Sets the weapon guessed.
     *
     * @param weapon the weapon's name
     */
    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    /**
     * Gets the room guessed.
     *
     * @return the room's name
     */
    public String getRoom() {
        return room;
    }

    /**
     * Sets the room guessed.
     *
     * @param room the room's name
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * Gets the player who showed a card.
     *
     * @return the name of the player who showed a card
     */
    public String getShowedBy() {
        return showedBy;
    }

    /**
     * Sets the player who showed a card.
     *
     * @param showedBy the name of the player who showed a card
     */
    public void setShowedBy(String showedBy) {
        this.showedBy = showedBy;
    }

    /**
     * Gets the card that was shown.
     *
     * @return the name of the card shown
     */
    public String getCardShown() {
        return cardShown;
    }

    /**
     * Sets the card that was shown.
     *
     * @param cardShown the name of the card shown
     */
    public void setCardShown(String cardShown) {
        this.cardShown = cardShown;
    }

    /**
     * Gets whether this guess is the final guess.
     *
     * @return true if this is the final guess, false otherwise
     */
    public Boolean getIsFinalGuess() {
        return isFinalGuess;
    }

    /**
     * Sets whether this guess is the final guess.
     *
     * @param isFinalGuess true if this is the final guess, false otherwise
     */
    public void setIsFinalGuess(Boolean isFinalGuess) {
        this.isFinalGuess = isFinalGuess;
    }

    /**
     * Gets whether this guess was correct.
     *
     * @return true if the guess was correct, false otherwise
     */
    public Boolean getWasCorrect() {
        return wasCorrect;
    }

    /**
     * Sets whether this guess was correct.
     *
     * @param wasCorrect true if the guess was correct, false otherwise
     */
    public void setWasCorrect(Boolean wasCorrect) {
        this.wasCorrect = wasCorrect;
    }
}