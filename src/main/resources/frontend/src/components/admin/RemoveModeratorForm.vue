<template>
  <div class="col">
    <h5 class="subtitle">Remove user as moderator</h5>
    <form @submit.prevent="removeUserAsModerator()" class="row">
      <select
        name="forum"
        class="forum-dropdown"
        v-model="demoteForumId"
        required
      >
        <option :value="null"></option>
        <option
          v-for="forum in moderatedForums"
          :key="forum.id"
          :value="forum.id"
        >
          {{ forum.name }}
        </option>
      </select>
      <button type="submit" class="btn" :disabled="processingDemotion">
        Demote
      </button>
    </form>
  </div>
</template>

<script>
import { Vue, Component, Prop } from "vue-property-decorator";

@Component()
class RemoveModeratorForm extends Vue {
  @Prop({ required: true })
  user;

  demoteForumId = null;
  processingDemotion = false;

  get moderatedForums() {
    return this.$store.state.forums.filter((forum) =>
      this.user.moderates.includes(forum.id)
    );
  }

  async removeUserAsModerator() {
    this.processingDemotion = true;
    await this.$store.dispatch("deleteModerator", {
      userId: this.user.id,
      forumId: this.demoteForumId,
    });
    this.demoteForumId = null;
    this.processingDemotion = false;
  }
}

export default RemoveModeratorForm;
</script>

<style lang="scss" scoped>
@import "@/styles/moderation-management";
</style>
