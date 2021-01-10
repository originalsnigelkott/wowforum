<template>
  <div>
    {{ user.username }} controls.
  </div>
</template>

<script>
import { Vue, Component, Prop } from 'vue-property-decorator';

@Component()
class UserControls extends Vue {
  @Prop({ required: true })
  user;
  
}

export default UserControls
</script>

<style lang='scss' scoped>
</style>