package com.cluesidekick.cluesidekick_api.dto;

import java.util.List;

public class FailedPlayerCardsDto {
    private String playerName;
    private List<String> cards;

    public FailedPlayerCardsDto() {
    }

    public FailedPlayerCardsDto(String playerName, List<String> cards) {
        this.playerName = playerName;
        this.cards = cards;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public List<String> getCards() {
        return cards;
    }

    public void setCards(List<String> cards) {
        this.cards = cards;
    }
}