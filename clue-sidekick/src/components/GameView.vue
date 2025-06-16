<script setup lang="ts">
import { computed } from "vue";
import ScoreSheet from "./ScoreSheet.vue";
import type { GameInfo } from "./types/GameInfo";

const props = defineProps({
  modelValue: {
    type: Object as () => GameInfo,
    required: true,
  },
});

const heldCards = computed(() => {
  const shown = props.modelValue.cardInfo?.shownCards ?? [];
  const user = props.modelValue.cardInfo?.userCards ?? [];
  console.log('Full modelValue:', props.modelValue);
  console.log('cardInfo:', props.modelValue.cardInfo);
  console.log('shown:', shown);
  console.log('user:', user);
  return shown.concat(user);
});
</script>

<template>
  <div>
    <ScoreSheet :modelValue="modelValue" :heldCards="heldCards" />
  </div>
</template>

<style scoped></style>
