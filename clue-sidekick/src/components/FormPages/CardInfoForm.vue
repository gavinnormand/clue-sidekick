<script setup lang="ts">
import { computed } from "vue";
import { Check } from "lucide-vue-next";
import MultiSelect from "../FormComponents/MultiSelect.vue";

const props = defineProps({
  modelValue: {
    type: Object,
    default: () => ({
      shownCards: [],
      userCards: [],
    }),
  },
  allCards: {
    type: Array as () => string[],
    default: () => [],
  },
});

const emit = defineEmits(["update:modelValue", "next"]);

function next() {
  if (userCards.value.length === 0) {
    alert("Please fill in all fields before proceeding.");
    return;
  }

  emit("next");
}

const shownCards = computed({
  get: () => props.modelValue.shownCards,
  set: (value) => {
    emit("update:modelValue", {
      ...props.modelValue,
      shownCards: value,
    });
  },
});

const userCards = computed({
  get: () => props.modelValue.userCards,
  set: (value) => {
    emit("update:modelValue", {
      ...props.modelValue,
      userCards: value,
    });
  },
});

console.log('allCards:', props.allCards);
console.log('allCards length:', props.allCards.length);
</script>

<template>
  <div class="m-8 rounded-xl bg-white p-4 shadow-md md:m-16">
    <h2 class="text-center text-2xl font-bold text-black">
      Configure The Cards!
    </h2>
    <p class="text-center font-bold text-black">
      Ensure you enter in all the information about the cards correctly!
    </p>
    <div class="m-4">
      <label for="suspects" class="text-md block font-medium text-black"
        >Cards Shown To All Players</label
      >
      <MultiSelect :options="props.allCards" v-model="shownCards" />
    </div>
    <div class="m-4">
      <label for="weapons" class="text-md block font-medium text-black"
        >Your Cards</label
      >
      <MultiSelect :options="props.allCards" v-model="userCards" />
    </div>
    <div class="flex items-center justify-center">
      <button
        type="button"
        class="inline-flex items-center gap-1 rounded-lg bg-emerald-600 px-3 py-1 text-xl"
        @click.prevent="next()"
      >
        Submit
        <Check
          class="relative top-[1px] align-middle"
          color="white"
          :size="24"
        />
      </button>
    </div>
  </div>
</template>

<style scoped></style>
