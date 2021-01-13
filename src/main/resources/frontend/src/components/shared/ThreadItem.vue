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
    <div class="action-buttons row center-xy">
      <div
        v-if="isModerator"
        @click.stop="toggleThreadLock()"
        @mouseenter="setLockHover(true)"
        @mouseleave="setLockHover(false)"
        class="action-btn row center-xy pointer"
      >
        <div v-if="thread.locked">
          <LockIcon
            title="Unlock thread"
            v-if="!lockHover"
            fillColor="#FF0000"
          ></LockIcon>
          <LockOpenIcon
            title="Unlock thread"
            v-if="lockHover"
            fillColor="#008000"
          ></LockOpenIcon>
        </div>
        <div v-if="!thread.locked">
          <LockOpenIcon
            title="Lock thread"
            v-if="!lockHover"
            fillColor="#008000"
          ></LockOpenIcon>
          <LockIcon
            title="Lock thread"
            v-if="lockHover"
            fillColor="#FF0000"
          ></LockIcon>
        </div>
      </div>
      <div
        v-if="isModerator"
        @click.stop="deleteThread()"
        class="action-btn row center-xy pointer"
      >
        <DeleteIcon title="Delete thread" fillColor="#FF0000"></DeleteIcon>
      </div>
    </div>
  </div>
</template>

<script>
import { Vue, Component, Prop } from "vue-property-decorator";
import { hasModerationRights, fetchWithCredentials } from "@/utils";
import { BASE_VERSION_URL } from "@/app-strings";
import LockedThreadMessage from "@/components/shared/LockedThreadMessage";

@Component({ components: { LockedThreadMessage } })
class ThreadItem extends Vue {
  @Prop({ required: true })
  thread;
  @Prop({ default: 24 })
  fontSize;
  @Prop({ default: true })
  canNavigate;

  lockHover = false;

  get styleObject() {
    return { fontSize: this.fontSize + "px" };
  }

  get isModerator() {
    return hasModerationRights(this.thread.forumId);
  }

  setLockHover(status) {
    this.lockHover = status;
  }

  navigate() {
    this.$router.push({ name: "Thread", params: { id: this.thread.id } });
  }

  async toggleThreadLock() {
    const payload = {
      topic: this.thread.topic,
      isLocked: !this.thread.locked,
    };
    const response = await fetchWithCredentials(
      `${BASE_VERSION_URL}/threads/${this.thread.id}`,
      {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      }
    );
    if (response.status === 204) {
      this.thread.locked = !this.thread.locked;
    }
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
  .action-btn {
    margin-right: 10px;
  }
}
</style>
