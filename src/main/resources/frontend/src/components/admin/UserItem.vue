<template>
  <div class="user">
    {{ user.username }}
  </div>
</template>

<script>
import { Vue, Component, Prop } from "vue-property-decorator";

@Component()
class UserItem extends Vue {
  @Prop({ required: true })
  user;
}

export default UserItem;
</script>

<style lang="scss" scoped></style>
