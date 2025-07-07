<script setup lang="ts">
import { computed } from "vue";
import ScoreSheet from "./ScoreSheet.vue";
import type { GameInfo, TurnInfo } from "./types";
import ChatBox from "./ChatBox.vue";

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

const heldCards = computed(() => {
  const shown = props.modelValue.cardInfo?.shownCards ?? [];
  const user = props.modelValue.cardInfo?.userCards ?? [];
  console.log("Full modelValue:", props.modelValue);
  console.log("cardInfo:", props.modelValue.cardInfo);
  console.log("shown:", shown);
  console.log("user:", user);
  return shown.concat(user);
});

const emit = defineEmits<{
  "turn-complete": [turn: TurnInfo];
}>();

function handleTurnComplete(turn: TurnInfo) {
  emit("turn-complete", turn);
}
</script>

<template>
  <div class="flex flex-row justify-between gap-4 p-4">
    <ScoreSheet
      :modelValue="modelValue"
      :heldCards="definitelyHeldCards"
    />
    <ChatBox :game-info="modelValue" @turn-complete="handleTurnComplete" />
  </div>
</template>

<style scoped></style>
