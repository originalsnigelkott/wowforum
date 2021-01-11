<template>
  <div class="user col">
    <div class="user-info row center-y">
      <h3 class="username">
        {{ user.username }}
      </h3>
      <div class="names col">
        <span class="firstname">
          {{ user.firstName }}
        </span>
        <span class="lastname">
          {{ user.lastName }}
        </span>
      </div>
    </div>
    <UserControls :user="user" />
  </div>
</template>

<script>
import { Vue, Component, Prop } from "vue-property-decorator";
import UserControls from "./UserControls";

@Component({
  components: { UserControls },
})
class UserItem extends Vue {
  @Prop({ required: true })
  user;
}

export default UserItem;
</script>

<style lang="scss" scoped>
.user {
  margin: 5px 0;
  border: turquoise solid 1px;
  .user-info {
    padding: 5px;
    border-bottom: turquoise solid 1px;
    .username {
      font-size: 24px;
      margin-right: 10px;
    }
    .names {
      .firstname,
      .lastname {
        font-size: 14px;
      }
    }
  }
}
</style>
