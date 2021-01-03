<template>
  <div class="post-list">
    <div class="posts col">
      <PostItem v-for="post of posts" :key="post.id" :post="post" />
    </div>
  </div>
</template>

<script>
import { Vue, Component, Prop } from "vue-property-decorator";
import PostItem from "./PostItem";

@Component({ components: { PostItem } })
class PostList extends Vue {
  @Prop({ required: true, default: () => [] })
  posts;
}

export default PostList;
</script>

<style lang="scss" scoped>
  .posts {
    padding: 5px 10px;
  }
</style>
