<template>
  <!-- <div class="button-select-wrapper" v-clickoutside="() => (showSelect = false)"> -->
  <div class="button-select-wrapper">
    <div class="button-name" @click="showSelect = !showSelect">
      {{ buttonName }}
    </div>
    <div class="select-wrapper" v-show="showSelect">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    value: {
      type: Boolean,
    },
    buttonName: {
      type: String,
    },
  },
  data() {
    return {
      showSelect: this.value,
    };
  },
  watch: {
    value(val) {
      this.showSelect = val
    },
    showSelect(val) {
      this.$emit('input', val)
    }
  },
};
</script>

<style lang="scss" scoped>
.button-select-wrapper {
  margin-left: 20px;
  position: relative;

  .button-name {
    width: 100px;
    height: 36px;
    background: url('~@/modules/module-screenView/assets/image/btn_map.png') no-repeat;
    background-size: 100%;
    font-size: 16px;
    color: #fff;
    text-align: center;
    line-height: 36px;
    cursor: pointer;
  }

  .select-wrapper {
    position: absolute;
    top: 150%;
    left: -600%;

  }
}
</style>
