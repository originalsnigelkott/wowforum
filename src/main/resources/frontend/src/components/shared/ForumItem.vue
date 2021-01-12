<template>
  <div
    class="forum"
    :class="{ pointer: canNavigate }"
    @click="canNavigate && navigate()"
  >
    <div class="header col">
      <div class="row spread">
        <h3 class="title" :style="styleObject">{{ forum.name }}</h3>
        <div
          v-if="isAdmin && showDelete"
          @click.stop="deleteForum()"
          class="action-btn row center-xy pointer"
        >
          <DeleteIcon title="Delete forum" fillColor="#FF0000"></DeleteIcon>
        </div>
      </div>
      <p class="description">{{ forum.description }}</p>
    </div>
    <div class="threads">
      <ThreadList :threads="threads" />
    </div>
  </div>
</template>

<script>
import { Vue, Component, Prop } from "vue-property-decorator";
import ThreadList from "./ThreadList";

@Component({
  components: { ThreadList },
})
class ForumItem extends Vue {
  @Prop({ default: 24 })
  fontSize;
  @Prop({ default: true })
  canNavigate;
  @Prop({ required: true })
  forum;
  @Prop({ default: false })
  showDelete;

  get styleObject() {
    return { fontSize: this.fontSize + "px" };
  }

  get threads() {
    return this.forum?.threads?.length ? this.forum.threads : [];
  }

  get isAdmin() {
    return this.$store.state.currentUser?.roles.includes("ADMIN");
  }

  navigate() {
    this.$router.push({ name: "Forum", params: { id: this.forum.id } });
  }

  deleteForum() {
    this.$store.dispatch("deleteForum", this.forum.id);
  }
}

export default ForumItem;
</script>

<style lang="scss" scoped>
.forum {
  margin: 5px 0;
  border: turquoise solid 1px;
  width: 100%;
  .header {
    padding: 5px 10px;
    border-bottom: turquoise solid 1px;
    .title,
    .description {
      padding: 5px 0;
    }
    .description {
      white-space: pre-wrap;
    }
  }
  .threads {
    padding: 5px 10px;
  }
}
</style>
