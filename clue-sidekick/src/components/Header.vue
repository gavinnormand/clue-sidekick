<script setup lang="ts">
const refreshPage = () => {
  location.reload();
};
</script>

<template>
  <div class="w-full bg-background py-4">
    <h1
      @click="refreshPage"
      class="mx-auto w-fit cursor-pointer font-futura text-7xl font-extrabold transition-all duration-300 hover:opacity-70"
    >
      CL<span class="text-[#D72631]">U</span>E Sidekick
    </h1>
  </div>
</template>

<style scoped></style>
