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
import { BASE_VERSION_URL } from "@/app-strings";
import { fetchWithCredentials } from "@/utils";
import ForumItem from "@/components/shared/ForumItem";
import ThreadForm from "@/components/shared/ThreadForm";

@Component({
  components: { ForumItem, ThreadForm },
})
class Forum extends Vue {
  forum = {};

  get currentUser() {
    return this.$store.state.currentUser;
  }

  get threads() {
    return this.forum.threads ? this.forum.threads : [];
  }

  addThread(thread) {
    this.forum.threads.push(thread);
  }

  async created() {
    const forumId = this.$route.params.id;
    const data = await fetchWithCredentials(
      `${BASE_VERSION_URL}/forums/${forumId}`
    );
    try {
      const forum = await data.json();
      this.forum = forum;
    } catch {
      console.error("An error occured while loading the threads.");
    }
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
