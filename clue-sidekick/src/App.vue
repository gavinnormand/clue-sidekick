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
const isServerStarting = ref(false);
const serverMessage = ref("");

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
  step.value = 3;
  isServerStarting.value = true;
  serverMessage.value = "Connecting to game server...";

  try {
    const controller = new AbortController();
    const timeoutId = setTimeout(() => {
      controller.abort();
      serverMessage.value =
        "Starting up the server... This may take 20-30 seconds on first load.";
    }, 5000);

    try {
      const result = await initializeGame(gameInfo.value, controller.signal);
      clearTimeout(timeoutId);
      definitelyHeldCards.value = result.definitelyHeldCards;
      gameInfo.value.activePlayers = gameInfo.value.playerInfo.players;
      console.log("Game initialized, held cards:", definitelyHeldCards.value);
    } catch (error: any) {
      if (error.name === "AbortError") {
        const result = await initializeGame(gameInfo.value);
        definitelyHeldCards.value = result.definitelyHeldCards;
        gameInfo.value.activePlayers = gameInfo.value.playerInfo.players;
      } else {
        throw error;
      }
    }
  } catch (error) {
    console.error("Failed to initialize game:", error);
    serverMessage.value = "Failed to connect to server. Please try again.";
    setTimeout(() => {
      isServerStarting.value = false;
      step.value = 2;
    }, 2000);
  } finally {
    isServerStarting.value = false;
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
  <div class="bg-backgroundHover flex min-h-screen flex-col">
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

      <!-- Loading Screen -->
      <div
        v-if="isServerStarting"
        class="flex flex-1 flex-col items-center justify-center"
      >
        <div class="text-center">
          <div class="mb-4">
            <div
              class="border-primary inline-block h-12 w-12 animate-spin rounded-full border-4 border-solid border-r-transparent"
            ></div>
          </div>
          <p class="text-lg text-gray-300">{{ serverMessage }}</p>
          <p
            v-if="serverMessage.includes('20-30 seconds')"
            class="mt-2 text-sm text-gray-400"
          >
            The free server needs to wake up after periods of inactivity.
            <br />
            Once started, the game will run smoothly!
          </p>
        </div>
      </div>

      <GameView
        v-if="step === 3 && !gameWon && !isServerStarting"
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