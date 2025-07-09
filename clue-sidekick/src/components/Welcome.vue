<script setup lang="ts">
import {
  ClipboardList,
  IdCardLanyard,
  Settings,
  UserPen,
} from "lucide-vue-next";

const emit = defineEmits(["next"]);

function next() {
  emit("next");
}
</script>

<template>
  <div class="flex flex-col items-center justify-center gap-6 px-4 py-12">
    <h1 class="mx-auto px-12 mb-6 text-center font-sans text-6xl font-bold">
      Instant Feedback for Every Clue, <br />
      Every Time!
    </h1>
    <p class="mx-auto my-2 w-fit text-center font-sans text-lg">
      A clean, focused companion for your Clue games. Start tracking guesses,
      crossing off cards, and solving mysteries.
    </p>

    <div class="container mx-auto px-4">
      <div class="mx-auto max-w-4xl">
        <div class="grid grid-cols-1 gap-8 md:grid-cols-2">
          <div
            class="rounded-lg border-4 border-black bg-white p-8 text-center shadow-xl"
          >
            <Settings class="mx-auto mb-2 h-10 w-10 text-black" />
            <h3 class="mb-4 text-2xl font-bold text-black">Setup Game</h3>
            <p class="text-lg text-black">
              <span class="font-bold">Enter board info: </span> Choose which
              weapons, rooms, and characters are in play in your version of Clue
            </p>
          </div>
          <div
            class="rounded-lg border-4 border-black bg-white p-8 text-center shadow-xl"
          >
            <UserPen class="mx-auto mb-2 h-10 w-10 text-black" />
            <h3 class="mb-4 text-2xl font-bold text-black">Add Players</h3>
            <p class="text-lg text-black">
              <span class="font-bold">Enter player info: </span> Add player
              names and select who you are in the game
            </p>
          </div>
          <div
            class="rounded-lg border-4 border-black bg-white p-8 text-center shadow-xl"
          >
            <IdCardLanyard class="mx-auto mb-2 h-10 w-10 text-black" />
            <h3 class="mb-4 text-2xl font-bold text-black">Mark Cards</h3>
            <p class="text-lg text-black">
              <span class="font-bold">Enter card info: </span> Mark which cards
              are yours and which are public
            </p>
          </div>
          <div
            class="rounded-lg border-4 border-black bg-white p-8 text-center shadow-xl"
          >
            <ClipboardList class="mx-auto mb-2 h-10 w-10 text-black" />
            <h3 class="mb-4 text-2xl font-bold text-black">Track Guesses</h3>
            <p class="text-lg text-black">
              <span class="font-bold">Start tracking guesses: </span> Log the
              players' guesses and watch the solution appear
            </p>
          </div>
        </div>
      </div>
    </div>

    <p class="mx-auto mt-2 w-fit text-center font-sans text-lg font-bold">
      When you are ready, click the button below to start a new game.
    </p>
    <div class="flex items-center justify-center">
      <button
        type="button"
        class="inline-flex items-center gap-1 rounded-lg bg-emerald-500 px-5 py-2 text-3xl hover:bg-emerald-600"
        @click.prevent="next()"
      >
        Get Started
      </button>
    </div>
  </div>
</template>

<style scoped></style>
