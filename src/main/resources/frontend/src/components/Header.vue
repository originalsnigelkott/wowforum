<template>
  <div class="header">
    <div class="header-content row center-xy">
      <h1 @click="navigateTo('Home')" class="title pointer">Wow forum</h1>
      <button
        v-if="shouldShowLoginButton"
        @click="navigateTo('Login')"
        class="auth-btn btn"
      >
        Login
      </button>
      <button
        v-if="shouldShowLogoutButton"
        @click="logout()"
        class="auth-btn btn"
      >
        Logout
      </button>
    </div>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";

@Component()
class Header extends Vue {
  get shouldShowLoginButton() {
    return this.$route.name != "Login" && !this.$store.state.currentUser;
  }

  get shouldShowLogoutButton() {
    return this.$store.state.currentUser;
  }

  navigateTo(routeName) {
    if (this.$route.name !=routeName) this.$router.push({ name: routeName });
  }

  logout() {
    this.$store.dispatch("logout");
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
  .header-content {
    position: relative;
    width: 100%;
    height: 100%;
    padding: 0 50px;
    .title {
      padding: 10px;
      &:hover {
        box-shadow: none;
      }
    }
    .auth-btn {
      position: fixed;
      right: 50px;
      background-color: hotpink;
      color: turquoise;
      font-weight: bold;
      &:hover {
        background-color: rgba(255, 105, 180, 0.938);
      }
    }
  }
}
</style>
