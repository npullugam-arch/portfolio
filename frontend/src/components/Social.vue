<script setup lang="ts">
import Github from "./icons/Github.vue";
import Linkedin from "./icons/Linkedin.vue";
import Instagram from "./icons/Instagram.vue";
import Mail from "./icons/Mail.vue";
import X from "./icons/X.vue";
import Whatsapp from "./icons/Whatsapp.vue";
import Link from "./Link.vue";
import { t } from "../i18n/utils/translate";
import ButtonRound from "./ButtonRound.vue";

import { computed } from "vue";
import { portfolio, contactHref } from "../services/portfolioStore";

const props = defineProps<{
  variant?: "theme" | "background";
}>();

// map icon names to components
const icons = {
  mail: Mail,
  github: Github,
  linkedin: Linkedin,
  x: X,
  instagram: Instagram,
  whatsapp: Whatsapp,
} as const;

const social = computed(() => [
  {url:contactHref("email",portfolio.contact.email),name:"mail"},
  {url:portfolio.contact.githubUrl||"",name:"github"},
  {url:portfolio.contact.linkedinUrl||"",name:"linkedin"},
  {url:portfolio.contact.twitterUrl||"",name:"x"},
  {url:portfolio.contact.instagramUrl||"",name:"instagram"},
  {url:contactHref("whatsapp",portfolio.contact.whatsapp),name:"whatsapp"},
  {url:contactHref("phone",portfolio.contact.phone),name:"phone"},
].filter(item=>item.url));

const getAriaLabel = (name: string) => `${t("go-to")} ${name.charAt(0).toUpperCase() + name.slice(1)}`;
</script>

<template>
  <div class="social">
    <Link
      v-for="item in social"
      :key="item.name"
      external
      :href="item.url"
      :aria-label="getAriaLabel(item.name)"
      class="social-link"
      data-cursor="circle-white"
    >
      <ButtonRound
        renderAs="div"
        :variant="props.variant ?? 'theme'"
        class="children-unclickable"
        data-hoversound="hover"
      >
        <component v-if="icons[item.name as keyof typeof icons]" :is="icons[item.name as keyof typeof icons]" :aria-label="getAriaLabel(item.name)" external />
        <span v-else class="social-fallback">☎</span>
      </ButtonRound>
    </Link>
  </div>
</template>

<style scoped lang="scss">
.social {
  display: flex;
  gap: var(--space-md);
}
.social-fallback { font-weight: 900; font-size: 14px; }
</style>
