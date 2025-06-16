<script setup lang="ts">
import { computed } from "vue";
import PlayerInputText from "../FormComponents/PlayerInputText.vue";
import { ArrowRight } from "lucide-vue-next";

const props = defineProps({
  modelValue: {
    type: Object,
    default: () => ({
      players: [],
      currentUser: "",
    }),
  },
});

const emit = defineEmits(["update:modelValue", "next"]);

function next() {
  if (players.value.length === 0 || currentUser.value === "") {
    alert("Please fill in all fields before proceeding.");
    return;
  }

  emit("next");
}

const players = computed({
  get: () => props.modelValue.players,
  set: (value) => {
    emit("update:modelValue", {
      ...props.modelValue,
      players: value,
    });
  },
});

const currentUser = computed({
  get: () => props.modelValue.currentUser,
  set: (value) => {
    emit("update:modelValue", {
      ...props.modelValue,
      currentUser: value,
    });
  },
});
</script>

<template>
  <div class="m-8 rounded-xl bg-white p-4 shadow-md md:m-16">
    <h2 class="mb-4 text-center text-2xl font-bold text-black">
      Configure Your Players!
    </h2>
    <p class="text-center font-bold text-black">
      Ensure you enter all of the players of the game
      <span class="text-red-500">IN THE ORDER THEY WILL PLAY</span>!
    </p>

    <div class="m-4">
      <label for="players" class="text-md block font-medium text-black">
        Players
      </label>
      <PlayerInputText v-model="players" />
    </div>

    <div v-if="players.length > 0" class="m-4">
      <label for="currentUser" class="text-md block font-medium text-black">
        Who are you?
      </label>
      <select
        v-model="currentUser"
        class="flex w-full flex-1 rounded-md border-2 border-black bg-background p-2 text-sm outline-none"
      >
        <option value="" disabled>Select your name</option>
        <option v-for="player in players" :key="player" :value="player">
          {{ player }}
        </option>
      </select>
    </div>

    <div class="flex items-center justify-center">
      <button
        type="button"
        class="inline-flex items-center gap-1 rounded-lg bg-emerald-600 px-3 py-1 text-xl"
        @click.prevent="next()"
      >
        Next
        <ArrowRight
          class="relative top-[1px] align-middle"
          color="white"
          :size="24"
        />
      </button>
    </div>
  </div>
</template>
