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
    forums: [],
    thread: {},
    userResults: []
  },
  mutations: {
    setCurrentUser(state, data) {
      state.currentUser = data;
    },
    setForum(state, data) {
      state.forum = data;
    },
    setForums(state, data) {
      state.forums = data;
    },
    setThread(state, data) {
      state.thread = data;
    },
    setUserResults(state, data) {
      state.userResults = data;
    },
    addThread(state, data) {
      if (state.forum.id === data.forumId) {
        state.forum.threads.push(data);
      }
    },
    addPost(state, data) {
      if (state.thread.id === data.threadId) {
        state.thread.posts.push(data);
      }
    },
    deleteForum(state, data) {
      state.forums = state.forums.filter((forum) => forum.id != data);
      if (state.forum.id === data) {
        state.forum = {};
      }
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
    addModerator(state, data) {
      const user = state.userResults.find((user) => user.id === data.userId);
      if (user) {
        user.moderates.push(data.forumId);
      }
    },
    deleteModerator(state, data) {
      const user = state.userResults.find((user) => user.id === data.userId);
      if (user) {
        user.moderates = user.moderates.filter((id) => id !== data.forumId);
      }
    },
    setUserRoles(state, data) {
      const user = state.userResults.find((user) => user.id === data.userId);
      if (user) {
        user.roles = data.roles;
      }
    },
    deleteUser(state, data) {
      state.userResults = state.userResults.filter((user) => user.id !== data);
    },
    removePost(state, id) {
      state.thread.posts = state.thread.posts.filter((post) => post.id !== id);
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
    async fetchForums({ commit }) {
      const response = await fetchWithCredentials(`${BASE_VERSION_URL}/forums`);
      try {
        const forums = await response.json();
        commit("setForums", forums);
      } catch {
        console.error("An error occured while loading the forums");
      }
    },
    async fetchThread({ commit }, threadId) {
      const response = await fetchWithCredentials(
        `${BASE_VERSION_URL}/threads/${threadId}`
      );
      if (response.status === 200) {
        const thread = await response.json();
        commit("setThread", thread);
      } else {
        console.error("An error occured when trying to fetch thread.");
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
    async createThread({ commit }, { payload, forumId }) {
      const response = await fetchWithCredentials(
        `${BASE_VERSION_URL}/forums/${forumId}/threads`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(payload)
        }
      );
      if (response.status === 201) {
        const thread = await response.json();
        commit("addThread", thread);
      } else {
        console.error("An error occured when trying to create thread.");
      }
    },
    async createPost({ commit }, { payload, threadId }) {
      const response = await fetchWithCredentials(
        `${BASE_VERSION_URL}/threads/${threadId}/posts`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(payload)
        }
      );
      if (response.status === 201) {
        const post = await response.json();
        commit("addPost", post);
      } else {
        console.error("An error occured when trying create post.");
      }
    },
    async deleteForum({ commit }, forumId) {
      const response = await fetchWithCredentials(
        `${BASE_VERSION_URL}/forums/${forumId}`,
        {
          method: "DELETE"
        }
      );
      if (response.status === 204) {
        commit("deleteForum", forumId);
      } else {
        console.error("An error occuren when trying to delete forum.");
      }
    },
    async deleteThread({ commit }, ids) {
      const response = await fetchWithCredentials(
        `${BASE_VERSION_URL}/threads/${ids.threadId}`,
        {
          method: "DELETE"
        }
      );
      if (response.status === 204) {
        commit("removeThread", ids);
      }
    },
    async addModerator({ commit }, { userId, forumId }) {
      const response = await fetchWithCredentials(
        `${BASE_VERSION_URL}/forums/${forumId}/moderators/${userId}`,
        {
          method: "POST"
        }
      );
      if (response.status === 204) {
        commit("addModerator", { userId, forumId });
      } else {
        console.error("An error occured when trying to add moderator.");
      }
    },
    async deleteModerator({ commit }, { userId, forumId }) {
      const response = await fetchWithCredentials(
        `${BASE_VERSION_URL}/forums/${forumId}/moderators/${userId}`,
        {
          method: "DELETE"
        }
      );
      if (response.status === 204) {
        commit("deleteModerator", { userId, forumId });
      } else {
        console.error("An error occured when trying to delete moderator.");
      }
    },
    async updateUser({ commit }, { userId, payload }) {
      const response = await fetchWithCredentials(
        `${BASE_VERSION_URL}/users/${userId}`,
        {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(payload)
        }
      );
      if (response.status === 204) {
        commit("setUserRoles", { userId, roles: payload.roles });
      } else {
        console.error("An error occured when trying to update user.");
      }
    },
    async deleteUser({ commit }, userId) {
      const response = await fetchWithCredentials(
        `${BASE_VERSION_URL}/users/${userId}`,
        {
          method: "DELETE"
        }
      );
      if (response.status === 204) {
        commit("deleteUser", userId);
      } else {
        console.error("An error occured when trying to delete user.");
      }
    }
  },
  getters: {
    currentUser: (state) => state.currentUser
  }
});
