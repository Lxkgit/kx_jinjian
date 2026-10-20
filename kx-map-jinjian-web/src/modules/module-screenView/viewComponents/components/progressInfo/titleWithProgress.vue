<template>
  <div class="title-with-progress-wrapper">
    <div class="title-wrapper">
      <slot />
      <span class="radio-num"
            :style="{ '--fontSize': fontSize, '--color': colors[3] }">{{ ratio }}%</span>
    </div>
    <div class="progress-wrapper" :style="{ '--borderColor': colors[0] }">
      <span class="progress-inter" :style="{
          '--width': `${ratio}%`,
          '--iconColor': colors[3],
          '--background': `linear-gradient(to left, ${colors[1]}, ${colors[2]})`,
        }" />
    </div>
  </div>
</template>

<script>
export default {
  props: {
    ratio: {
      type: String,
      default: "",
    },
    fontSize: {
      type: String,
      default: "18px",
    },
    colors: {
      type: Array,
      default: () => ["#00AFF8", "#00AEFF", "#00FFED", "#8BF6FF"],
    },
  },
};
</script>

<style lang="scss" scoped>
.title-with-progress-wrapper {
  width: 100%;
  .title-wrapper {
    width: 100%;
    display: flex;
    justify-content: space-between;
    margin-bottom: 13px;
    .radio-num {
      font-family: Number;
      font-size: var(--fontSize);
      color: var(--color);
    }
  }
  .progress-wrapper {
    width: 100%;
    height: 12px;
    border-radius: 4px;
    border: 1px solid var(--borderColor);
    padding: 1px;
    position: relative;
    overflow: hidden;
    .progress-inter {
      position: absolute;
      top: 50%;
      left: 0px;
      transform: translateY(-50%);
      display: inline-block;
      width: var(--width);
      height: 10px;
      background: var(--background);
      border-radius: 2px;
      &::after {
        content: "";
        position: absolute;
        right: 0;
        top: 50%;
        transform: translate(50%, -50%);
        width: 13px;
        height: 13px;
        border-radius: 50%;
        background: var(--iconColor);
      }
    }
  }
}
</style>
