package com.cluesidekick.cluesidekick_api.service;

import com.cluesidekick.cluesidekick_api.dto.*;
import com.cluesidekick.cluesidekick_api.game_logic.*;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing the Clue Sidekick game logic and state.
 */
@Service
public class GameService {

    private ClueGame currentGame;
    private String currentUser;

    /**
     * Initializes a new game with the provided setup and returns the cards
     * definitely held by the current user.
     *
     * @param gameSetup the setup information for the game
     * @return HeldCardsDto containing the cards definitely held by the user
     */
    public HeldCardsDto initializeGame(GameSetupDto gameSetup) {
        this.currentUser = gameSetup.getCurrentUser();

        ArrayList<Player> players = new ArrayList<>();
        for (String playerName : gameSetup.getPlayers()) {
            if (playerName.equals(currentUser)) {
                players.add(new Player("Me"));
            } else {
                players.add(new Player(playerName));
            }
        }

        ArrayList<ACard> allCards = new ArrayList<>();
        for (String suspect : gameSetup.getSuspects()) {
            allCards.add(new Suspect(suspect));
        }
        for (String weapon : gameSetup.getWeapons()) {
            allCards.add(new Weapon(weapon));
        }
        for (String room : gameSetup.getRooms()) {
            allCards.add(new Room(room));
        }

        ArrayList<ACard> myCards = new ArrayList<>();
        for (String cardName : gameSetup.getMyCards()) {
            myCards.add(findCard(allCards, cardName));
        }

        ArrayList<ACard> publicCards = new ArrayList<>();
        for (String cardName : gameSetup.getPublicCards()) {
            publicCards.add(findCard(allCards, cardName));
        }

        this.currentGame = new ClueGame(players, allCards, publicCards, myCards);

        return new HeldCardsDto(getHeldCardNames());
    }

    /**
     * Processes a guess made by a player and updates the game state accordingly.
     *
     * @param guessDto the guess information
     * @return HeldCardsDto containing the cards definitely held by the user after
     *         processing the guess
     */
    public HeldCardsDto processGuess(GuessDto guessDto) {
        if (currentGame == null) {
            throw new IllegalStateException("Game not initialized");
        }

        Player guesser = findPlayer(guessDto.getPlayer());

        ArrayList<ACard> allCards = currentGame.getAllCards();
        ACard suspectCard = findCard(allCards, guessDto.getSuspect());
        ACard weaponCard = findCard(allCards, guessDto.getWeapon());
        ACard roomCard = findCard(allCards, guessDto.getRoom());

        Guess guess = new Guess(
                (Suspect) suspectCard,
                (Weapon) weaponCard,
                (Room) roomCard);

        if (guessDto.getShowedBy() == null) {
            currentGame.makeGuess(guesser, guess);
        } else {
            Player responder = findPlayer(guessDto.getShowedBy());

            if (guessDto.getCardShown() != null) {
                ACard shownCard = findCard(allCards, guessDto.getCardShown());
                currentGame.makeGuess(guesser, guess, responder, shownCard);
            } else {
                currentGame.makeGuess(guesser, guess, responder);
            }
        }

        return new HeldCardsDto(getHeldCardNames());
    }

    /**
     * Reveals the cards of a failed player and eliminates them from the game.
     *
     * @param failedPlayerCards the player and their revealed cards
     * @return GameStateUpdateDto containing updated held cards and active players
     */
    public GameStateUpdateDto revealPlayerCards(FailedPlayerCardsDto failedPlayerCards) {
        if (currentGame == null) {
            throw new IllegalStateException("Game not initialized");
        }

        Player failedPlayer = findPlayer(failedPlayerCards.getPlayerName());

        ArrayList<ACard> allCards = currentGame.getAllCards();
        for (String cardName : failedPlayerCards.getCards()) {
            ACard card = findCard(allCards, cardName);
            currentGame.markPlayerHasCard(failedPlayer, card);
        }

        currentGame.eliminatePlayer(failedPlayer);

        return new GameStateUpdateDto(getHeldCardNames(), getActivePlayerNames());
    }

    /**
     * Gets the names of all cards definitely held by the current user.
     *
     * @return list of held card names
     */
    private List<String> getHeldCardNames() {
        ArrayList<ACard> heldCards = currentGame.getAllDefinitelyHeldCards();
        List<String> result = new ArrayList<>();
        for (ACard card : heldCards) {
            result.add(card.getName());
        }
        return result;
    }

    /**
     * Gets the names of all active players in the game.
     *
     * @return list of active player names
     */
    private List<String> getActivePlayerNames() {
        List<String> activeNames = new ArrayList<>();
        for (Player player : currentGame.getPlayers()) {
            if (player.isMe()) {
                activeNames.add(currentUser);
            } else {
                activeNames.add(player.getName());
            }
        }
        return activeNames;
    }

    /**
     * Finds a player by name.
     *
     * @param playerName the name of the player to find
     * @return the Player object
     * @throws IllegalArgumentException if the player is not found
     */
    private Player findPlayer(String playerName) {
        for (Player player : currentGame.getPlayers()) {
            if (player.isMe() && playerName.equals(currentUser)) {
                return player;
            }
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        throw new IllegalArgumentException("Player not found: " + playerName);
    }

    /**
     * Finds a card by name from the provided list.
     *
     * @param cards    the list of cards to search
     * @param cardName the name of the card to find
     * @return the ACard object
     * @throws IllegalArgumentException if the card is not found
     */
    private ACard findCard(ArrayList<ACard> cards, String cardName) {
        for (ACard card : cards) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        throw new IllegalArgumentException("Card not found: " + cardName);
    }
}