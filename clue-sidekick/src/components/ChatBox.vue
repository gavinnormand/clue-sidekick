<script setup lang="ts">
import { ref, computed } from "vue";
import type { GameInfo, TurnInfo } from "./types";

const props = defineProps({
  gameInfo: {
    type: Object as () => GameInfo,
    required: true,
  },
});

const emit = defineEmits<{
  "turn-complete": [turn: TurnInfo];
}>();

const currentTurn = ref<Partial<TurnInfo>>({
  suspect: "",
  weapon: "",
  room: "",
  showedBy: null,
  cardShown: null,
});

const chatPhase = ref<
  "asking-guess" | "asking-who-showed" | "asking-what-card"
>("asking-guess");

const currentPlayerIndex = ref(0);

const currentPlayer = computed(
  () => props.gameInfo.playerInfo.players[currentPlayerIndex.value],
);

const isUsersTurn = computed(
  () => currentPlayer.value === props.gameInfo.playerInfo.currentUser,
);

const currentQuestion = computed(() => {
  switch (chatPhase.value) {
    case "asking-guess":
      return `${currentPlayer.value}'s turn: What did ${isUsersTurn.value ? "you" : "they"} guess?`;
    case "asking-who-showed":
      return `Who showed a card? (or did no one show?)`;
    case "asking-what-card":
      return `What card did ${currentTurn.value.showedBy} show you?`;
    default:
      return "";
  }
});

function getOtherPlayers(): string[] {
  const players = props.gameInfo.playerInfo.players;
  const startIdx = currentPlayerIndex.value;
  const ordered: string[] = [];

  for (let i = 1; i < players.length; i++) {
    const idx = (startIdx + i) % players.length;
    ordered.push(players[idx]);
  }
  return ordered;
}

function submitGuess() {
  if (
    !currentTurn.value.suspect ||
    !currentTurn.value.weapon ||
    !currentTurn.value.room
  ) {
    alert("Please select all three: suspect, weapon, and room");
    return;
  }

  currentTurn.value.player = currentPlayer.value;
  chatPhase.value = "asking-who-showed";
}

function selectWhoShowed(player: string | null) {
  currentTurn.value.showedBy = player;

  if (player === null) {
    completeTurn();
  } else if (isUsersTurn.value) {
    chatPhase.value = "asking-what-card";
  } else {
    currentTurn.value.cardShown = null;
    completeTurn();
  }
}

function selectCardShown(card: string) {
  currentTurn.value.cardShown = card;
  completeTurn();
}

function completeTurn() {
  emit("turn-complete", currentTurn.value as TurnInfo);

  currentTurn.value = {
    suspect: "",
    weapon: "",
    room: "",
    showedBy: null,
    cardShown: null,
  };

  currentPlayerIndex.value =
    (currentPlayerIndex.value + 1) % props.gameInfo.playerInfo.players.length;
  chatPhase.value = "asking-guess";
}

const canSubmitGuess = computed(
  () =>
    currentTurn.value.suspect &&
    currentTurn.value.weapon &&
    currentTurn.value.room,
);
</script>

<template>
  <div class="w-full max-w-2xl">
    <h2 class="mb-2 text-2xl font-bold">Clue Assistant</h2>
    <div class="rounded-lg border-2 border-black bg-white p-6">
      <div
        v-if="gameInfo.turnHistory && gameInfo.turnHistory.length > 0"
        class="mb-4 rounded"
      >
        <p class="text-sm text-black">
          Turn {{ gameInfo.turnHistory.length + 1 }} • Current Player:
          <span class="font-semibold">{{ currentPlayer }}</span>
        </p>
      </div>
      <div class="mb-4">
        <p class="text-lg font-semibold text-gray-800">{{ currentQuestion }}</p>
      </div>
      <div v-if="chatPhase === 'asking-guess'" class="space-y-3">
        <div>
          <label class="mb-1 block text-sm font-medium text-gray-700"
            >Suspect</label
          >
          <select
            v-model="currentTurn.suspect"
            class="w-full rounded-md border border-black bg-background p-2"
          >
            <option value="">Select suspect...</option>
            <option
              v-for="s in gameInfo.boardInfo.suspects"
              :key="s"
              :value="s"
            >
              {{ s }}
            </option>
          </select>
        </div>

        <div>
          <label class="mb-1 block text-sm font-medium text-gray-700"
            >Weapon</label
          >
          <select
            v-model="currentTurn.weapon"
            class="w-full rounded-md border border-black bg-background p-2"
          >
            <option value="">Select weapon...</option>
            <option v-for="w in gameInfo.boardInfo.weapons" :key="w" :value="w">
              {{ w }}
            </option>
          </select>
        </div>

        <div>
          <label class="mb-1 block text-sm font-medium text-gray-700"
            >Room</label
          >
          <select
            v-model="currentTurn.room"
            class="w-full rounded-md border border-black bg-background p-2"
          >
            <option value="">Select room...</option>
            <option v-for="r in gameInfo.boardInfo.rooms" :key="r" :value="r">
              {{ r }}
            </option>
          </select>
        </div>

        <button
          @click="submitGuess"
          :disabled="!canSubmitGuess"
          class="w-full rounded-md bg-emerald-500 px-4 py-2 text-white transition-colors hover:bg-emerald-600 disabled:cursor-not-allowed disabled:bg-gray-300"
        >
          Submit Guess
        </button>
      </div>

      <div v-else-if="chatPhase === 'asking-who-showed'" class="space-y-2">
        <p class="mb-3 text-sm text-gray-600">
          Players are asked in clockwise order:
        </p>
        <button
          v-for="player in getOtherPlayers()"
          :key="player"
          @click="selectWhoShowed(player)"
          class="hover:bg-backgroundHover block w-full rounded-md border border-black bg-background p-3 text-left transition-colors hover:border-blue-300"
        >
          {{ player }} showed a card
        </button>

        <button
          @click="selectWhoShowed(null)"
          class="block w-full rounded-md bg-red-500 p-3 font-medium text-white transition-colors hover:bg-red-600"
        >
          No one could show a card
        </button>
      </div>

      <div v-else-if="chatPhase === 'asking-what-card'" class="space-y-2">
        <p class="mb-3 text-sm text-gray-600">
          Which card did {{ currentTurn.showedBy }} show you?
        </p>

        <button
          @click="selectCardShown(currentTurn.suspect || '')"
          class="hover:bg-backgroundHover block w-full rounded-md border border-black bg-background p-3 text-left transition-colors"
        >
          {{ currentTurn.suspect }}
        </button>

        <button
          @click="selectCardShown(currentTurn.weapon || '')"
          class="hover:bg-backgroundHover block w-full rounded-md border border-black bg-background p-3 text-left transition-colors"
        >
          {{ currentTurn.weapon }}
        </button>

        <button
          @click="selectCardShown(currentTurn.room || '')"
          class="hover:bg-backgroundHover block w-full rounded-md border border-black bg-background p-3 text-left transition-colors"
        >
          {{ currentTurn.room }}
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
