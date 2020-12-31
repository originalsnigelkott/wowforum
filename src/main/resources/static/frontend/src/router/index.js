import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home";
import Forum from "../views/Forum";
import Thread from "../views/Thread";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/forum/:id",
    name: "Forum",
    component: Forum,
  },
  {
    path: "/thread/:id",
    name: "Thread",
    component: Thread,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
