import store from "@/store/index";

export async function fetchWithCredentials(url, opts) {
  const response = await fetch(url, { ...opts, credentials: "include" });
  return response;
}

export function hasModerationRights(forumId) {
  const currentUser = store.getters.currentUser;
  return (
    currentUser?.roles.includes("ADMIN") ||
    currentUser?.moderates.includes(forumId)
  );
}
