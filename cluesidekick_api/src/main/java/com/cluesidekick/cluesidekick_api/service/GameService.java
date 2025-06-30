package com.cluesidekick.cluesidekick_api.service;

import com.cluesidekick.cluesidekick_api.dto.*;
import com.cluesidekick.cluesidekick_api.game_logic.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private ClueGame currentGame;
    private String currentUser;

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

    private List<String> getHeldCardNames() {
        ArrayList<ACard> heldCards = currentGame.getAllDefinitelyHeldCards();
        List<String> result = new ArrayList<>();
        for (ACard card : heldCards) {
            result.add(card.getName());
        }
        return result;
    }

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

    private ACard findCard(ArrayList<ACard> cards, String cardName) {
        for (ACard card : cards) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        throw new IllegalArgumentException("Card not found: " + cardName);
    }
}