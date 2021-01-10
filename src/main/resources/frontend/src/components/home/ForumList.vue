<template>
  <div class="forum-list">
    <div class="forums col">
      <ForumItem v-for="forum of forums" :key="forum.id" :forum="forum" :fontSize="18" />
    </div>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import ForumItem from "@/components/shared/ForumItem";

@Component({
  components: { ForumItem },
})
class ForumList extends Vue {
  get forums() {
    return this.$store.state.forums;
  }

  async created() {
    await this.$store.dispatch("fetchForums");
  }
}

export default ForumList;
</script>

<style lang="scss" scoped>
.forum-list {
  .forums {
    padding: 5px 10px;
  }
}
</style>
