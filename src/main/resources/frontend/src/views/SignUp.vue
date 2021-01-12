<template>
  <div class="view col center-xy">
    <form class="sign-up-form form col" @submit.prevent="signUp()">
      <div class="input-fields col">
        <label for="username-input">Username</label>
        <input
          type="text"
          id="username-input"
          class="input-field"
          v-model="username"
          autofocus
          required
          minlength="3"
          maxlength="40"
        />
        <label for="password-input">Password</label>
        <input
          type="password"
          id="password-input"
          class="input-field"
          v-model="password"
          required
          minlength="6"
        />
        <label for="firstname-input">First name</label>
        <input
          type="text"
          id="firstname-input"
          class="input-field"
          v-model="firstName"
          required
          minlength="1"
          maxlength="40"
        />
        <label for="lastname-input">Last name</label>
        <input
          type="text"
          id="lastname-input"
          class="input-field"
          v-model="lastName"
          required
          minlength="1"
          maxlength="40"
        />
      </div>
      <button :disabled="processing" type="submit" class="btn submit-btn">
        Sign up
      </button>
    </form>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import { BASE_VERSION_URL } from "@/app-strings";
import { fetchWithCredentials } from "@/utils";

@Component()
class SignUp extends Vue {
  processing = false;
  username = null;
  password = null;
  firstName = null;
  lastName = null;

  async signUp() {
    this.processing = true;
    const response = await this.attemptSignUp();
    await this.handleResponse(response);
    this.processing = false;
  }

  async attemptSignUp() {
    const payload = {
      username: this.username,
      password: this.password,
      firstName: this.firstName,
      lastName: this.lastName,
    };
    const response = await fetchWithCredentials(BASE_VERSION_URL + "/users", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });
    return response;
  }

  async handleResponse(response) {
    switch (response.status) {
      case 201: {
        this.$router.push({ name: "Login" });
        break;
      }
      default: {
        console.error("Something went wrong.");
        break;
      }
    }
  }
}

export default SignUp;
</script>

<style lang="scss" scoped>
@import "@/styles/form";
</style>
