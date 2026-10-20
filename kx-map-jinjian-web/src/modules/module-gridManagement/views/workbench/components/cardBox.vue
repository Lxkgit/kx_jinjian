<template>
  <div class="card-box-wrapper"
       :style="{'--width':width,'--height':height,'--minHeight': minHeight,'--maxHeight': maxHeight}">
    <div class="card-header" v-if="title">
      <span class="header-text">
        {{ title }}
      </span>
      <slot name="operate"></slot>
    </div>
    <section :style="{'--overflow':overflow}">
      <slot></slot>
    </section>
  </div>
</template>

<script>
export default ({
  props: {
    width: {
      type: String,
    },
    height: {
      type: String,
    },
    minHeight: {
      type: String
    },
    maxHeight: {
      type: String
    },
    title: {
      type: String,
    },
    overflow: {
      type: String,
      default: 'auto'
    },
  },
})
</script>

<style lang="scss" scoped>
.card-box-wrapper {
  width: var(--width);
  height: var(--height);
  min-height: var(--minHeight);
  max-height: var(--maxHeight);
  background: #ffffff;
  box-shadow: 0px 2px 3px 0px rgba(70, 19, 12, 0.08);
  border-radius: 4px;
  margin-bottom: 15px;
  padding: 15px 20px;
  .card-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 10px;
    .header-text {
      position: relative;
      padding-left: 20px;
      font-size: 18px;
      &::before {
        content: "";
        position: absolute;
        top: 50%;
        left: 0;
        transform: translateY(-50%);
        width: 15px;
        height: 15px;
        background: #ffffff;
        border: 3px solid #4977fc;
        border-radius: 50%;
      }
    }
  }
  section {
    height: calc(100% - 28px);
    overflow: var(--overflow);
    &::-webkit-scrollbar {
      width: 0;
    }
  }
}
</style>