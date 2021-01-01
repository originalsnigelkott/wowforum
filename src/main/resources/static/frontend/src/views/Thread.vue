<template>
  <div class="view">
    <div class="content">
      <div class="header row">
        <h2 class="title">{{ thread.topic }}</h2>
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
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import { BASE_URL } from "../app-strings";
import PostList from "../components/thread/PostList";

@Component({ components: { PostList } })
class Thread extends Vue {
  thread = {};

  get author() {
    return this.thread?.initialPost?.creator?.username;
  }

  get creationDate() {
    return new Date(this.thread?.initialPost?.created).toLocaleString();
  }

  async created() {
    const threadId = this.$route.params.id;
    const data = await fetch(`${BASE_URL}/threads/${threadId}`);
    try {
      const thread = await data.json();
      this.thread = thread;
      console.log(thread);
    } catch {
      console.error("An error occured while loading the thread.");
    }
  }
}

export default Thread;
</script>

<style lang="scss" scoped>
.content {
  border: turquoise solid 1px;
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
      .author, .created {
        color: turquoise;
        font-weight: bold;
      }
    }
  }
}
</style>
