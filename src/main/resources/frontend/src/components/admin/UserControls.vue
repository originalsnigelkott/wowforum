<template>
  <div class="controls col">
    <div class="control-section">
      <ModerationControls :user="user" />
    </div>
    <div v-if="!isCurrentUser" class="control-section row admin-controls">
      <button v-if="!isAdmin" @click="promoteUser()" class="btn">
        Promote to admin
      </button>
      <button v-if="isAdmin" @click="demoteUser()" class="btn">
        Demote admin
      </button>
    </div>
  </div>
</template>

<script>
import { Vue, Component, Prop } from "vue-property-decorator";
import ModerationControls from "./ModerationControls";

@Component({
  components: { ModerationControls },
})
class UserControls extends Vue {
  @Prop({ required: true })
  user;

  get isAdmin() {
    return this.user.roles.includes("ADMIN");
  }

  get isCurrentUser() {
    return this.user.id === this.$store.state.currentUser?.id;
  }

  promoteUser() {
    this.updateUser(this.user.roles + ",ADMIN");
  }

  demoteUser() {
    this.updateUser(this.user.roles.replace(",ADMIN", ""));
  }

  updateUser(roles) {
    const dispatchObject = {
      userId: this.user.id,
      payload: { roles },
    };
    this.$store.dispatch("updateUser", dispatchObject);
  }
}

export default UserControls;
</script>

<style lang="scss">
.controls {
  width: 100%;
  .control-section {
    padding: 5px;
    border-bottom: turquoise solid 1px;
    .title {
      font-size: 18px;
    }
    .subtitle {
      font-size: 16px;
    }
    &:last-child {
      border-bottom: none;
    }
  }
  .admin-controls {
    justify-content: flex-end;
  }
}
</style>
