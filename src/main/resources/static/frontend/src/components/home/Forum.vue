<template>
  <div>
    <h4>{{ forum.name }}</h4>
  </div>
</template>

<script>
import { Vue, Component, Prop } from 'vue-property-decorator';

@Component()
class Forum extends Vue {
  @Prop({ required: true })
  forum
}

export default Forum
</script>

<style lang="scss" scoped>

</style>