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
    userResults: [],
    forums: [],
  },
  mutations: {
    setCurrentUser(state, data) {
      state.currentUser = data;
    },
    setForum(state, data) {
      state.forum = data;
    },
    removeThread(state, data) {
      if (Object.keys(state.forum).length) {
        state.forum.threads = state.forum.threads.filter(
          (thread) => thread.id != data.threadId
        );
      }
      const forum = state.forums.find((forum) => forum.id === data.forumId);
      if (forum) {
        forum.threads = forum.threads.filter(
          (thread) => thread.id != data.threadId
        );
      }
    },
    setUserResults(state, data) {
      state.userResults = data;
    },
    setForums(state, data) {
      state.forums = data;
    },
    addThread(state, data) {
      if(state.forum.id === data.forumId) {
        state.forum.threads.push(data);
      }
    }
  },
  actions: {
    async getCurrentUser({ commit }) {
      const response = await fetchWithCredentials(BASE_URL + "/auth/login");
      try {
        const user = await response.json();
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
      const response = await fetchWithCredentials(
        `${BASE_VERSION_URL}/forums/${forumId}`
      );
      try {
        const forum = await response.json();
        commit("setForum", forum);
      } catch {
        console.error("An error occured while loading the threads.");
      }
    },
    async deleteThread({ commit }, ids) {
      const response = await fetchWithCredentials(
        `${BASE_VERSION_URL}/threads/${ids.threadId}`,
        {
          method: "DELETE",
        }
      );
      if (response.status === 204) {
        commit("removeThread", ids);
      }
    },
    async fetchUsers({ commit }, searchPhrase) {
      const response = await fetchWithCredentials(
        `${BASE_VERSION_URL}/users?username=${searchPhrase}`
      );
      try {
        const users = await response.json();
        commit("setUserResults", users);
      } catch {
        console.error("An error occured while loading the users");
      }
    },
    async fetchForums({ commit }) {
      const response = await fetchWithCredentials(`${BASE_VERSION_URL}/forums`);
      try {
        const forums = await response.json();
        commit("setForums", forums);
      } catch {
        console.error("An error occured while loading the forums");
      }
    },
    async createThread({ commit }, { payload, forumId }) {
      const response = await fetchWithCredentials(
        `${BASE_VERSION_URL}/forums/${forumId}/threads`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(payload),
        }
      );
      switch(response.status) {
        case 201: {
          const thread = await response.json();
          commit("addThread", thread);
          break;
        }
        default: {
          console.error("An error occured when trying to create thread.");
          break;
        }
      }
    },
  },
  getters: {
    currentUser: (state) => state.currentUser,
  },
});
