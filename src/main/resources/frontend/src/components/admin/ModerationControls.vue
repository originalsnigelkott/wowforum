<template>
  <div class="moderation-controls col">
    <h4 class="title">Moderation controls</h4>

    <div class="row spread">
      <ul v-if="user.moderates.length" class="moderates-list col">
        <h5 class="subtitle">Moderates:</h5>
        <li
          v-for="forumId in user.moderates"
          :key="forumId"
          class="row center-y"
        >
          <div class="list-marker" />
          <span>{{ getForumName(forumId) }}</span>
        </li>
      </ul>

      <h5 v-if="!user.moderates.length" class="subtitle">
        User is not moderating any forums
      </h5>

      <div class="manage-moderation-rights col">
        <div class="add-moderator">
          <AddModeratorForm :user="user" />
        </div>
        <RemoveModeratorForm :user="user" />
      </div>
    </div>
  </div>
</template>

<script>
import { Vue, Component, Prop } from "vue-property-decorator";
import AddModeratorForm from "./AddModeratorForm";
import RemoveModeratorForm from "./RemoveModeratorForm";

@Component({
  components: { AddModeratorForm, RemoveModeratorForm }
})
class ModerationControls extends Vue {
  @Prop({ required: true })
  user;

  getForumName(forumId) {
    const forum = this.$store.state.forums.find(
      (forum) => forum.id === forumId
    );
    console.log(forum);
    return forum.name;
  }
}

export default ModerationControls;
</script>

<style lang="scss" scoped>
.add-moderator {
  margin-bottom: 10px;
}
</style>
