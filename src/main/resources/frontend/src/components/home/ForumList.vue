<template>
  <div class="forum-list">
    <div class="forums col">
      <ForumItem v-for="forum of forums" :key="forum.id" :forum="forum" :fontSize="18" />
    </div>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import { fetchWithCredentials } from "@/utils"
import { BASE_VERSION_URL } from "@/app-strings";
import ForumItem from "@/components/shared/ForumItem";

@Component({
  components: { ForumItem },
})
class ForumList extends Vue {
  forums = [];

  async created() {
    const data = await fetchWithCredentials(`${BASE_VERSION_URL}/forums`);
    try {
      const forums = await data.json();
      this.forums = forums;
    } catch {
      console.error("An error occured while loading the forums.");
    }
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
