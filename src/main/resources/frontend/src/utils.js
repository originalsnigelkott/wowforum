export async function fetchWithCredentials(url, opts) {
  const response = await fetch(url, { credentials: "include", ...opts });
  return response;
}