// dto/HeldCardsDto.java
package com.cluesidekick.cluesidekick_api.dto;

import java.util.List;

public class HeldCardsDto {
    private List<String> definitelyHeldCards;
    
    public HeldCardsDto() {}
    
    public HeldCardsDto(List<String> definitelyHeldCards) {
        this.definitelyHeldCards = definitelyHeldCards;
    }
    
    public List<String> getDefinitelyHeldCards() {
        return definitelyHeldCards;
    }
    
    public void setDefinitelyHeldCards(List<String> definitelyHeldCards) {
        this.definitelyHeldCards = definitelyHeldCards;
    }
}