<template>
  <div
    class="thread row spread"
    :class="{ pointer: canNavigate }"
    @click.stop="canNavigate && navigate()"
  >
    <div class="header row center-y">
      <h4 class="title" :style="styleObject">
        {{ thread.topic }}
        <LockedThreadMessage v-if="thread.locked" />
      </h4>
    </div>
    <div
      v-if="isModerator"
      @click.stop="deleteThread()"
      class="delete-btn row center-xy pointer"
    >
      <span class="delete-icon">&#x1f5d1;</span>
    </div>
  </div>
</template>

<script>
import { Vue, Component, Prop } from "vue-property-decorator";
import { hasModerationRights } from "@/utils";
import LockedThreadMessage from "@/components/shared/LockedThreadMessage";

@Component({ components: { LockedThreadMessage } })
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

  get isModerator() {
    console.log(hasModerationRights(this.thread.forumId));
    return hasModerationRights(this.thread.forumId);
  }

  navigate() {
    this.$router.push({ name: "Thread", params: { id: this.thread.id } });
  }

  deleteThread() {
    const ids = { threadId: this.thread.id, forumId: this.thread.forumId };
    this.$store.dispatch("deleteThread", ids);
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
  .delete-btn {
    width: 30px;
    height: 30px;
    font-size: 20px;
    margin-right: 10px;
    .delete-icon {
      color: red;
    }
  }
}
</style>
