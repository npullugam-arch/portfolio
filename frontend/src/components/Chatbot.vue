<script setup lang="ts">
import { nextTick, onMounted, ref, watch } from "vue";
import { sendChatMessage } from "../services/chatApi";

interface ChatMessage { role: "user" | "assistant" | "error"; content: string }

const isOpen = ref(false);
const showWelcome = ref(true);
const draft = ref("");
const isSending = ref(false);
const messages = ref<ChatMessage[]>([
  { role: "assistant", content: "Hello. I am Nanda's general AI assistant. What would you like to explore?" },
]);
const messagesElement = ref<HTMLElement | null>(null);

function openChat() { isOpen.value = true; showWelcome.value = false; }
function closeChat() { isOpen.value = false; }
async function scrollToLatest() {
  await nextTick();
  if (messagesElement.value) messagesElement.value.scrollTop = messagesElement.value.scrollHeight;
}
async function submit() {
  const message = draft.value.trim();
  if (!message || isSending.value) return;
  messages.value.push({ role: "user", content: message });
  draft.value = "";
  isSending.value = true;
  await scrollToLatest();
  try {
    messages.value.push({ role: "assistant", content: await sendChatMessage(message) });
  } catch (error) {
    messages.value.push({ role: "assistant", content: "I couldn't complete that response. Please try sending it again." });
  } finally {
    isSending.value = false;
    await scrollToLatest();
  }
}
function handleKeydown(event: KeyboardEvent) {
  if (event.key === "Enter" && !event.shiftKey) { event.preventDefault(); void submit(); }
}
watch(messages, () => void scrollToLatest(), { deep: true });
onMounted(() => window.setTimeout(() => { if (!isOpen.value) showWelcome.value = true; }, 600));
</script>

<template>
  <div class="chatbot" aria-live="polite">
    <Transition name="chatbot-welcome">
      <button v-if="showWelcome && !isOpen" class="chatbot-welcome" type="button" @click="openChat">
        Hi, I'm Nanda's AI Assistant. How can I help you?
      </button>
    </Transition>
    <Transition name="chatbot-panel">
      <section v-if="isOpen" class="chatbot-panel" aria-label="AI assistant chat">
        <header class="chatbot-header">
          <div class="chatbot-heading"><span class="chatbot-avatar">AI</span><div><strong>AI Assistant</strong><span class="chatbot-status"><i></i> Online</span></div></div>
          <button class="chatbot-close" type="button" aria-label="Minimize chat" @click="closeChat">-</button>
        </header>
        <div ref="messagesElement" class="chatbot-messages">
          <div v-for="(message, index) in messages" :key="`${index}-${message.role}`" :class="['chatbot-message', `chatbot-message-${message.role}`]">{{ message.content }}</div>
          <div v-if="isSending" class="chatbot-message chatbot-message-assistant chatbot-typing" aria-label="Assistant is typing"><i></i><i></i><i></i></div>
        </div>
        <form class="chatbot-form" @submit.prevent="submit">
          <textarea v-model="draft" rows="1" maxlength="4000" placeholder="Ask anything..." aria-label="Message" :disabled="isSending" @keydown="handleKeydown"></textarea>
          <button class="chatbot-send" type="submit" aria-label="Send message" :disabled="isSending || !draft.trim()">&gt;</button>
        </form>
      </section>
    </Transition>
    <button class="chatbot-toggle" type="button" :aria-expanded="isOpen" aria-label="Open AI assistant" @click="isOpen ? closeChat() : openChat()"><span>AI</span><b></b></button>
  </div>
</template>

<style scoped lang="scss">
.chatbot { position: fixed; right: var(--space-outer); bottom: var(--space-outer); z-index: calc(var(--z-index-preloader) - 1); font-family: "Urbanist", sans-serif; color: var(--color-text-400); }
.chatbot-toggle { width: 58px; height: 58px; border: 1px solid var(--color-text-400); border-radius: 50%; background: var(--color-background-400); color: var(--color-text-400); box-shadow: 0 10px 30px rgba(45, 42, 36, .18); font: 700 14px "ProFontWindows", monospace; position: relative; transition: transform .25s var(--ease-smooth), background .25s; }
.chatbot-toggle:hover { transform: translateY(-4px); background: var(--color-cyan-400); }
.chatbot-toggle b { position: absolute; width: 8px; height: 8px; border-radius: 50%; background: #42a568; right: 5px; top: 5px; border: 2px solid var(--color-background-400); }
.chatbot-welcome { position: absolute; right: 0; bottom: 70px; width: 245px; padding: 14px 16px; border: 1px solid var(--color-text-400); border-radius: var(--radius-md); background: var(--color-background-400); box-shadow: 0 12px 28px rgba(45, 42, 36, .16); text-align: left; font: 700 13px/1.35 "Urbanist"; }
.chatbot-panel { position: absolute; right: 0; bottom: 70px; width: min(370px, calc(100vw - 32px)); height: min(560px, calc(100vh - 110px)); display: flex; flex-direction: column; overflow: hidden; border: 1px solid var(--color-text-400); border-radius: var(--radius-lg); background: var(--color-background-400); box-shadow: 0 20px 55px rgba(45, 42, 36, .24); }
.chatbot-header { display: flex; align-items: center; justify-content: space-between; padding: 16px; background: var(--color-text-400); color: var(--color-background-400); }
.chatbot-heading { display: flex; align-items: center; gap: 10px; font-size: 15px; }.chatbot-heading > div { display: grid; gap: 3px; }.chatbot-avatar { display: grid; place-items: center; width: 34px; height: 34px; border-radius: 50%; background: var(--color-cyan-400); color: var(--color-text-400); font: 700 11px "ProFontWindows"; }.chatbot-status { font-size: 11px; font-weight: 400; opacity: .8; }.chatbot-status i { display: inline-block; width: 6px; height: 6px; margin-right: 4px; border-radius: 50%; background: #71d78f; }.chatbot-close { border: 0; background: transparent; color: inherit; font-size: 22px; line-height: 1; padding: 2px 6px; }
.chatbot-messages { flex: 1; overflow-y: auto; display: flex; flex-direction: column; gap: 10px; padding: 16px; }.chatbot-message { max-width: 84%; padding: 10px 12px; border-radius: var(--radius-md); white-space: pre-wrap; overflow-wrap: anywhere; font-size: 14px; line-height: 1.45; }.chatbot-message-user { align-self: flex-end; background: var(--color-text-400); color: var(--color-background-400); border-bottom-right-radius: var(--radius-sm); }.chatbot-message-assistant, .chatbot-message-error { align-self: flex-start; background: var(--color-beige-600); border-bottom-left-radius: var(--radius-sm); color: inherit; }.chatbot-typing { display: flex; gap: 4px; padding: 13px; }.chatbot-typing i { width: 5px; height: 5px; border-radius: 50%; background: var(--color-text-300); animation: chatbot-pulse 1s infinite ease-in-out; }.chatbot-typing i:nth-child(2) { animation-delay: .15s; }.chatbot-typing i:nth-child(3) { animation-delay: .3s; }
.chatbot-form { display: flex; gap: 8px; padding: 12px; border-top: 1px solid var(--color-grayscale-500); }.chatbot-form textarea { min-height: 38px; max-height: 100px; flex: 1; resize: none; border: 1px solid var(--color-grayscale-500); border-radius: var(--radius-sm); padding: 10px; background: rgba(255,255,255,.45); color: inherit; font: 14px "Urbanist"; outline: 0; }.chatbot-form textarea:focus { border-color: var(--color-cyan-500); }.chatbot-send { width: 40px; border: 0; border-radius: var(--radius-sm); background: var(--color-cyan-500); color: white; font-size: 20px; }.chatbot-send:disabled { opacity: .4; cursor: not-allowed; }
.chatbot-panel-enter-active, .chatbot-panel-leave-active, .chatbot-welcome-enter-active, .chatbot-welcome-leave-active { transition: opacity .22s ease, transform .22s var(--ease-smooth); }.chatbot-panel-enter-from, .chatbot-panel-leave-to { opacity: 0; transform: translateY(14px) scale(.97); }.chatbot-welcome-enter-from, .chatbot-welcome-leave-to { opacity: 0; transform: translateY(8px); }
@keyframes chatbot-pulse { 0%, 80%, 100% { opacity: .3; transform: translateY(0); } 40% { opacity: 1; transform: translateY(-3px); } }
@media (max-width: 480px) { .chatbot { right: 12px; bottom: 12px; }.chatbot-panel { bottom: 68px; height: min(560px, calc(100vh - 92px)); } }
</style>