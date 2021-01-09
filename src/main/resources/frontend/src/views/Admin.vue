<template>
  <div class="view col center-y">
    <h3>Admin page</h3>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";

@Component()
class Admin extends Vue {
  get currentUser() {
    return this.$store.state.currentUser;
  }

  created() {
    if (!this.currentUser?.roles.includes("ADMIN")) {
      this.$router.push({ name: "Home" });
    }
  }
}

export default Admin;
</script>

<style lang="scss" scoped></style>
