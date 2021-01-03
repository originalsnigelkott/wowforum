<template>
  <div class="view col center-xy">
    <form class="login-form col" @submit.prevent="login()">
      <div class="input-fields col">
        <label for="username-input">Username</label>
        <input
          type="text"
          id="username-input"
          class="input-field"
          v-model="username"
          autofocus
          required
        />
        <label for="password-input">Password</label>
        <input
          type="password"
          id="password-input"
          class="input-field"
          v-model="password"
          required
        />
      </div>
      <button :disabled="processing" type="submit" class="btn login-btn">
        Login
      </button>
    </form>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import { BASE_URL } from "@/app-strings";
import { fetchWithCredentials } from "@/utils"

@Component()
class Login extends Vue {
  processing = false;
  username = null;
  password = null;

  async login() {
    this.processing = true;
    const response = await this.attemptLogin();
    await this.handleResponse(response);
    this.processing = false;
  }

  async attemptLogin() {
    const credentials =
      "username=" +
      encodeURIComponent(this.username) +
      "&password=" +
      encodeURIComponent(this.password);
    const response = await fetchWithCredentials(BASE_URL + "/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      body: credentials,
    });
    return response;
  }

  async handleResponse(response) {
    console.log("Handling response ", response);
    switch (response.status) {
      case 200: {
        await this.$store.dispatch("getCurrentUser");
        break;
      }
      case 401: {
        console.log("Authentication failed.");
        break;
      }
      default: {
        console.log("Something went wrong.");
        break;
      }
    }
  }
}

export default Login;
</script>

<style lang="scss" scoped>
.login-form {
  padding: 10px;
  border: turquoise solid 1px;
  align-items: flex-end;
  .input-fields {
    width: 100%;
    .input-field {
      margin-bottom: 10px;
    }
  }
  .login-btn {
    user-select: none;
    margin-top: 10px;
    width: 100px;
  }
}
</style>
