<script setup lang="ts">
import { computed } from "vue";
import { Square } from "lucide-vue-next";
import { SquareX } from "lucide-vue-next";
import { SquareCheck } from "lucide-vue-next";

const props = defineProps({
  modelValue: {
    type: Object,
    default: () => ({
      boardInfo: {
        suspects: [],
        weapons: [],
        rooms: [],
      },
      playerInfo: {
        players: [],
        currentUser: "",
      },
      cardInfo: {
        shownCards: [],
        userCards: [],
      },
    }),
  },
  heldCards: {
    type: Array,
    default: () => [],
  },
});

const unknownSuspects = computed(() => {
  return props.modelValue.boardInfo.suspects.filter(
    (suspect: string) => !props.heldCards.includes(suspect),
  );
});

const unknownWeapons = computed(() => {
  return props.modelValue.boardInfo.weapons.filter(
    (weapon: string) => !props.heldCards.includes(weapon),
  );
});

const unknownRooms = computed(() => {
  return props.modelValue.boardInfo.rooms.filter(
    (room: string) => !props.heldCards.includes(room),
  );
});
</script>

<template>
  <div class="w-full">
    <h2 class="mb-2 text-2xl font-bold">Score Sheet</h2>
    <div class="border-2 border-black bg-white text-black">
      <h3 class="border-b border-black px-2 py-1 text-lg font-semibold">
        Suspects
      </h3>
      <ul>
        <li
          v-for="(suspect, index) in modelValue.boardInfo.suspects"
          :key="index"
          class="grid grid-cols-3 items-center border-b border-black px-2 py-1"
        >
          <span class="col-start-1 text-left">
            {{ suspect }}
          </span>
          <SquareX
            v-if="props.heldCards.includes(suspect)"
            class="col-start-2 mx-auto h-6 w-6 text-red-600"
          />
          <SquareCheck
            v-else-if="unknownSuspects.length === 1"
            class="col-start-2 mx-auto h-6 w-6 text-green-600"
          />
          <Square v-else class="col-start-2 mx-auto h-6 w-6 text-black" />
        </li>
      </ul>
      <h3 class="border-b border-black px-2 py-1 text-lg font-semibold">
        Weapons
      </h3>
      <ul>
        <li
          v-for="(weapon, index) in modelValue.boardInfo.weapons"
          :key="index"
          class="grid grid-cols-3 items-center border-b border-black px-2 py-1"
        >
          <span class="col-start-1 text-left">
            {{ weapon }}
          </span>
          <SquareX
            v-if="props.heldCards.includes(weapon)"
            class="col-start-2 mx-auto h-6 w-6 text-red-600"
          />
          <SquareCheck
            v-else-if="unknownWeapons.length === 1"
            class="col-start-2 mx-auto h-6 w-6 text-green-600"
          />
          <Square v-else class="col-start-2 mx-auto h-6 w-6 text-black" />
        </li>
      </ul>
      <h3 class="border-b border-black px-2 py-1 text-lg font-semibold">
        Rooms
      </h3>
      <ul>
        <li
          v-for="(room, index) in modelValue.boardInfo.rooms"
          :key="index"
          class="grid grid-cols-3 items-center border-b border-black px-2 py-1"
        >
          <span class="col-start-1 text-left">
            {{ room }}
          </span>
          <SquareX
            v-if="props.heldCards.includes(room)"
            class="col-start-2 mx-auto h-6 w-6 text-red-600"
          />
          <SquareCheck
            v-else-if="unknownRooms.length === 1"
            class="col-start-2 mx-auto h-6 w-6 text-green-600"
          />
          <Square v-else class="col-start-2 mx-auto h-6 w-6 text-black" />
        </li>
      </ul>
    </div>
  </div>
</template>

<style scoped></style>
