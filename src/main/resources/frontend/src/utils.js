import store from "@/store/index";

export async function fetchWithCredentials(url, opts) {
  const response = await fetch(url, { credentials: "include", ...opts });
  return response;
}

export function hasModerationRights(forumId) {
  return (
    store.getters.currentUser?.roles.includes("ADMIN") ||
    store.getters.currentUser?.moderates.includes(forumId)
  );
}
