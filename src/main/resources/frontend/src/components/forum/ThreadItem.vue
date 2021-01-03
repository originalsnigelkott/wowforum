<template>
  <div
    class="thread"
    :class="{ pointer: canNavigate }"
    @click.stop="canNavigate && navigate()"
  >
    <div class="header">
      <h4 class="title" :style="styleObject">{{ thread.topic }}</h4>
    </div>
  </div>
</template>

<script>
import { Vue, Component, Prop } from "vue-property-decorator";

@Component()
class ThreadItem extends Vue {
  @Prop({ required: true })
  thread;
  @Prop({ default: 24 })
  fontSize;
  @Prop({ default: true })
  canNavigate;

  get styleObject() {
    return { fontSize: this.fontSize + "px" };
  }

  navigate() {
    this.$router.push({ name: "Thread", params: { id: this.thread.id } });
  }
}

export default ThreadItem;
</script>

<style lang="scss" scoped>
.thread {
  margin: 5px 0;
  border: turquoise solid 1px;
  .header {
    padding: 5px 10px;
  }
}
</style>
