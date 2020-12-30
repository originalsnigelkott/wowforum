<template>
  <main>
    <Header />
    <router-view />
  </main>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import Header from "./components/Header.vue";

@Component({
  components: { Header },
})
class App extends Vue {}

export default App;
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

main {
  padding-top: 75px;
}

.row {
  display: flex;
}

.col {
  display: flex;
  flex-direction: column;
}

.center-x {
  justify-content: center;
}

.center-y {
  align-items: center;
}

.center-xy {
  justify-content: center;
  align-items: center;
}
</style>
