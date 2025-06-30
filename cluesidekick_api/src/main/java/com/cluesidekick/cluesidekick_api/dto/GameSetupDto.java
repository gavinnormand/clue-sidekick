package com.cluesidekick.cluesidekick_api.dto;

import java.util.List;

public class GameSetupDto {
    private List<String> suspects;
    private List<String> weapons;
    private List<String> rooms;
    private List<String> players;
    private String currentUser;
    private List<String> myCards;
    private List<String> publicCards;
    
    public GameSetupDto() {
    }
    
    public GameSetupDto(List<String> suspects, List<String> weapons, List<String> rooms, 
                       List<String> players, String currentUser, List<String> myCards, 
                       List<String> publicCards) {
        this.suspects = suspects;
        this.weapons = weapons;
        this.rooms = rooms;
        this.players = players;
        this.currentUser = currentUser;
        this.myCards = myCards;
        this.publicCards = publicCards;
    }
    
    public List<String> getSuspects() {
        return suspects;
    }
    
    public void setSuspects(List<String> suspects) {
        this.suspects = suspects;
    }
    
    public List<String> getWeapons() {
        return weapons;
    }
    
    public void setWeapons(List<String> weapons) {
        this.weapons = weapons;
    }
    
    public List<String> getRooms() {
        return rooms;
    }
    
    public void setRooms(List<String> rooms) {
        this.rooms = rooms;
    }
    
    public List<String> getPlayers() {
        return players;
    }
    
    public void setPlayers(List<String> players) {
        this.players = players;
    }
    
    public String getCurrentUser() {
        return currentUser;
    }
    
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
    
    public List<String> getMyCards() {
        return myCards;
    }
    
    public void setMyCards(List<String> myCards) {
        this.myCards = myCards;
    }
    
    public List<String> getPublicCards() {
        return publicCards;
    }
    
    public void setPublicCards(List<String> publicCards) {
        this.publicCards = publicCards;
    }
}