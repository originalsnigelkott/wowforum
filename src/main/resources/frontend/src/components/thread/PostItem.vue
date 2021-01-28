<template>
  <div class="post">
    <div class="header row center-y">
      <span>
        Author: <span class="author">{{ creator }}</span>
      </span>
      <div class="row center-y">
        <span>
          Created: <span class="created">{{ creationDate }}</span>
        </span>
        <DeleteIcon
          v-if="isDeletable"
          title="Delete forum"
          fillColor="#FF0000"
          class="delete-btn pointer"
          @click="removePost()"
        ></DeleteIcon>
      </div>
    </div>
    <div class="content">
      <p class="message" :class="{ warning: post.warning }">
        {{ post.content }}
      </p>
    </div>
  </div>
</template>

<script>
import { Vue, Component, Prop } from "vue-property-decorator";
import { BASE_VERSION_URL } from "@/app-strings";
import { hasModerationRights, fetchWithCredentials } from "@/utils";

@Component()
class PostItem extends Vue {
  @Prop({ required: true })
  post;

  get creator() {
    return this.post?.creator?.username || "Deleted user";
  }
  get creationDate() {
    return new Date(this.post.created).toLocaleString();
  }
  get isDeletable() {
    const state = this.$store.state;
    return (
      hasModerationRights(state.thread?.forumId) &&
      state.thread?.initialPost?.id !== this.post.id
    );
  }

  async removePost() {
    const id = this.post.id;
    const response = await fetchWithCredentials(
      `${BASE_VERSION_URL}/posts/${id}`,
      {
        method: "DELETE"
      }
    );
    if (response.status === 204) {
      this.$store.commit("removePost", id);
    }
  }
}

export default PostItem;
</script>

<style lang="scss" scoped>
.post {
  margin: 5px 0;
  border: turquoise solid 1px;
  .header {
    padding: 5px 10px;
    border-bottom: turquoise solid 1px;
    justify-content: space-between;
    .author,
    .created {
      font-weight: bold;
      color: turquoise;
    }
    .delete-btn {
      margin-left: 8px;
    }
  }
  .content {
    padding: 5px 10px;
    .message {
      white-space: pre-wrap;
    }
    .warning {
      color: red;
      font-weight: bold;
      text-decoration: underline;
    }
  }
}
</style>
