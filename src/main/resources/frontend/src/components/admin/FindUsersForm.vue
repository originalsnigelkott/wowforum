<template>
  <form class="find-users-form row" @submit.prevent="getUsers()">
    <input
      type="text"
      placeholder="Username"
      v-model="username"
      class="username-input"
    />
    <button type="submit" class="btn">Find users</button>
  </form>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";

@Component()
class FindUsersForm extends Vue {
    username = "";

  async getUsers() {
    await this.$store.dispatch("fetchUsers", this.username);
    this.username = "";
  }
}

export default FindUsersForm;
</script>

<style lang="scss" scoped>
.find-users-form {
  padding: 10px;
  border: turquoise solid 1px;
  .username-input {
    margin-right: 10px;
  }
}
</style>
