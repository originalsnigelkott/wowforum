<template>
  <div>
    <div class="header">
      <h3>Create new post</h3>
    </div>
    <form class="form col" @submit.prevent="createPost()">
      <div class="input-fields col">
        <label for="content-input">Content</label>
        <textarea
          id="content-input"
          class="input-field"
          v-model="post.content"
          rows="4"
          minlength="3"
          autofocus
          required
        ></textarea>
        <div class="col" v-if="canWriteWarning">
          <label for="warning-input" class="warning">Warning message?</label>
          <input
            id="warning-input"
            class="warning"
            type="checkbox"
            v-model="post.isWarning"
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
class PostForm extends Vue {
  @Prop({ default: false })
  canWriteWarning;

  processing = false;
  post = {
    content: null,
    isWarning: false,
  };

  async createPost() {
    this.processing = true;
    const threadId = this.$route.params.id;
    const response = await fetchWithCredentials(
      `${BASE_VERSION_URL}/threads/${threadId}/posts`,
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(this.post),
      }
    );
    await this.handleResponse(response);
    this.processing = false;
  }

  async handleResponse(response) {
    switch (response.status) {
      case 201: {
        const post = await response.json();
        this.$emit("addPost", post);
        this.post.content = null;
        this.post.isWarning = false;
        break;
      }
      default: {
        console.log("Something went wrong.");
        break;
      }
    }
  }
}

export default PostForm;
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
