import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/Home";
import Forum from "@/views/Forum";
import Thread from "@/views/Thread";
import Login from "@/views/Login";
import SignUp from "@/views/SignUp";
import Admin from "@/views/Admin";

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
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/signup",
    name: "SignUp",
    component: SignUp,
  },
  {
    path: "/admin",
    name: "Admin",
    component: Admin,
  },
  {
    path: "*",
    component: Home,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
