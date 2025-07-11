<script setup lang="ts">
import { ref, computed } from "vue";
import type { GameInfo, TurnInfo } from "../types";
import MultiSelect from "../FormComponents/MultiSelect.vue";

const props = defineProps({
  gameInfo: {
    type: Object as () => GameInfo,
    required: true,
  },
});

const emit = defineEmits<{
  "turn-complete": [turn: TurnInfo];
  "game-won": [];
  "failed-player-cards": [data: { player: string; cards: string[] }];
}>();

const currentTurn = ref<Partial<TurnInfo>>({
  suspect: "",
  weapon: "",
  room: "",
  showedBy: null,
  cardShown: null,
  isFinalGuess: false,
});

const chatPhase = ref<
  | "asking-guess"
  | "asking-who-showed"
  | "asking-what-card"
  | "asking-final-result"
  | "asking-failed-player-cards"
>("asking-guess");

const currentPlayerIndex = ref(0);
const failedGuesser = ref<string>("");
const selectedFailedPlayerCards = ref<string[]>([]);

const activePlayers = computed(
  () => props.gameInfo.activePlayers || props.gameInfo.playerInfo.players,
);

const currentPlayer = computed(
  () =>
    activePlayers.value[currentPlayerIndex.value % activePlayers.value.length],
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
    case "asking-final-result":
      return `Was ${currentTurn.value.player}'s final guess correct?`;
    case "asking-failed-player-cards":
      return `What cards does ${failedGuesser.value} have? (Select all their cards)`;
    default:
      return "";
  }
});

function getOtherPlayers(): string[] {
  const players = activePlayers.value;
  const startIdx = currentPlayerIndex.value % players.length;
  const ordered: string[] = [];

  for (let i = 1; i < players.length; i++) {
    const idx = (startIdx + i) % players.length;
    ordered.push(players[idx]);
  }
  return ordered;
}

function getAllPossibleCards(): string[] {
  return [
    ...props.gameInfo.boardInfo.suspects,
    ...props.gameInfo.boardInfo.weapons,
    ...props.gameInfo.boardInfo.rooms,
  ];
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

  if (currentTurn.value.isFinalGuess) {
    chatPhase.value = "asking-final-result";
  } else {
    chatPhase.value = "asking-who-showed";
  }
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

function handleFinalGuessResult(wasCorrect: boolean) {
  currentTurn.value.wasCorrect = wasCorrect;

  if (wasCorrect) {
    emit("turn-complete", currentTurn.value as TurnInfo);
    emit("game-won");
  } else {
    failedGuesser.value = currentTurn.value.player!;
    emit("turn-complete", currentTurn.value as TurnInfo);
    chatPhase.value = "asking-failed-player-cards";
  }
}

function submitFailedPlayerCards() {
  if (selectedFailedPlayerCards.value.length === 0) {
    alert("Please select at least one card");
    return;
  }

  emit("failed-player-cards", {
    player: failedGuesser.value,
    cards: selectedFailedPlayerCards.value,
  });

  // Reset
  selectedFailedPlayerCards.value = [];
  failedGuesser.value = "";
  currentTurn.value = {
    suspect: "",
    weapon: "",
    room: "",
    showedBy: null,
    cardShown: null,
    isFinalGuess: false,
  };

  currentPlayerIndex.value =
    (currentPlayerIndex.value + 1) % activePlayers.value.length;
  chatPhase.value = "asking-guess";
}

function completeTurn() {
  emit("turn-complete", currentTurn.value as TurnInfo);

  currentTurn.value = {
    suspect: "",
    weapon: "",
    room: "",
    showedBy: null,
    cardShown: null,
    isFinalGuess: false,
  };

  currentPlayerIndex.value =
    (currentPlayerIndex.value + 1) % activePlayers.value.length;
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
  <div class="w-full">
    <h2 class="mb-2 text-2xl font-bold">Clue Assistant</h2>
    <div class="rounded-lg border-2 border-black bg-white p-6">
      <div
        v-if="gameInfo.turnHistory && gameInfo.turnHistory.length > 0"
        class="mb-3 rounded"
      >
        <p class="text-sm text-black">
          Turn {{ gameInfo.turnHistory.length + 1 }} • Current Player:
          <span class="font-semibold">{{ currentPlayer }}</span>
        </p>
      </div>
      <p class="text-lg font-semibold text-gray-800">{{ currentQuestion }}</p>
      <div v-if="chatPhase === 'asking-guess'" class="space-y-3">
        <div class="flex items-center space-x-3 rounded-md pt-3">
          <label class="relative inline-flex cursor-pointer items-center">
            <input
              type="checkbox"
              id="finalGuess"
              v-model="currentTurn.isFinalGuess"
              class="peer sr-only"
            />
            <div
              class="peer h-6 w-11 rounded-full bg-gray-200 transition-colors duration-300 peer-checked:bg-green-500"
            ></div>
            <div
              class="absolute left-1 top-1 h-4 w-4 rounded-full bg-white transition-transform duration-300 peer-checked:translate-x-5"
            ></div>
          </label>
          <label for="finalGuess" class="font-medium text-gray-700">
            Final guess
          </label>
        </div>

        <div>
          <label class="mb-1 block text-sm font-medium text-gray-700">
            Suspect
          </label>
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
          <label class="mb-1 block text-sm font-medium text-gray-700">
            Weapon
          </label>
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
          <label class="mb-1 block text-sm font-medium text-gray-700">
            Room
          </label>
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

      <div v-else-if="chatPhase === 'asking-final-result'" class="space-y-4">
        <div class="rounded-md bg-background p-4">
          <p class="mb-2 font-medium">Final Guess:</p>
          <p>
            {{ currentTurn.suspect }} with {{ currentTurn.weapon }} in
            {{ currentTurn.room }}
          </p>
        </div>

        <div class="flex space-x-4">
          <button
            @click="handleFinalGuessResult(true)"
            class="flex-1 rounded-md bg-emerald-500 p-3 font-medium text-white hover:bg-emerald-600"
          >
            Correct
          </button>
          <button
            @click="handleFinalGuessResult(false)"
            class="flex-1 rounded-md bg-red-500 p-3 font-medium text-white hover:bg-red-600"
          >
            Incorrect
          </button>
        </div>
      </div>

      <div
        v-else-if="chatPhase === 'asking-failed-player-cards'"
        class="space-y-4"
      >
        <p class="mb-2 text-sm font-medium">
          Select all cards that {{ failedGuesser }} has:
        </p>

        <MultiSelect
          v-model="selectedFailedPlayerCards"
          :options="getAllPossibleCards()"
        />

        <button
          @click="submitFailedPlayerCards"
          :disabled="selectedFailedPlayerCards.length === 0"
          class="w-full rounded-md bg-emerald-500 p-3 text-white transition-colors hover:bg-emerald-600 disabled:bg-gray-300"
        >
          Submit Cards
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped></style>