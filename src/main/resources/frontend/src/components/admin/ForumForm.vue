<template>
  <div>
    <div class="header">
      <h3>Create Forum</h3>
    </div>
    <form class="forum-form col form" @submit.prevent="createForum()">
      <div class="input-fields col">
        <label for="name-input">Name</label>
        <input
          id="name-input"
          class="input-field"
          v-model="forum.name"
          autofocus
          required
          minlength="3"
          maxlength="60"
        />

        <label for="description-input">Description</label>
        <textarea
          id="description-input"
          class="input-field"
          v-model="forum.description"
          rows="3"
          required
        ></textarea>
      </div>
      <button :disabled="processing" type="submit" class="btn submit-btn">
        Create forum
      </button>
      <span v-if="error" class="error">{{ error }}</span>
    </form>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import { fetchWithCredentials } from "@/utils";
import { BASE_VERSION_URL } from "@/app-strings";

@Component()
class ForumForm extends Vue {
  processing = false;
  error = null;

  forum = {
    name: "",
    description: "",
  };

  async createForum() {
    this.error = null;
    this.processing = true;
    const response = await fetchWithCredentials(`${BASE_VERSION_URL}/forums`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(this.forum),
    });
    if (response.status != 201) {
      this.error = "An error occured. Please try again."
      console.error("An error occured when posting new forum.");
    } else {
      this.forum.name = "";
      this.forum.description = "";
    }
    this.processing = false;
  }
}

export default ForumForm;
</script>

<style lang="scss" scoped>
@import "@/styles/form";
.form {
  width: 100% !important;
}
.header {
  border-left: turquoise solid 1px;
  border-right: turquoise solid 1px;
  border-top: turquoise solid 1px;
  padding: 10px;
}
.error {
  margin-top: 10px;
  color: red;
}
</style>
