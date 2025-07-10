package com.cluesidekick.cluesidekick_api.dto;

public class GuessDto {
    private String player;
    private String suspect;
    private String weapon;
    private String room;
    private String showedBy;
    private String cardShown;
    private Boolean isFinalGuess;
    private Boolean wasCorrect;

    public GuessDto() {
    }

    public GuessDto(String player, String suspect, String weapon, String room,
            String showedBy, String cardShown) {
        this.player = player;
        this.suspect = suspect;
        this.weapon = weapon;
        this.room = room;
        this.showedBy = showedBy;
        this.cardShown = cardShown;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getSuspect() {
        return suspect;
    }

    public void setSuspect(String suspect) {
        this.suspect = suspect;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getShowedBy() {
        return showedBy;
    }

    public void setShowedBy(String showedBy) {
        this.showedBy = showedBy;
    }

    public String getCardShown() {
        return cardShown;
    }

    public void setCardShown(String cardShown) {
        this.cardShown = cardShown;
    }

    public Boolean getIsFinalGuess() {
        return isFinalGuess;
    }

    public void setIsFinalGuess(Boolean isFinalGuess) {
        this.isFinalGuess = isFinalGuess;
    }

    public Boolean getWasCorrect() {
        return wasCorrect;
    }

    public void setWasCorrect(Boolean wasCorrect) {
        this.wasCorrect = wasCorrect;
    }
}