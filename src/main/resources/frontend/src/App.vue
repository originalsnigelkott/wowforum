<template>
  <main>
    <Header />
    <router-view />
  </main>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import Header from "@/components/Header.vue";

@Component({
  components: { Header }
})
class App extends Vue {
  async created() {
    await this.$store.dispatch("fetchForums");
    await this.$store.dispatch("getCurrentUser");
  }
}

export default App;
</script>

<style lang="scss">
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  color: hotpink;
}

body {
  background-color: black;
  width: 100%;
  height: 100%;
}

main {
  padding-top: 75px;
  width: 100%;
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

.spread {
  justify-content: space-between;
}

.view {
  width: 100%;
  min-height: calc(100vh - 75px);
  padding: 10px 50px;
}

.pointer {
  cursor: pointer;
  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    &:hover {
      text-shadow: white 0 0 2px;
    }
  }
  &:hover {
    box-shadow: white 0 0 2px;
  }
}

input,
textarea {
  border-radius: 5px;
  border: none;
  padding: 5px;
  font-size: 16px;
  background-color: rgb(85, 85, 85);
  resize: none;
  &:focus {
    outline: none;
    box-shadow: hotpink 0 0 2px 2px;
  }
}

button {
  background: none;
  color: inherit;
  border: none;
  padding: 0;
  font: inherit;
  cursor: pointer;
  outline: inherit;
}

.btn {
  cursor: pointer;
  text-align: center;
  padding: 5px;
  border-radius: 5px;
  background-color: turquoise;
  &:hover {
    background-color: rgba(64, 224, 208, 0.938);
    text-shadow: white 0 0 2px;
    box-shadow: white 0 0 2px;
  }
}

.action-btn {
  width: 30px;
  height: 30px;
}

.material-design-icon {
  display: flex;
  justify-content: center;
  align-items: center;
}

@media screen and (max-width: 650px) {
  .view {
    padding: 5px 5px !important;
  }
}

@media screen and (min-width: 1200px) {
  .view {
    padding: 10px 100px !important;
  }
}

@media screen and (min-width: 1400px) {
  .view {
    padding: 10px 200px !important;
  }
}
@media screen and (min-width: 1600px) {
  .view {
    padding: 10px 300px !important;
  }
}
</style>
