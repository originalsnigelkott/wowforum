<template>
  <div class="view col center-xy">
    <form class="login-form col">
      <div class="input-fields col">
        <label for="username-input">Username</label>
        <input id="username-input" autofocus type="text" />
        <label for="password-input">Password</label>
        <input id="password-input" type="password" />
      </div>
      <span class="btn login-btn" @click="submit()">Login</span>
    </form>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";

@Component()
class Login extends Vue {
  submit() {
    console.log("Submitting!")
    // TODO: Implement submit.
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
    #username-input, #password-input {
      margin-bottom: 10px;
    }
  }
  .login-btn {
    user-select:none;
    margin-top: 10px;
    width: 100px;
  }
}
</style>
