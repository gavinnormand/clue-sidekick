export interface BoardInfo {
  suspects: string[];
  weapons: string[];
  rooms: string[];
}

export interface PlayerInfo {
  players: string[];
  currentUser: string;
}

export interface CardInfo {
  shownCards: string[];
  userCards: string[];
}

export interface GameInfo {
  board: BoardInfo;
  players: PlayerInfo;
  cards: CardInfo;
}