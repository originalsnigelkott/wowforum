<template>
  <div class="controls col">
    <div class="control-section">
      <ModerationControls :user="user" />
    </div>
  </div>
</template>

<script>
import { Vue, Component, Prop } from "vue-property-decorator";
import ModerationControls from "./ModerationControls";

@Component({
  components: { ModerationControls },
})
class UserControls extends Vue {
  @Prop({ required: true })
  user;
}

export default UserControls;
</script>

<style lang="scss">
.controls {
  width: 100%;
  .control-section {
    padding: 5px;
    border-bottom: turquoise solid 1px;
    .title {
      font-size: 18px;
    }
    .subtitle {
      font-size: 16px;
    }
  }
}
</style>
