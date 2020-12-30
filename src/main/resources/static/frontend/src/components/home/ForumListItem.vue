<template>
  <div class="forum">
    <div class="header" @click="log()">
      <h4 class="title">{{ forum.name }}</h4>
    </div>
    <div class="description">
      <p>{{ forum.description }}</p>
    </div>
    <div class="threads">
      <div v-if="threads.length > 0">
        <div class="thread" v-for="thread of forum.threads" :key="thread.id">
          <h4 class="thread-title">
            {{ thread.topic }}
          </h4>
        </div>
      </div>

      <div v-if="!threads.length > 0" class="thread">
        <h5>No threads in this forum yet!</h5>
      </div>
    </div>
  </div>
</template>

<script>
import { Vue, Component, Prop } from "vue-property-decorator";

@Component()
class ForumListItem extends Vue {
  @Prop({ required: true })
  forum;

  get threads() {
    return this.forum.threads;
  }
}

export default ForumListItem;
</script>

<style lang="scss" scoped>
.forum {
  margin: 5px 0;
  border: turquoise solid 1px;
  .header {
    width: 100%;
    padding: 10px;
    border-bottom: turquoise solid 1px;
    .title {
      font-size: 18px;
    }
  }
  .description {
    padding: 5px 10px;
    border-bottom: turquoise solid 1px;
  }
  .threads {
    padding: 5px;
    .thread {
      padding: 5px;
    }
  }
}
</style>
