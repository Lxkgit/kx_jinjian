<template>
  <div
    class="button-operation-wrapper"
    v-clickoutside="() => (showSelect = false)"
  >
    <img :src="src" class="button-wrapper" @click="showSelect = !showSelect" />
    <div
      v-show="showSelect"
      class="operation-wrapper"
      :class="{
        left: direction === 'left',
        right: direction === 'right',
        rightTop: direction === 'rightTop',
      }"
    >
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    src: {
      type: String,
    },
    direction: {
      type: String,
      default: "left",
    },
  },
  data() {
    return {
      showSelect: false,
    };
  },
  computed: {},
};
</script>

<style lang="scss" scoped>
.button-operation-wrapper {
  margin-bottom: 9px;
  .button-wrapper {
    width: 70px;
    height: 70px;
  }
  .operation-wrapper {
    position: absolute;
    top: 48px;

    &.left {
      right: 0;
      transform: translate(105%, -50%);
    }

    &.right {
      left: 0;
      transform: translate(-105%, 75%);
    }

    &.rightTop {
      left: 0;
      transform: translate(-102.5%, -90%);
    }
  }
}
</style>