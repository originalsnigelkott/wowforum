<template>
  <div class="view">
    
  </div>
</template>

<script>
import { Vue, Component } from 'vue-property-decorator';
import { BASE_URL } from '../app-strings';

@Component()
class Thread extends Vue {
  thread = {};

  async created() {
    const threadId = this.$route.params.id;
    const data = await fetch(`${BASE_URL}/threads/${threadId}`);
    try {
      const thread = await data.json();
      this.thread = thread;
    } catch {
      console.error("An error occured while loading the thread.");
    }
  }
}

export default Thread
</script>

<style lang="scss" scoped>
</style>