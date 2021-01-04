<template>
  <div class="view col center-y">
    <ForumItem :forum="forum" :canNavigate="false" />
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import { BASE_VERSION_URL } from "@/app-strings";
import { fetchWithCredentials } from "@/utils"
import ForumItem from "@/components/shared/ForumItem";

@Component({
  components: { ForumItem },
})
class Forum extends Vue {
  forum = {};

  get threads() {
    return this.forum.threads ? this.forum.threads : [];
  }

  async created() {
    const forumId = this.$route.params.id;
    const data = await fetchWithCredentials(`${BASE_VERSION_URL}/forums/${forumId}`);
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
  border: turquoise solid 1px;
  .header {
    padding: 5px 10px;
    border-bottom: turquoise solid 1px;
    .title {
      font-size: 24px;
    }
    .title,
    .description {
      padding: 5px 0;
    }
  }
}
</style>
