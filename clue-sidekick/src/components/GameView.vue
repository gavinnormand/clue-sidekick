<script setup lang="ts">
import ScoreSheet from "./GameComponents/ScoreSheet.vue";
import type { GameInfo, TurnInfo } from "./types";
import ChatBox from "./GameComponents/ChatBox.vue";

const props = defineProps({
  modelValue: {
    type: Object as () => GameInfo,
    required: true,
  },
  definitelyHeldCards: {
    type: Array as () => string[],
    default: () => [],
  },
});

const emit = defineEmits<{
  "turn-complete": [turn: TurnInfo];
  "game-won": [];
  "failed-player-cards": [data: { player: string; cards: string[] }];
}>();

function handleTurnComplete(turn: TurnInfo) {
  emit("turn-complete", turn);
}

function handleFailedPlayerCards(data: { player: string; cards: string[] }) {
  emit("failed-player-cards", data);
}
</script>

<template>
  <div class="flex flex-row justify-between gap-4 p-4">
    <ScoreSheet
      :modelValue="props.modelValue"
      :heldCards="props.definitelyHeldCards"
    />
    <ChatBox
      :game-info="modelValue"
      @turn-complete="handleTurnComplete"
      @game-won="() => emit('game-won')"
      @failed-player-cards="handleFailedPlayerCards"
    />
  </div>
</template>

<style scoped></style>
