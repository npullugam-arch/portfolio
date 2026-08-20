interface CsrfResponse { token: string; headerName: string }

const wait = (milliseconds: number) => new Promise((resolve) => window.setTimeout(resolve, milliseconds));

export async function sendChatMessage(message: string): Promise<string> {
  for (let attempt = 0; attempt < 2; attempt += 1) {
    const csrfResponse = await fetch("/api/csrf", { headers: { Accept: "application/json" }, credentials: "same-origin" });
    if (!csrfResponse.ok) throw new Error("Unable to start a secure chat session.");
    const csrf: CsrfResponse = await csrfResponse.json();
    const response = await fetch("/api/chat", {
      method: "POST",
      credentials: "same-origin",
      headers: { "Content-Type": "application/json", Accept: "application/json", [csrf.headerName]: csrf.token },
      body: JSON.stringify({ message }),
    });
    const body = await response.json().catch(() => ({})) as { reply?: string; error?: string };
    if (response.ok && body.reply) return body.reply;
    if (response.status !== 429 || attempt === 1) throw new Error(body.error || "The AI assistant is unavailable right now.");
    const retryAfter = Number(response.headers.get("Retry-After"));
    await wait(Math.min(Number.isFinite(retryAfter) && retryAfter > 0 ? retryAfter * 1000 : 3000, 15000));
  }
  throw new Error("The AI assistant is unavailable right now.");
}