<template>
  <div class="thread-list">
    <div v-if="threads.length > 0" class="threads">
      <ThreadItem v-for="thread of threads" :key="thread.id" :fontSize="18" :thread="thread" />
    </div>

    <div v-if="!threads.length > 0" class="threads">
      <div class="thread">
        <p>No threads in this forum yet!</p>
      </div>
    </div>
  </div>
</template>

<script>
import { Vue, Component, Prop } from "vue-property-decorator";
import ThreadItem from "@/components/forum/ThreadItem.vue";

@Component({
  components: { ThreadItem },
})
class ThreadList extends Vue {
  @Prop({ required: true })
  threads;
}

export default ThreadList;
</script>

<style lang="scss" scoped>
.thread {
  padding: 5px 0;
}
</style>
