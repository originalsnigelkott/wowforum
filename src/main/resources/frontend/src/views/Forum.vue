<template>
  <div v-if="hasLoaded" class="view col center-y">
    <div class="content">
      <ForumItem :forum="forum" :canNavigate="false" />
    </div>
    <ThreadForm v-if="currentUser" :canWriteWarning="isModerator" />
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import { hasModerationRights } from "@/utils";
import ForumItem from "@/components/shared/ForumItem";
import ThreadForm from "@/components/shared/ThreadForm";

@Component({
  components: { ForumItem, ThreadForm }
})
class Forum extends Vue {
  get forum() {
    return this.$store.state.forum;
  }

  get hasLoaded() {
    return Object.keys(this.forum).length;
  }

  get currentUser() {
    return this.$store.state.currentUser;
  }

  get threads() {
    return this.forum.threads ? this.forum.threads : [];
  }

  get isModerator() {
    return hasModerationRights(this.forum.id);
  }

  created() {
    const forumId = this.$route.params.id;
    this.$store.dispatch("fetchForum", forumId);
  }

  destroyed() {
    this.$store.commit("setForum", {});
  }
}

export default Forum;
</script>

<style lang="scss" scoped>
.content {
  width: 100%;
  margin-bottom: 20px;
}
</style>
