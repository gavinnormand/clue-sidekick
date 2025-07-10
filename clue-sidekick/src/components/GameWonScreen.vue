<script setup lang="ts">
import type { GameInfo } from "./types";

const props = defineProps({
  gameInfo: {
    type: Object as () => GameInfo,
    required: true,
  },
});

const emit = defineEmits<{
  "start-new-game": [];
  "start-same-settings": [];
}>();

const winningTurn =
  props.gameInfo.turnHistory[props.gameInfo.turnHistory.length - 1];
</script>

<template>
   <div class="flex flex-1 items-center justify-center">
    <div class="max-w-md rounded-lg bg-white p-8 text-center shadow-lg">
      <h1 class="mb-4 text-4xl font-bold text-emerald-500">{{ winningTurn?.player }} Won!</h1>

      <div class="mb-4 rounded-md bg-background p-4">
        <p class="mb-2 text-lg font-semibold">Final Solution:</p>
        <p>
          {{ winningTurn?.suspect }}
          with the {{ winningTurn?.weapon }} in the {{ winningTurn?.room }}
        </p>
      </div>

      <div class="mb-4">
        <p class="text-sm text-gray-600">
          Solved in {{ gameInfo.turnHistory.length }} turns
        </p>
      </div>

      <div class="space-y-3">
        <button
          @click="emit('start-new-game')"
          class="w-full rounded-md bg-emerald-500 px-6 py-3 text-white transition-colors hover:bg-emerald-600"
        >
          Start New Game
        </button>

        <button
          @click="emit('start-same-settings')"
          class="w-full rounded-md border-2 border-emerald-500 bg-white px-6 py-3 text-emerald-500 transition-colors hover:bg-emerald-50"
        >
          Play Again with Same Settings
        </button>
      </div>
    </div>
  </div>
</template>
