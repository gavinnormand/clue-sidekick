<script setup lang="ts">
import Header from "./components/Header.vue";
import Footer from "./components/Footer.vue";
import Welcome from "./components/Welcome.vue";
import FormController from "./components/FormController.vue";
import GameView from "./components/GameView.vue";
import { ref } from "vue";
import type { GameInfo, TurnInfo } from "./components/types";
import { initializeGame, processGuess } from "./api/gameApi";

const step = ref(1);

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

  try {
    const result = await initializeGame(gameInfo.value);
    definitelyHeldCards.value = result.definitelyHeldCards;
    console.log("Game initialized, held cards:", definitelyHeldCards.value);
  } catch (error) {
    console.error("Failed to initialize game:", error);
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
</script>

<template>
  <div class="flex min-h-screen flex-col bg-backgroundHover">
    <Header />
    <div class="flex-1">
      <Welcome v-if="step === 1" @next="goToStep2" />

      <FormController
        v-if="step === 2"
        :modelValue="gameInfo"
        @update:modelValue="updateGameInfo"
        @next="goToStep3"
      />

      <GameView
        v-if="step === 3"
        :modelValue="gameInfo"
        :definitelyHeldCards="definitelyHeldCards"
        @turn-complete="handleTurnComplete"
      />
    </div>
    <Footer />
  </div>
</template>
