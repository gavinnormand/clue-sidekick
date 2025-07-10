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
  boardInfo: BoardInfo;
  playerInfo: PlayerInfo;
  cardInfo: CardInfo;
  turnHistory: TurnInfo[];
  activePlayers?: string[];
}

export interface TurnInfo {
  player: string;
  suspect: string;            
  weapon: string;
  room: string;
  showedBy: string | null;
  cardShown: string | null;
  isFinalGuess?: boolean;
  wasCorrect?: boolean; 
}