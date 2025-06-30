<script setup lang="ts">
import BoardInfoForm from "./FormPages/BoardInfoForm.vue";
import { ref, computed } from "vue";
import PlayerInfoForm from "./FormPages/PlayerInfoForm.vue";
import CardInfoForm from "./FormPages/CardInfoForm.vue";
import type { BoardInfo, CardInfo, GameInfo, PlayerInfo } from "./types";

const emit = defineEmits(["update:modelValue", "next"]);

const props = defineProps({
  modelValue: {
    type: Object as () => GameInfo,
    required: true
  }
});

const step = ref(1);

const boardInfo = computed({
  get: () => props.modelValue.boardInfo,
  set: (value) => {
    emit('update:modelValue', {
      ...props.modelValue,
      boardInfo: value
    });
  }
});

const playerInfo = computed({
  get: () => props.modelValue.playerInfo,
  set: (value) => {
    emit('update:modelValue', {
      ...props.modelValue,
      playerInfo: value
    });
  }
});

const cardInfo = computed({
  get: () => props.modelValue.cardInfo,
  set: (value) => {
    emit('update:modelValue', {
      ...props.modelValue,
      cardInfo: value
    });
  }
});

function updateBoardInfo(newBoardInfo: BoardInfo) {
  boardInfo.value = newBoardInfo;
}

function updatePlayerInfo(newPlayerInfo: PlayerInfo) {
  playerInfo.value = newPlayerInfo;
}

function updateCardInfo(newCardInfo: CardInfo) {
  cardInfo.value = newCardInfo;
}

function goToStep2() {
  step.value = 2;
}

function goToStep3() {
  step.value = 3;
}

function submitForm() {
  emit("next");
}
</script>

<template>
  <BoardInfoForm
    v-if="step === 1"
    :modelValue="boardInfo"
    @update:modelValue="updateBoardInfo"
    @next="goToStep2"
  />

  <PlayerInfoForm
    v-if="step === 2"
    :modelValue="playerInfo"
    @update:modelValue="updatePlayerInfo"
    @next="goToStep3"
  />

  <CardInfoForm
    v-if="step === 3"
    :modelValue="cardInfo"
    :allCards="boardInfo.suspects.concat(boardInfo.weapons, boardInfo.rooms)"
    @update:modelValue="updateCardInfo"
    @next="submitForm"
  />
</template>
