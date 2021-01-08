<template>
  <div class="view col center-y">
    <div class="content">
      <ForumItem :forum="forum" :canNavigate="false" />
    </div>
    <ThreadForm v-if="currentUser" @addThread="addThread($event)" />
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import ForumItem from "@/components/shared/ForumItem";
import ThreadForm from "@/components/shared/ThreadForm";

@Component({
  components: { ForumItem, ThreadForm },
})
class Forum extends Vue {
  get forum() {
    return this.$store.state.forum;
  }

  get currentUser() {
    return this.$store.state.currentUser;
  }

  get threads() {
    return this.forum.threads ? this.forum.threads : [];
  }

  addThread(thread) {
    this.forum.threads.push(thread);
  }

  created() {
    const forumId = this.$route.params.id;
    this.$store.dispatch("fetchForum", forumId);
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
