const API_BASE = 'http://localhost:8080/api';

export async function initializeGame(gameInfo: any) {
  const response = await fetch(`${API_BASE}/initialize-game`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
      suspects: gameInfo.boardInfo.suspects,
      weapons: gameInfo.boardInfo.weapons,
      rooms: gameInfo.boardInfo.rooms,
      players: gameInfo.playerInfo.players,
      currentUser: gameInfo.playerInfo.currentUser,
      myCards: gameInfo.cardInfo.userCards,
      publicCards: gameInfo.cardInfo.shownCards
    })
  });
  
  if (!response.ok) throw new Error('Failed to initialize game');
  return response.json();
}

export async function processGuess(turn: any) {
  const response = await fetch(`${API_BASE}/process-guess`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(turn)
  });
  
  if (!response.ok) throw new Error('Failed to process guess');
  return response.json();
}