package com.cluesidekick.cluesidekick_api.game_logic;
import java.util.ArrayList;
import java.util.Arrays;


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

    /** Getter for Suspect
     * 
     * @return
     */
    public Suspect getSuspect() {
        return this.suspect;
    }


    /**
     * Getter for weapon
     * @return
     */
    public Weapon getWeapon() {
        return this.weapon;
    }

    /** Getter for room
     * 
     * @return
     */
    public Room getRoom() {
        return this.room;
    }

    /**
     * Returns an ArrayList of the cards in the guess.
     *
     * @return An ArrayList containing the suspect, weapon, and room.
     */
    public ArrayList<ACard> getCards() {
        return new ArrayList<ACard>(Arrays.asList(this.suspect, this.weapon, this.room));
    }




}
