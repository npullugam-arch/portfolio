<script setup lang="ts">
import { nextTick, onMounted, ref, watch } from "vue";
import { sendChatMessage } from "../services/chatApi";
import chatbotPuppy from "../assets/chatbot-puppy.png";

interface ChatMessage { role: "user" | "assistant" | "error"; content: string }

const isOpen = ref(false);
const showWelcome = ref(true);
const draft = ref("");
const isSending = ref(false);
const messages = ref<ChatMessage[]>([
  { role: "assistant", content: "Hello. I am Nanda AI, built by Nanda Kishore. What would you like to explore?" },
]);
const messagesElement = ref<HTMLElement | null>(null);

function openChat() { isOpen.value = true; showWelcome.value = false; }
function closeChat() { isOpen.value = false; }
function askSuggestion(question: string) { draft.value = question; void submit(); }
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
        <span class="welcome-kicker">NANDA / AI</span>
        <strong>Ask me about the work.</strong>
        <span>Projects, skills, experience and more.</span>
      </button>
    </Transition>
    <Transition name="chatbot-panel">
      <section v-if="isOpen" class="chatbot-panel" aria-label="AI assistant chat">
        <header class="chatbot-header">
          <div class="chatbot-heading"><span class="chatbot-avatar"><img :src="chatbotPuppy" alt="Chatbot puppy" /></span><div><span class="chatbot-eyebrow">NANDA / PORTFOLIO</span><strong>Curious minds welcome</strong><span class="chatbot-status"><i></i> Live assistant</span></div></div>
          <button class="chatbot-close" type="button" aria-label="Minimize chat" @click="closeChat">&#10005;</button>
        </header>
        <div ref="messagesElement" class="chatbot-messages">
          <div v-if="messages.length === 1" class="chatbot-intro"><span class="intro-line"></span><span>Ask a question</span></div>
          <div v-for="(message, index) in messages" :key="`${index}-${message.role}`" :class="['chatbot-message', `chatbot-message-${message.role}`]"><span v-if="message.role === 'assistant'" class="message-label">NANDA / AI</span>{{ message.content }}</div>
          <div v-if="isSending" class="chatbot-message chatbot-message-assistant chatbot-typing" aria-label="Assistant is typing"><i></i><i></i><i></i></div>
        </div>
        <div v-if="messages.length === 1" class="chatbot-suggestions" aria-label="Suggested questions">
          <button type="button" @click="askSuggestion('What projects has Nanda built?')">Projects <span>&#8599;</span></button>
          <button type="button" @click="askSuggestion('What technologies does Nanda work with?')">Stack <span>&#8599;</span></button>
          <button type="button" @click="askSuggestion('How can I contact Nanda?')">Contact <span>&#8599;</span></button>
        </div>
        <form class="chatbot-form" @submit.prevent="submit">
          <textarea v-model="draft" rows="1" maxlength="4000" placeholder="Write your question..." aria-label="Message" :disabled="isSending" @keydown="handleKeydown"></textarea>
          <button class="chatbot-send" type="submit" aria-label="Send message" :disabled="isSending || !draft.trim()"><span>&#8599;</span></button>
        </form>
        <span class="chatbot-footnote">AI responses may take a moment</span>
      </section>
    </Transition>
    <button class="chatbot-toggle" type="button" :aria-expanded="isOpen" aria-label="Open AI assistant" @click="isOpen ? closeChat() : openChat()"><span class="toggle-ring"></span><img :src="chatbotPuppy" alt="" /><b></b></button>
  </div>
</template>

<style scoped lang="scss">
.chatbot { --chat-ink: #1c2523; --chat-mint: #c7f56a; --chat-paper: #f4f3ec; position: fixed; right: var(--space-outer); bottom: var(--space-outer); z-index: calc(var(--z-index-preloader) - 1); color: var(--chat-ink); font-family: "Urbanist", sans-serif; }
.chatbot-toggle { width: 64px; height: 64px; position: relative; border: 1px solid rgba(28,37,35,.72); border-radius: 50%; background: var(--chat-ink); color: var(--chat-mint); box-shadow: 0 18px 38px rgba(28,37,35,.22); font: 700 13px "ProFontWindows", monospace; transition: transform .3s var(--ease-smooth), box-shadow .3s; }
.chatbot-toggle:hover { transform: translateY(-5px) rotate(5deg); box-shadow: 0 22px 44px rgba(28,37,35,.3); }.chatbot-toggle img { position: absolute; inset: 8px; width: calc(100% - 16px); height: calc(100% - 16px); border-radius: 50%; object-fit: cover; background: #f3c98b; }.toggle-ring { position: absolute; z-index: 1; inset: 7px; border: 1px solid rgba(199,245,106,.45); border-radius: 50%; pointer-events: none; }.chatbot-toggle b { position: absolute; z-index: 2; width: 9px; height: 9px; border-radius: 50%; background: #9bff77; right: 7px; top: 7px; border: 2px solid var(--chat-ink); }
.chatbot-welcome { display: grid; gap: 5px; position: absolute; right: 0; bottom: 78px; width: 260px; padding: 17px 18px; border: 1px solid rgba(28,37,35,.18); border-radius: 4px 18px 18px 18px; background: rgba(244,243,236,.84); box-shadow: 0 18px 46px rgba(28,37,35,.16); backdrop-filter: blur(18px); text-align: left; color: var(--chat-ink); font: 500 13px/1.35 "Urbanist"; }.welcome-kicker, .chatbot-eyebrow, .message-label { color: #66756e; font: 700 9px "ProFontWindows", monospace; letter-spacing: .08em; }.chatbot-welcome strong { font-size: 17px; letter-spacing: -.02em; }
.chatbot-panel { position: absolute; right: 0; bottom: 78px; width: min(405px, calc(100vw - 28px)); height: min(620px, calc(100vh - 104px)); display: flex; flex-direction: column; overflow: hidden; border: 1px solid rgba(28,37,35,.22); border-radius: 5px 26px 26px 26px; background: rgba(244,243,236,.82); box-shadow: 0 30px 80px rgba(28,37,35,.25), inset 0 1px rgba(255,255,255,.9); backdrop-filter: blur(22px); }
.chatbot-header { display: flex; align-items: center; justify-content: space-between; padding: 21px 21px 18px; background: linear-gradient(135deg, #263936, #182321); color: var(--chat-paper); }.chatbot-heading { display: flex; align-items: center; gap: 12px; }.chatbot-heading > div { display: grid; gap: 4px; }.chatbot-heading strong { color: #f8f8ef; font-size: 16px; letter-spacing: -.02em; }.chatbot-avatar { display: grid; place-items: center; width: 43px; height: 43px; overflow: hidden; border: 1px solid rgba(199,245,106,.65); border-radius: 50%; background: #f3c98b; box-shadow: 0 0 0 5px rgba(199,245,106,.08); }.chatbot-avatar img { width: 100%; height: 100%; object-fit: cover; }.chatbot-status { color: #c1cec6; font-size: 10px; }.chatbot-status i { display: inline-block; width: 6px; height: 6px; margin-right: 5px; border-radius: 50%; background: #9bff77; box-shadow: 0 0 10px #9bff77; }.chatbot-close { border: 0; background: transparent; color: #c1cec6; font-size: 14px; line-height: 1; padding: 8px; transition: color .2s, transform .2s; }.chatbot-close:hover { color: var(--chat-mint); transform: rotate(90deg); }
.chatbot-messages { flex: 1; overflow-y: auto; display: flex; flex-direction: column; gap: 13px; padding: 20px; }.chatbot-intro { display: flex; align-items: center; gap: 8px; margin: 2px 0 3px; color: #82908a; font: 700 9px "ProFontWindows", monospace; letter-spacing: .08em; text-transform: uppercase; }.intro-line { width: 23px; height: 1px; background: #9ab68b; }.chatbot-message { max-width: 84%; padding: 12px 14px; border-radius: 4px 16px 16px 16px; white-space: pre-wrap; overflow-wrap: anywhere; font-size: 13px; line-height: 1.55; animation: chatbot-message-in .35s var(--ease-smooth) both; }.message-label { display: block; margin-bottom: 6px; font-size: 8px; }.chatbot-message-user { align-self: flex-end; border-radius: 16px 4px 16px 16px; background: var(--chat-ink); color: #f5f8e9; box-shadow: 0 8px 20px rgba(28,37,35,.13); }.chatbot-message-assistant, .chatbot-message-error { align-self: flex-start; background: rgba(255,255,255,.65); border: 1px solid rgba(28,37,35,.08); }.chatbot-typing { display: flex; gap: 5px; padding: 15px; }.chatbot-typing i { width: 5px; height: 5px; border-radius: 50%; background: #799b76; animation: chatbot-pulse 1s infinite ease-in-out; }.chatbot-typing i:nth-child(2) { animation-delay: .15s; }.chatbot-typing i:nth-child(3) { animation-delay: .3s; }
.chatbot-suggestions { display: flex; flex-wrap: wrap; gap: 7px; padding: 0 20px 13px; }.chatbot-suggestions button { border: 1px solid rgba(28,37,35,.2); border-radius: 99px; padding: 7px 10px; background: transparent; color: #46564f; font: 700 10px "Urbanist"; transition: background .2s, color .2s, transform .2s; }.chatbot-suggestions button:hover { transform: translateY(-2px); background: var(--chat-ink); color: var(--chat-mint); }.chatbot-suggestions span { margin-left: 3px; }
.chatbot-form { display: flex; gap: 9px; padding: 13px 16px 7px; border-top: 1px solid rgba(28,37,35,.12); }.chatbot-form textarea { min-height: 42px; max-height: 100px; flex: 1; resize: none; border: 1px solid rgba(28,37,35,.18); border-radius: 13px; padding: 12px 13px; background: rgba(255,255,255,.58); color: var(--chat-ink); font: 13px "Urbanist"; outline: 0; }.chatbot-form textarea:focus { border-color: #789f70; box-shadow: 0 0 0 3px rgba(120,159,112,.12); }.chatbot-send { width: 44px; border: 0; border-radius: 13px; background: var(--chat-mint); color: var(--chat-ink); font-size: 22px; transition: transform .2s, opacity .2s; }.chatbot-send:hover:not(:disabled) { transform: translateY(-2px) rotate(3deg); }.chatbot-send:disabled { opacity: .4; cursor: not-allowed; }.chatbot-footnote { padding: 0 19px 14px; color: #89958e; text-align: right; font-size: 9px; }
.chatbot-panel-enter-active, .chatbot-panel-leave-active, .chatbot-welcome-enter-active, .chatbot-welcome-leave-active { transition: opacity .22s ease, transform .22s var(--ease-smooth); }.chatbot-panel-enter-from, .chatbot-panel-leave-to { opacity: 0; transform: translateY(14px) scale(.97); }.chatbot-welcome-enter-from, .chatbot-welcome-leave-to { opacity: 0; transform: translateY(8px); }
@keyframes chatbot-pulse { 0%, 80%, 100% { opacity: .3; transform: translateY(0); } 40% { opacity: 1; transform: translateY(-3px); } }
@keyframes chatbot-message-in { from { opacity: 0; transform: translateY(8px) scale(.98); } to { opacity: 1; transform: translateY(0) scale(1); } }
@media (max-width: 480px) { .chatbot { right: 12px; bottom: 12px; }.chatbot-toggle { width: 58px; height: 58px; }.chatbot-panel { right: -1px; bottom: 70px; width: min(405px, calc(100vw - 24px)); height: min(620px, calc(100vh - 88px)); border-radius: 5px 22px 22px 22px; }.chatbot-messages { padding: 16px; }.chatbot-header { padding: 17px; } }
@media (prefers-reduced-motion: reduce) { .chatbot-panel, .chatbot-message, .chatbot-toggle { animation: none; transition: none; } }
</style>