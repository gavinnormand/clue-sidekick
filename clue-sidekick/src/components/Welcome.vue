<script setup lang="ts">
const emit = defineEmits(["next"]);

function next() {
  emit("next");
}
</script>

<template>
  <p class="mx-auto my-2 w-fit text-center font-sans text-lg font-bold">
    Hello and welcome to the Clue Sidekick! <br />
    This is a companion app for the board game Clue, designed to keep track of
    clues and suspects during the game, and deduce the solution to the mystery.
    <br />
    <br />
    When you are ready, click the button below to start a new game.
  </p>
  <div class="flex items-center justify-center m-4">
    <button
      type="button"
      class="inline-flex items-center gap-1 rounded-lg bg-emerald-600 px-6 py-3 text-4xl"
      @click.prevent="next()"
    >
      Start
    </button>
  </div>
</template>

<style scoped></style>
