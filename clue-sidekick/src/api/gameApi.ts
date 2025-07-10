import type { GameInfo } from "../components/types";

const API_BASE_URL = import.meta.env.PROD
  ? "https://clue-sidekick-api.onrender.com/api"
  : "http://localhost:8080/api";

export async function initializeGame(gameInfo: GameInfo, signal?: AbortSignal) {
  const response = await fetch(`${API_BASE_URL}/initialize-game`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    credentials: "include",
    body: JSON.stringify({
      suspects: gameInfo.boardInfo.suspects,
      weapons: gameInfo.boardInfo.weapons,
      rooms: gameInfo.boardInfo.rooms,
      players: gameInfo.playerInfo.players,
      currentUser: gameInfo.playerInfo.currentUser,
      myCards: gameInfo.cardInfo.userCards,
      publicCards: gameInfo.cardInfo.shownCards,
    }),
    signal,
  });

  if (!response.ok) {
    throw new Error("Failed to initialize game");
  }

  return await response.json();
}

export async function processGuess(turn: any) {
  const response = await fetch(`${API_BASE_URL}/process-guess`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    credentials: "include",
    body: JSON.stringify(turn),
  });

  if (!response.ok) throw new Error("Failed to process guess");
  return response.json();
}

export async function revealFailedPlayerCards(
  playerName: string,
  cards: string[],
) {
  const response = await fetch(`${API_BASE_URL}/reveal-player-cards`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    credentials: "include",
    body: JSON.stringify({
      playerName: playerName,
      cards: cards,
    }),
  });

  if (!response.ok) throw new Error("Failed to reveal player cards");
  return response.json();
}
