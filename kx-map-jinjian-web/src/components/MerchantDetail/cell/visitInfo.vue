<template>
  <div class="visit-info-wrapper">
    <interView :institutionId="institutionId" />
  </div>
</template>

<script>
import interView from '../components/interView.vue';
import mixin from '../mixins/mixin'
export default ({
  components: {
    interView
  },
  mixins: [mixin],
  mounted() {
    this.init();
  },
})
</script>

<style lang="scss" scoped>
</style>