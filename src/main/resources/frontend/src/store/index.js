import Vue from "vue";
import Vuex from "vuex";
import { BASE_URL } from "@/app-strings";
import { fetchWithCredentials } from "@/utils";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    currentUser: null,
  },
  mutations: {
    setCurrentUser(state, data) {
      state.currentUser = data;
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
  },
  modules: {},
});
