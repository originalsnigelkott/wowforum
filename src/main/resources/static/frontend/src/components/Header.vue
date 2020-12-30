<template>
  <div class="header row center-xy">
    <h1 class="title">Wow forum</h1>
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";

@Component()
class Header extends Vue {}

export default Header;
</script>

<style lang="scss" scoped>
.header {
  height: 75px;
  width: 100vw;
  position: fixed;
  top: 0;
  right: 0;
  z-index: 999;
  background-color: turquoise;
}
</style>
