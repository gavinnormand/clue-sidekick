package com.cluesidekick.cluesidekick_api.dto;

import java.util.List;

public class GameStateUpdateDto {
    private List<String> definitelyHeldCards;
    private List<String> activePlayers;

    public GameStateUpdateDto() {
    }

    public GameStateUpdateDto(List<String> definitelyHeldCards, List<String> activePlayers) {
        this.definitelyHeldCards = definitelyHeldCards;
        this.activePlayers = activePlayers;
    }

    public List<String> getDefinitelyHeldCards() {
        return definitelyHeldCards;
    }

    public void setDefinitelyHeldCards(List<String> definitelyHeldCards) {
        this.definitelyHeldCards = definitelyHeldCards;
    }

    public List<String> getActivePlayers() {
        return activePlayers;
    }

    public void setActivePlayers(List<String> activePlayers) {
        this.activePlayers = activePlayers;
    }
}