<template>
  <div class="view">
    <ForumList />
  </div>
</template>

<script>
import { Vue, Component } from "vue-property-decorator";
import ForumList from "../components/home/ForumList";

@Component({
  components: { ForumList },
})
class Home extends Vue {}

export default Home;
</script>

<style lang="scss" scoped>

</style>
