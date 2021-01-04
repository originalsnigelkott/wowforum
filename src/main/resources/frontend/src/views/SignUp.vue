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
        />
        <label for="password-input">Password</label>
        <input
          type="password"
          id="password-input"
          class="input-field"
          v-model="password"
          required
        />
        <label for="firstname-input">First name</label>
        <input
          type="text"
          id="firstname-input"
          class="input-field"
          v-model="firstName"
          required
        />
        <label for="lastname-input">Last name</label>
        <input
          type="text"
          id="lastname-input"
          class="input-field"
          v-model="lastName"
          required
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

@Component()
class SignUp extends Vue {
  processing = false;
  username = null;
  password = null;
  firstName = null;
  lastName = null;

  async signUp() {
    // TODO: Implement
  }
}

export default SignUp;
</script>

<style lang="scss" scoped>
@import "@/styles/form";
</style>
