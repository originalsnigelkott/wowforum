import Vue from "vue";
import Vuex from "vuex";
import router from "@/router";
import { BASE_URL, BASE_VERSION_URL } from "@/app-strings";
import { fetchWithCredentials } from "@/utils";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    currentUser: null,
    forum: {},
  },
  mutations: {
    setCurrentUser(state, data) {
      state.currentUser = data;
    },
    setForum(state, data) {
      state.forum = data;
    },
    removeThread(state, data) {
      console.log(state.forum.threads);
      if (Object.keys(state.forum).length) {
        state.forum.threads = state.forum.threads.filter(
          (thread) => thread.id != data.threadId
        );
      }
      console.log(state.forum.threads);
    },
  },
  actions: {
    async getCurrentUser({ commit }) {
      const data = await fetchWithCredentials(BASE_URL + "/auth/login");
      try {
        const user = await data.json();
        commit("setCurrentUser", user);
      } catch {
        console.log("Not authenticated.");
      }
    },
    async logout({ commit }) {
      await fetchWithCredentials(BASE_URL + "/auth/logout");
      commit("setCurrentUser", null);
      if (router.currentRoute.name != "Home") router.push({ name: "Home" });
    },
    async fetchForum({ commit }, forumId) {
      const data = await fetchWithCredentials(
        `${BASE_VERSION_URL}/forums/${forumId}`
      );
      try {
        const forum = await data.json();
        commit("setForum", forum);
      } catch {
        console.error("An error occured while loading the threads.");
      }
    },
    async deleteThread({ commit }, ids) {
      const data = await fetchWithCredentials(
        `${BASE_VERSION_URL}/threads/${ids.threadId}`,
        {
          method: "DELETE",
        }
      );
      if(data.status === 204) {
        commit("removeThread", ids);
      }
    },
  },
  getters: {
    currentUser: (state) => state.currentUser,
  },
});
