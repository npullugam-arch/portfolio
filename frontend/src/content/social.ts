export const social = [
  { url: "mailto:mohan@example.com", name: "mail" },
  { url: "https://github.com/davidhckh", name: "github" },
  { url: "https://www.linkedin.com/in/david-heckhoff/", name: "linkedin" },
  { url: "#", name: "x" },
  //{ url: "https://www.instagram.com/davidhckh/", name: "instagram" },
] as const satisfies { url: string; name: "mail" | "github" | "instagram" | "linkedin" | "x" }[];
