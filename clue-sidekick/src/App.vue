<script setup lang="ts">
import Header from "./components/Header.vue";
import Welcome from "./components/Welcome.vue";
import FormController from "./components/FormController.vue";
import GameView from "./components/GameView.vue";
import { ref } from "vue";
import type { GameInfo } from "./types/GameInfo";

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
});

function updateGameInfo(newGameInfo: GameInfo) {
  gameInfo.value = newGameInfo;
}

function goToStep2() {
  step.value = 2;
}

function goToStep3() {
  step.value = 3;
}
</script>

<template>
  <div class="bg-background">
    <Header />
    <Welcome v-if="step === 1" @next="goToStep2" />

    <FormController
      v-if="step === 2"
      :modelValue="gameInfo"
      @update:modelValue="updateGameInfo"
      @next="goToStep3"
    />

    <GameView v-if="step === 3" :modelValue="gameInfo" />
  </div>
</template>
