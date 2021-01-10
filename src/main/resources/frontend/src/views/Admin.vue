<template>
  <div class="view col center-y">
    <div class="content">
      <div class="header">
        <h2 class="title">Admin controls</h2>
      </div>
      <div class="controls col">
        <div class="control">
          <ForumForm />
        </div>
        <div class="control">
          <FindUsersForm />
        </div>
        <div class="control">
          <UserList />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import ForumForm from "@/components/admin/ForumForm";
import FindUsersForm from "@/components/admin/FindUsersForm";
import UserList from "@/components/admin/UserList";

@Component({
  components: { FindUsersForm, UserList, ForumForm },
})
class Admin extends Vue {
  get currentUser() {
    return this.$store.state.currentUser;
  }

  get userResults() {
    return this.$store.state.userResults;
  }

  created() {
    // if (!this.currentUser?.roles.includes("ADMIN")) {
    //   this.$router.push({ name: "Home" });
    // }
  }
}

export default Admin;
</script>

<style lang="scss" scoped>
.content {
  width: 100%;
  border: turquoise solid 1px;
  .header {
    padding: 10px;
    border-bottom: turquoise solid 1px;
    .title {
      font-size: 24px;
    }
  }
  .controls {
    padding: 5px 10px;
    .control {
      margin: 5px 0;
    }
  }
}
</style>
