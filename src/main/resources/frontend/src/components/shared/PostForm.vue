<template>
  <form class="form" @submit.prevent="createPost()">
    <div class="input-fields col">
      <label for="content-input">Content</label>
      <textarea
        id="content-input"
        class="input-field"
        v-model="content"
        rows="4"
        cols="50"
        autofocus
        required
      ></textarea>
    </div>
    <button :disabled="processing" type="submit" class="btn submit-btn">
      Create post
    </button>
  </form>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import { BASE_VERSION_URL } from "@/app-strings";
import { fetchWithCredentials } from "@/utils";

@Component()
class CreatePostForm extends Vue {
  processing = false;
  content = null;

  async createPost() {
    this.processing = true;
    const threadId = this.$route.params.id;
    const response = await fetchWithCredentials(
      `${BASE_VERSION_URL}/threads/${threadId}/posts`,
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ content: this.content }),
      }
    );
    await this.handleResponse(response);
    this.processing = false;
  }

  async handleResponse(response) {
    switch (response.status) {
      case 201: {
        const post = await response.json();
        this.$emit("addPost", post)
        this.content = null;
        break;
      }
      default: {
        console.log("Something went wrong.")
        break;
      }
    }

  }
}

export default CreatePostForm;
</script>

<style lang="scss" scoped>
@import "@/styles/form";
</style>
