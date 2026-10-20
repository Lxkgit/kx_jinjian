<template>
  <div class="legend-part-wrapper">
    <img src="../assets/image/legend.png" @click="showLegendHandle" alt="">
    <div class="legend-list-wrapper">
      <div class="list-header"></div>
    </div>
  </div>
</template>

<script>
export default ({
  data() {
    return {

    }
  },
  methods: {
    showLegendHandle() {

    }
  },
})
</script>

<style lang="scss" scoped>
.legend-part-wrapper {
  position: absolute;
  bottom: 25px;
  left: 465px;
  pointer-events: auto;
  > img {
    width: 60px;
    height: 60px;
  }
}
</style>