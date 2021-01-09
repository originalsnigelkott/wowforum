import Vue from "vue";
import App from "@/App.vue";
import store from "@/store";
import router from "@/router";
import DeleteIcon from "vue-material-design-icons/Delete.vue"

Vue.component("DeleteIcon", DeleteIcon);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
