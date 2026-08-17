<script setup lang="ts">
import { projectId, projectVisible, recentProjectId } from "../../../composables/useRouteObserver";
import { isTransitioning } from "../../../composables/useProjectTransition";
import { ref, watch } from "vue";
import ProjectContent from "./ProjectContent.vue";
import Footer from "../../../components/Footer.vue";
import { locale } from "../../../i18n/store";
import { lenis } from "../../../composables/useScroll";

import { portfolioApi } from "../../../services/portfolioApi";
import { portfolio } from "../../../services/portfolioStore";
import type { ProjectData } from "../../../services/portfolioApi";
import type { ProjectContent as ProjectContentData } from "../../../content/types";

const loading = ref(true);
const content = ref<ProjectContentData | null>(null);
const error = ref<Error | null>(null);

const toProjectContent = (project: ProjectData): ProjectContentData => {
  const mediaComponents = (project.media || []).map((item) => ({
    type: "media" as const,
    props: {
      type: item.mediaType.toLowerCase() as "image" | "video",
      src: item.mediaUrl,
      caption: item.caption,
    },
  }));
  return {
    title: project.projectTitle || project.name,
    theme: "light",
    description: project.detailedDescription || project.projectSubtitle || project.shortDescription || "",
    live: project.liveUrl || undefined,
    source: project.githubUrl || undefined,
    tags: (project.technologies || []).map((item) => item.technologyName?.trim()).filter(Boolean) as string[],
    components: mediaComponents,
  };
};

const fetchProject = async (project: string | undefined) => {
  loading.value = true;
  error.value = null;
  const cached = portfolio.projects.find((item) => item.slug === project);
  if (cached) content.value = toProjectContent(cached);
  try {
    const dynamic = await portfolioApi.project(project as string);
    content.value = toProjectContent(dynamic);
  } catch (err) {
    if (!content.value) {
      const latest = portfolio.projects.find((item) => item.slug === project);
      if (latest) content.value = toProjectContent(latest);
    }
    error.value = new Error(`Failed to fetch project ${project}`);
  } finally {
    loading.value = false;
  }
};

watch(
  [recentProjectId, locale],
  () => {
    if (recentProjectId.value) {
      fetchProject(recentProjectId.value);
    }
  },
  { immediate: true },
);

watch(
  () => portfolio.projects,
  (projects) => {
    if (content.value || !recentProjectId.value) return;
    const project = projects.find((item) => item.slug === recentProjectId.value);
    if (project) content.value = toProjectContent(project);
  },
  { deep: true },
);

watch(
  [projectId, isTransitioning, locale],
  () => {
    if (!projectId.value || isTransitioning.value) return;
    lenis.value?.scrollTo(0, { immediate: true });
  },
  { immediate: true },
);
</script>

<template>
  <div
    ref="projectRef"
    :class="[
      'project',
      recentProjectId !== null && `project-${recentProjectId}`,
      isTransitioning && `project-transitioning`,
      projectVisible && `project-visible`,
    ]"
  >
    <div :class="['project-content-wrapper', projectVisible && `project-content-wrapper-visible`]">
      <ProjectContent
        v-if="content && recentProjectId && projectVisible"
        :content="content"
        :projectId="recentProjectId"
      />
      <div v-else-if="loading" class="project-state">Loading project…</div>
      <div v-else class="project-state">
        <p>Project data could not be loaded.</p>
        <button type="button" @click="fetchProject(recentProjectId || undefined)">Try again</button>
      </div>
      <Footer :class="['project-footer', `project-${recentProjectId}`]"></Footer>
    </div>
  </div>
</template>

<style scoped lang="scss">
.project {
  min-height: calc(var(--lvh) * 100);
  background-color: var(--color-background-300);
  max-width: calc(var(--lvw) * 100);
  overflow: hidden;

  &-content-wrapper {
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    width: 100%;
    opacity: 0;
    transition: opacity 0.4s ease-out;

    &-visible {
      opacity: 1;
    }
  }

  &-footer {
    position: relative;
    margin-top: auto;
    color: var(--color-text-400);
  }

  &-state {
    min-height: 70vh;
    display: grid;
    place-content: center;
    justify-items: center;
    gap: var(--space-sm);
    padding: var(--space-outer);
    color: var(--color-text-400);
  }

  ::selection {
    background: var(--color-accent-400);
    color: var(--color-accent-text-400);
    text-shadow: none;
  }

  ::-moz-selection {
    background: var(--color-accent-400);
    color: var(--color-accent-text-400);
    text-shadow: none;
  }
}
</style>
