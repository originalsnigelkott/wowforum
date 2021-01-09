<template>
  <div class="header">
    <div class="header-content row center-xy">
      <button v-if="isAdmin" @click="navigateTo('Admin')" class="admin-btn btn">
        Admin
      </button>
      <h1 @click="navigateTo('Home')" class="title pointer">Wow forum</h1>
      <button
        v-if="shouldShowLoginButton"
        @click="navigateTo('Login')"
        class="auth-btn btn"
      >
        Login
      </button>
      <button v-if="currentUser" @click="logout()" class="auth-btn btn">
        Logout
      </button>
    </div>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";

@Component()
class Header extends Vue {
  get currentUser() {
    return this.$store.state.currentUser;
  }

  get shouldShowLoginButton() {
    return this.$route.name != "Login" && !this.currentUser;
  }

  get isAdmin() {
    return this.currentUser?.roles.includes("ADMIN");
  }

  navigateTo(routeName) {
    if (this.$route.name != routeName) this.$router.push({ name: routeName });
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
    .btn {
      position: fixed;
      background-color: hotpink;
      color: turquoise;
      font-weight: bold;
      &:hover {
        background-color: rgba(255, 105, 180, 0.938);
      }
    }
    .auth-btn {
      right: 50px;
    }
    .admin-btn {
      left: 50px;
    }
  }
}
</style>
