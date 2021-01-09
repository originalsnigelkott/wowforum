<template>
  <div>
    <div class="header">
      <h3>Create new thread</h3>
    </div>
    <form class="form col" @submit.prevent="createThread()">
      <div class="input-fields col">
        <label for="topic-input">Content</label>
        <input
          id="topic-input"
          class="input-field"
          v-model="thread.topic"
          autofocus
          required
          minlength="3"
          maxlength="60"
        />

        <label for="content-input">Content</label>
        <textarea
          id="content-input"
          class="input-field"
          v-model="thread.initialPost.content"
          rows="4"
          required
          minlength="3"
        ></textarea>
        <div class="col" v-if="canWriteWarning">
          <label for="warning-input" class="warning">Warning message?</label>
          <input
            id="warning-input"
            class="warning"
            type="checkbox"
            v-model="thread.initialPost.isWarning"
          />
        </div>
      </div>
      <button :disabled="processing" type="submit" class="btn submit-btn">
        Create post
      </button>
    </form>
  </div>
</template>

<script>
import { Vue, Component, Prop } from "vue-property-decorator";
import { BASE_VERSION_URL } from "@/app-strings";
import { fetchWithCredentials } from "@/utils";

@Component()
class ThreadForm extends Vue {
  @Prop({ default: false })
  canWriteWarning;

  processing = false;
  thread = {
    topic: null,
    initialPost: {
      content: null,
      isWarning: false,
    },
  };

  async createThread() {
    this.processing = true;
    const forumId = this.$route.params.id;
    const response = await fetchWithCredentials(
      `${BASE_VERSION_URL}/forums/${forumId}/threads`,
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(this.thread),
      }
    );
    await this.handleResponse(response);
    this.processing = false;
  }

  async handleResponse(response) {
    switch (response.status) {
      case 201: {
        const thread = await response.json();
        this.$emit("addThread", thread);
        this.thread.initialPost.content = null;
        this.thread.topic = null;
        break;
      }
      default: {
        console.log("Something went wrong.");
        break;
      }
    }
  }
}

export default ThreadForm;
</script>

<style lang="scss" scoped>
@import "@/styles/form";

.header {
  border-left: turquoise solid 1px;
  border-right: turquoise solid 1px;
  border-top: turquoise solid 1px;
  padding: 10px;
}
</style>
