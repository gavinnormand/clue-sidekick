package com.cluesidekick.cluesidekick_api.game_logic;

/**
 * This class represents a guess in the ClueGame.
 */
public class Guess {
    
    private Suspect suspect;
    private Weapon weapon;
    private Room room;

    /**
     * Constructor to create a guess with a given suspect, weapon, and room.
     *
     * @param suspect The suspect in the guess.
     * @param weapon The weapon in the guess.
     * @param room The room in the guess.
     */
    public Guess(Suspect suspect, Weapon weapon, Room room) {
        this.suspect = suspect;
        this.weapon = weapon;
        this.room = room;
    }

    public ACard getSuspect() {
        return this.suspect;
    }

    public ACard getWeapon() {
        return this.weapon;
    }

    public ACard getRoom() {
        return this.room;
    }




}
