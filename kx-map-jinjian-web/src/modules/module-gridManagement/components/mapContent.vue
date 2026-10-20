<template>
  <div class="map-wrapper">

    <section>
      <slot name="map"></slot>
    </section>

    <div class="operate-wrapper">
      <div class="header-wrapper">
        <slot name="header"></slot>
      </div>
      <div class="operate-main-wrapper">
        <slot name="operate"></slot>
      </div>
      <div class="footer-wrapper">
        <slot name="footer"></slot>
      </div>
    </div>
    <slot></slot>
  </div>
</template>

<script>
export default ({
})
</script>

<style lang="scss" scoped>
.map-wrapper {
  width: 100%;
  height: 100%;
  position: relative;
  > section {
    width: 100%;
    height: 100%;
  }
  .operate-wrapper {
    position: absolute;
    width: 300px;
    height: calc(100% - 20px);
    top: 10px;
    right: 8px;
    background: #fff;
    box-shadow: 0px 2px 3px 0px rgba(70, 19, 12, 0.08);
    .operate-main-wrapper {
      height: calc(100% - 60px - 50px);
      padding: 0 10px;
      overflow: auto;
    }
    .header-wrapper {
      line-height: 36px;
    }
    .footer-wrapper {
      background: #f9fafe;
      box-shadow: -5px -6px 8px 0px rgba(153, 153, 153, 0.18);
      height: 60px;
      line-height: 60px;
    }
  }
}
</style>