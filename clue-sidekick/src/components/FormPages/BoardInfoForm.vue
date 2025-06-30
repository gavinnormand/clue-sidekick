<script setup lang="ts">
import MultiInputText from "../FormComponents/MultiInputText.vue";
import { computed } from "vue";
import { ArrowRight } from "lucide-vue-next";

const props = defineProps({
  modelValue: {
    type: Object,
    default: () => ({
      suspects: [],
      weapons: [],
      rooms: [],
    }),
  },
});

const emit = defineEmits(["update:modelValue", "next"]);

function next() {
  if (
    suspects.value.length === 0 ||
    weapons.value.length === 0 ||
    rooms.value.length === 0
  ) {
    alert("Please fill in all fields before proceeding.");
    return;
  }

  emit("next");
}

const suspects = computed({
  get: () => props.modelValue.suspects,
  set: (value) => {
    emit("update:modelValue", {
      ...props.modelValue,
      suspects: value,
    });
  },
});

const weapons = computed({
  get: () => props.modelValue.weapons,
  set: (value) => {
    emit("update:modelValue", {
      ...props.modelValue,
      weapons: value,
    });
  },
});

const rooms = computed({
  get: () => props.modelValue.rooms,
  set: (value) => {
    emit("update:modelValue", {
      ...props.modelValue,
      rooms: value,
    });
  },
});
</script>

<template>
  <div class="m-8 rounded-xl bg-white p-4 shadow-md md:m-16">
    <h2 class="text-center text-2xl font-bold text-black">
      Configure Your Board!
    </h2>
    <p class="text-center font-bold text-black">
      Ensure you enter in all the information about the board correctly!
    </p>

    <div class="m-4">
      <label for="suspects" class="text-md block font-medium text-black">
        Suspects
      </label>
      <MultiInputText v-model="suspects" />
    </div>

    <div class="m-4">
      <label for="weapons" class="text-md block font-medium text-black">
        Weapons
      </label>
      <MultiInputText v-model="weapons" />
    </div>

    <div class="m-4">
      <label for="rooms" class="text-md block font-medium text-black">
        Rooms
      </label>
      <MultiInputText v-model="rooms" />
    </div>

    <div class="flex items-center justify-center">
      <button
        type="button"
        class="inline-flex items-center gap-1 rounded-lg bg-emerald-600 px-3 py-1 text-xl hover:bg-emerald-500"
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
