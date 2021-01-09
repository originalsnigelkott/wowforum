import Vue from "vue";
import App from "@/App.vue";
import store from "@/store";
import router from "@/router";
import LockIcon from "vue-material-design-icons/Lock.vue"
import LockOpenVariantIcon from "vue-material-design-icons/LockOpenVariant.vue"
import DeleteIcon from "vue-material-design-icons/Delete.vue"

Vue.component("LockIcon", LockIcon);
Vue.component("LockOpenIcon", LockOpenVariantIcon);
Vue.component("DeleteIcon", DeleteIcon);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
