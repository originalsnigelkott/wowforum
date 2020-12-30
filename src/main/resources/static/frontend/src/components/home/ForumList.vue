<template>
  <div class="forum-list">
    <div class="header">
      <h3 class="title">Forums</h3>
    </div>
    <div class="forums">
      <Forum v-for="forum of forums" :key="forum.id" :forum="forum" />
    </div>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import { BASE_URL } from "../../app-strings";
import Forum from "./Forum";

@Component({
  components: { Forum },
})
class ForumList extends Vue {
  forums = [];

  async created() {
    const data = await fetch(`${BASE_URL}/forums`);
    try {
      const forums = await data.json();
      this.forums = forums;
      console.log(this.forums);
    } catch {
      console.log("An error occured while loading the forums.");
    }
  }
}

export default ForumList;
</script>

<style lang="scss" scoped>
.forum-list {
  width: 100%;
  border: turquoise solid 1px;
  .header {
    width: 100%;
    padding: 10px;
    border-bottom: turquoise solid 1px;
    .title {
      font-size: 24px;
    }
  }
  .forums {
    padding: 5px 10px;
  }
}
</style>
