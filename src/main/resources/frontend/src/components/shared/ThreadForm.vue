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
          v-model="topic"
          autofocus
          required
        />

        <label for="content-input">Content</label>
        <textarea
          id="content-input"
          class="input-field"
          v-model="content"
          rows="4"
          required
        ></textarea>
      </div>
      <button :disabled="processing" type="submit" class="btn submit-btn">
        Create post
      </button>
    </form>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import { BASE_VERSION_URL } from "@/app-strings";
import { fetchWithCredentials } from "@/utils";

@Component()
class ThreadForm extends Vue {
  processing = false;
  topic = null;
  content = null;

  async createThread() {
    this.processing = true;
    const forumId = this.$route.params.id;
    const payload = {
      topic: this.topic,
      initialPost: { content: this.content },
    };
    const response = await fetchWithCredentials(
      `${BASE_VERSION_URL}/forums/${forumId}/threads`,
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
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
        this.content = null;
        this.topic = null;
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
