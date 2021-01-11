<template>
  <div class="col">
    <h5 class="subtitle">Promote user to moderator</h5>
    <form @submit.prevent="addUserAsModerator()" class="row">
      <select
        name="forum"
        class="forum-dropdown"
        v-model="promoteForumId"
        required
      >
        <option :value="null"></option>
        <option
          v-for="forum in unModeratedForums"
          :key="forum.id"
          :value="forum.id"
        >
          {{ forum.name }}
        </option>
      </select>
      <button type="submit" class="btn" :disabled="processingPromotion">
        Promote
      </button>
    </form>
  </div>
</template>

<script>
import { Vue, Component, Prop } from "vue-property-decorator";

@Component()
class AddModeratorForm extends Vue {
  @Prop({ required: true })
  user;

  promoteForumId = null;
  processingPromotion = false;

  get unModeratedForums() {
    return this.$store.state.forums.filter(
      (forum) => !this.user.moderates.includes(forum.id)
    );
  }

  async addUserAsModerator() {
    this.processingPromotion = true;
    await this.$store.dispatch("addModerator", {
      userId: this.user.id,
      forumId: this.promoteForumId,
    });
    this.promoteForumId = null;
    this.processingPromotion = false;
  }
}

export default AddModeratorForm;
</script>

<style lang="scss" scoped>
@import "@/styles/moderation-management";
</style>
