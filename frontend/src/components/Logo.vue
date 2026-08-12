<script setup lang="ts">
const logoUrl = `${import.meta.env.BASE_URL}meta/nanda-kishore-logo.png`;
</script>

<template>
  <img class="logo-image" :src="logoUrl" alt="Nanda Kishore" />
</template>

<style scoped lang="scss">
.logo-image {
  display: block;
  height: auto;
  object-fit: contain;
}
</style>
