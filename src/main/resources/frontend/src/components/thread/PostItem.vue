<template>
  <div class="post">
    <div class="header row">
      <span>
        Author: <span class="author">{{ creator }}</span>
      </span>
      <span>
        Created: <span class="created">{{ creationDate }}</span>
      </span>
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
