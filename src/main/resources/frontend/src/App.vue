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
  components: { Header },
})
class App extends Vue {}

export default App;
</script>

<style lang="scss">
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  color: hotpink;
}

main {
  padding-top: 75px;
  background-color: black;
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

.view {
  min-width: 100vw;
  min-height: calc(100vh - 75px);
  padding: 10px 30px;
}

.pointer {
  cursor: pointer;
  h1, h2, h3, h4, h5, h6 {
    &:hover {
      text-shadow: white 0 0 2px;
    }
  }
  &:hover {
    box-shadow: white 0 0 2px;
  }
}

input {
  border-radius: 5px;
  border: none;
  padding: 5px;
  font-size: 16px;
  background-color: rgb(85, 85, 85);
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
    background-color:rgba(64, 224, 208, 0.938);
    text-shadow: white 0 0 2px;
    box-shadow: white 0 0 2px;
  }
}
</style>
