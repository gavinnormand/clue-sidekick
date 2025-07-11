<template>
  <div>
    <div
      class="flex flex-wrap items-center rounded-md border-2 border-black bg-background"
    >
      <input
        ref="inputRef"
        v-model="input"
        @keydown.enter.prevent="addTag"
        @keydown.delete="handleDelete"
        @blur="addTag"
        type="text"
        class="flex-1 bg-background p-2 text-sm outline-none"
        placeholder="Type and press Enter..."
      />
    </div>
    <div
      v-for="(tag, index) in modelValue"
      :key="index"
      class="mr-2 mt-2 w-fit flex-col items-center rounded-full bg-gray-200 px-2 text-sm text-black"
    >
      <div class="flex items-center">
        <p>{{ index + 1 }} . {{ tag }}</p>
        <button
          tabindex="-1"
          class="pl-1 text-lg text-black hover:text-red-500"
          @click.stop="removeTag(index)"
        >
          &times;
        </button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";

const props = defineProps({
  modelValue: {
    type: Array,
    default: () => [],
  },
});
const emit = defineEmits(["update:modelValue"]);

const input = ref("");

function addTag() {
  const newTag = input.value.trim();
  if (newTag && !props.modelValue.includes(newTag)) {
    emit("update:modelValue", [...props.modelValue, newTag]);
  }
  input.value = "";
}

function removeTag(index: number) {
  const newTags = [...props.modelValue];
  newTags.splice(index, 1);
  emit("update:modelValue", newTags);
}

function handleDelete() {
  if (input.value === "" && props.modelValue.length) {
    const newTags = [...props.modelValue];
    newTags.pop();
    emit("update:modelValue", newTags);
  }
}
</script>
