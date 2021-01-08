<template>
  <span class="locked"> - <span class="locked underline">LOCKED</span> </span>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";

@Component()
class LockedThreadMessage extends Vue {}

export default LockedThreadMessage;
</script>

<style lang="scss" scoped>
.locked {
  color: red;
}
.underline {
  text-decoration: underline;
}
</style>
