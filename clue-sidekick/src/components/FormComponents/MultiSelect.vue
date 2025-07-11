<template>
  <div
    class="flex flex-wrap gap-2 rounded-md border-2 border-black bg-background p-3"
  >
    <button
      v-for="(option, index) in options"
      :key="index"
      type="button"
      @click="select(index)"
      :class="[
        'cursor-pointer rounded-lg px-3 py-1 text-sm transition-colors',
        modelValue.includes(option)
          ? 'bg-emerald-600 text-white'
          : 'bg-gray-200 text-black hover:bg-gray-300',
      ]"
    >
      {{ option }}
    </button>
  </div>
</template>

<script setup lang="ts">
const props = defineProps({
  modelValue: {
    type: Array as () => string[],
    default: () => [],
  },
  options: {
    type: Array as () => string[],
    default: () => [],
  },
});

const emit = defineEmits(["update:modelValue"]);

function select(index: number) {
  const selection = props.options[index];
  const newSelections = [...props.modelValue];

  const existingIndex = newSelections.indexOf(selection);

  if (existingIndex > -1) {
    newSelections.splice(existingIndex, 1);
  } else {
    newSelections.push(selection);
  }

  emit("update:modelValue", newSelections);
}
</script>
