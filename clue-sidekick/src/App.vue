<script setup lang="ts">
import Header from "./components/HeaderFooter/Header.vue";
import Footer from "./components/HeaderFooter/Footer.vue";
import Welcome from "./components/Welcome.vue";
import FormController from "./components/FormController.vue";
import GameView from "./components/GameView.vue";
import { ref } from "vue";
import type { GameInfo, TurnInfo } from "./components/types";
import {
  initializeGame,
  processGuess,
  revealFailedPlayerCards,
} from "./api/gameApi";
import GameWonScreen from "./components/GameWonScreen.vue";

const step = ref(1);
const loading = ref(false);

const gameInfo = ref<GameInfo>({
  boardInfo: {
    suspects: [],
    weapons: [],
    rooms: [],
  },
  playerInfo: {
    players: [],
    currentUser: "",
  },
  cardInfo: {
    shownCards: [],
    userCards: [],
  },
  turnHistory: [],
  activePlayers: [],
});

const definitelyHeldCards = ref<string[]>([]);

function updateGameInfo(newGameInfo: GameInfo) {
  gameInfo.value = newGameInfo;
}

function goToStep2() {
  step.value = 2;
}

async function goToStep3() {
  loading.value = true;
  step.value = 3;

  try {
    const result = await initializeGame(gameInfo.value);
    definitelyHeldCards.value = result.definitelyHeldCards;
    gameInfo.value.activePlayers = gameInfo.value.playerInfo.players;
    console.log("Game initialized, held cards:", definitelyHeldCards.value);
  } catch (error) {
    console.error("Failed to initialize game:", error);
    alert("Failed to connect to server. Please try again.");
    step.value = 2;
  } finally {
    loading.value = false;
  }
}

async function handleTurnComplete(turn: TurnInfo) {
  gameInfo.value.turnHistory = [...(gameInfo.value.turnHistory || []), turn];

  try {
    const result = await processGuess(turn);
    definitelyHeldCards.value = result.definitelyHeldCards;
    console.log("Updated held cards:", definitelyHeldCards.value);
  } catch (error) {
    console.error("Failed to process turn:", error);
  }
}

async function handleFailedPlayerCards(data: {
  player: string;
  cards: string[];
}) {
  try {
    const result = await revealFailedPlayerCards(data.player, data.cards);
    definitelyHeldCards.value = result.definitelyHeldCards;

    if (result.activePlayers) {
      gameInfo.value.activePlayers = result.activePlayers;
    }

    console.log(
      "Player eliminated, updated held cards:",
      definitelyHeldCards.value,
    );
  } catch (error) {
    console.error("Failed to reveal player cards:", error);
  }
}

const gameWon = ref(false);

function handleGameWon() {
  gameWon.value = true;
}

function startNewGame() {
  step.value = 1;
  gameWon.value = false;
  gameInfo.value = {
    boardInfo: { suspects: [], weapons: [], rooms: [] },
    playerInfo: { players: [], currentUser: "" },
    cardInfo: { shownCards: [], userCards: [] },
    turnHistory: [],
    activePlayers: [],
  };
  definitelyHeldCards.value = [];
}

function startGameWithSameSettings() {
  gameWon.value = false;
  gameInfo.value.cardInfo = { shownCards: [], userCards: [] };
  gameInfo.value.turnHistory = [];
  gameInfo.value.activePlayers = gameInfo.value.playerInfo.players;
  definitelyHeldCards.value = [];
  step.value = 2;
}
</script>

<template>
  <div class="flex min-h-screen flex-col bg-backgroundHover">
    <Header />
    <div class="flex flex-1 flex-col">
      <Welcome v-if="step === 1" @next="goToStep2" />

      <FormController
        v-if="step === 2"
        :modelValue="gameInfo"
        :startAtCardSelection="gameWon"
        @update:modelValue="updateGameInfo"
        @next="goToStep3"
      />

      <div v-if="loading" class="flex flex-1 items-center justify-center">
        <svg
          class="h-12 w-12 animate-spin text-emerald-500"
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
        >
          <circle
            class="opacity-25"
            cx="12"
            cy="12"
            r="10"
            stroke="currentColor"
            stroke-width="4"
          ></circle>
          <path
            class="opacity-75"
            fill="currentColor"
            d="M4 12a8 8 0 018-8v8z"
          ></path>
        </svg>
      </div>

      <GameView
        v-if="step === 3 && !gameWon"
        :modelValue="gameInfo"
        :definitelyHeldCards="definitelyHeldCards"
        @turn-complete="handleTurnComplete"
        @game-won="handleGameWon"
        @failed-player-cards="handleFailedPlayerCards"
      />

      <GameWonScreen
        v-if="gameWon"
        :game-info="gameInfo"
        @start-new-game="startNewGame"
        @start-same-settings="startGameWithSameSettings"
      />
    </div>
    <Footer />
  </div>
</template>
