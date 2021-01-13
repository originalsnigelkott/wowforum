<template>
  <div class="view col center-xy">
    <form class="login-form form col" @submit.prevent="login()">
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
      <button :disabled="processing" type="submit" class="btn submit-btn">
        Login
      </button>
      <span v-if="error" class="error">{{ error }}</span>
    </form>
    <span class="sign-up-link pointer" @click="navigateTo('SignUp')">
      Do not have an account? Click here!
    </span>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import { BASE_URL } from "@/app-strings";
import { fetchWithCredentials } from "@/utils";

@Component()
class Login extends Vue {
  processing = false;
  username = null;
  password = null;
  error = null;

  navigateTo(routeName) {
    if (this.$route.name != routeName) this.$router.push({ name: routeName });
  }

  async login() {
    this.error = null;
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
    switch (response.status) {
      case 200: {
        await this.$store.dispatch("getCurrentUser");
        this.$router.push({ name: "Home" });
        break;
      }
      case 401: {
        this.error = "Username and/or password is wrong.";
        console.error("Authentication failed.");
        break;
      }
      default: {
        this.error = "Something went wrong please try again.";
        console.error("Something went wrong.");
        break;
      }
    }
  }
}

export default Login;
</script>

<style lang="scss" scoped>
@import "@/styles/form";

.login-form {
  .error {
    color: red;
    margin-top: 10px;
    font-weight: bold;
  }
}

.sign-up-link {
  margin-top: 20px;
  box-shadow: none;
  &:hover {
    text-decoration: underline;
  }
}
</style>
