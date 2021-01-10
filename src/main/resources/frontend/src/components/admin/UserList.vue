<template>
  <div class="user-list col">
    <div v-if="users.length" class="users col">
      <UserItem v-for="user in users" :key="user.id" :user="user" />
    </div>
    <div v-if="!users.length" class="users">
      <span>No users to display</span>
    </div>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import UserItem from "./UserItem";

@Component({
  components: { UserItem },
})
class UserList extends Vue {
  get users() {
    return this.$store.state.userResults;
  }
}

export default UserList;
</script>

<style lang="scss" scoped>
  .user-list {
    padding: 5px 10px;
    border: turquoise solid 1px;
  }
</style>
