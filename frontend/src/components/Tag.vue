<script setup lang="ts">
import { computed } from "vue";
import { tagLabels, type TagVariant } from "./tagVariants";

const props = defineProps<{
  variant: string;
}>();

const knownVariants = new Set(Object.keys(tagLabels));
const normalizedVariant = computed(() => props.variant.toLowerCase().replace(/[^a-z0-9]/g, ""));
const knownVariant = computed(() => knownVariants.has(normalizedVariant.value) ? normalizedVariant.value as TagVariant : null);
const classes = computed(() => ["tag", knownVariant.value && `tag-variant-${knownVariant.value}`]);
const label = computed(() => knownVariant.value ? tagLabels[knownVariant.value] : props.variant);
const palettes = [
  ["#7c3aed", "#ffffff"], ["#0891b2", "#ffffff"], ["#db2777", "#ffffff"],
  ["#ea580c", "#ffffff"], ["#16a34a", "#ffffff"], ["#ca8a04", "#1f1600"],
  ["#4f46e5", "#ffffff"], ["#0f766e", "#ffffff"], ["#be123c", "#ffffff"],
] as const;
const customStyle = computed(() => {
  if (knownVariant.value) return undefined;
  const hash = [...props.variant].reduce((value, character) => ((value * 31) + character.charCodeAt(0)) >>> 0, 0);
  const palette = palettes[hash % palettes.length] ?? palettes[0];
  return { backgroundColor: palette[0], color: palette[1] };
});
</script>

<template>
  <div :class="classes" :style="customStyle">
    <p class="tag-copy">{{ label }}</p>
  </div>
</template>

<style scoped lang="scss">
.tag {
  border-radius: var(--radius-md);
  padding: calc(var(--space-xxs) - var(--stroke-md)) calc(var(--space-sm  ) - var(--stroke-md));
  border: var(--stroke-md) solid transparent;

  &-copy {
    font-size: var(--font-size-xs);
    font-weight: 700;
  }

  &-variant {
    &-three {
      background-color: #ed9c55;
      color: #492708;
    }

    &-react {
      background-color: #61dafb;
      color: #0f2e36;
    }

    &-websockets {
      background-color: #2674a4;
      color: white;
    }

    &-redis {
      background-color: #ce4036;
      color: white;
    }

    &-html {
      background-color: #e34f26;
      color: white;
    }

    &-css {
      background-color: #1572b6;
      color: white;
    }

    &-javascript {
      background-color: #ffdf4f;
      color: #362d03;
    }

    &-node {
      background-color: #237d23;
      color: white;
    }

    &-next {
      background-color: white;
      color: black;
    }

    &-kubernetes {
      background-color: #326ce5;
      color: white;
    }

    &-postgresql {
      background-color: #2f6c92;
      color: white;
    }

    &-ogl {
      background-color: #0e5495;
      color: white;
    }

    &-glsl {
      background-color: #95630e;
      color: white;
    }
  }
}
</style>
