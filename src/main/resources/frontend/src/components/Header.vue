<template>
  <div class="header row center-xy">
    <h1 @click="goHome()" class="title pointer">Wow forum</h1>
    <button v-if="shouldShowLoginButton" @click="goToLogin()" class="auth-btn btn">Login</button>
    <button v-if="shouldShowLogoutButton" @click="logout()" class="auth-btn btn">Logout</button>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";

@Component()
class Header extends Vue {
  get shouldShowLoginButton() {
    return this.$route.name != "Login" && !this.$store.currentUser;
  }
  
  get shouldShowLogoutButton() {
    return this.$store.currentUser;
  }

  goHome() {
    if (this.$route.name != "Home") this.$router.push({ name: "Home" });
  }

  goToLogin() {
    if (this.$route.name != "Login") this.$router.push({ name: "Login" });
  }
}

export default Header;
</script>

<style lang="scss" scoped>
.header {
  height: 75px;
  width: 100vw;
  position: fixed;
  top: 0;
  right: 0;
  z-index: 999;
  background-color: turquoise;
  padding: 0 50px;
  .title {
    padding: 10px;
    &:hover {
      box-shadow: none;
    }
  }
  .auth-btn {
    position: absolute;
    right: 50px;
    background-color: hotpink;
    color: turquoise;
    font-weight: bold;
    &:hover {
      background-color: rgba(255, 105, 180, 0.938);
    }
  }
}
</style>
