<template>
  <div class="view col center-y">
    <div class="content">
      <div class="header">
        <h2 class="title">Forums</h2>
      </div>
      <div class="forum-list">
        <ForumList />
      </div>
    </div>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import ForumList from "@/components/home/ForumList";

@Component({
  components: { ForumList },
})
class Home extends Vue {}

export default Home;
</script>

<style lang="scss" scoped>
.content {
  width: 100%;
  border: turquoise solid 1px;
  .header {
    padding: 10px;
    border-bottom: turquoise solid 1px;
    .title {
      font-size: 24px;
    }
  }
}
</style>
