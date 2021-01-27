<template>
  <div class="view col center-y">
    <div class="content">
      <div class="header row">
        <h2 class="title">
          {{ thread.topic }}
          <LockedThreadMessage v-if="locked" />
        </h2>
        <div class="meta-data row">
          <span>
            Created by: <span class="author">{{ author }}</span>
          </span>
          <span>
            Created at: <span class="created">{{ creationDate }}</span>
          </span>
        </div>
      </div>
      <PostList :posts="thread.posts" />
    </div>
    <PostForm
      v-if="currentUser && !locked"
      :canWriteWarning="userHasModerationRights"
    />
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import PostList from "@/components/thread/PostList";
import PostForm from "@/components/shared/PostForm";
import LockedThreadMessage from "@/components/shared/LockedThreadMessage";

@Component({ components: { PostList, PostForm, LockedThreadMessage } })
class Thread extends Vue {
  get thread() {
    return this.$store.state.thread;
  }

  get currentUser() {
    return this.$store.state.currentUser;
  }

  get locked() {
    return this.thread?.locked;
  }

  get author() {
    return this.thread?.initialPost?.creator?.username || "Deleted user";
  }

  get creationDate() {
    return new Date(this.thread?.initialPost?.created).toLocaleString();
  }

  get userHasModerationRights() {
    return (
      this.currentUser?.roles.includes("ADMIN") ||
      this.currentUser?.moderates.includes(this.thread.forumId)
    );
  }

  async created() {
    const threadId = this.$route.params.id;
    await this.$store.dispatch("fetchThread", threadId);
  }
}

export default Thread;
</script>

<style lang="scss" scoped>
.content {
  border: turquoise solid 1px;
  margin-bottom: 20px;
  width: 100%;
  .header {
    padding: 10px;
    border-bottom: turquoise solid 1px;
    justify-content: space-between;
    .title {
      font-size: 24px;
    }
    .meta-data {
      align-items: center;
      .author {
        margin-right: 4px;
      }
      .author,
      .created {
        color: turquoise;
        font-weight: bold;
      }
    }
  }
}
</style>
