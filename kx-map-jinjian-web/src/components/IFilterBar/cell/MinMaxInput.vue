<template>
  <div class="min-max-input-wrapper">
    <el-input-number v-model="intervalData[0]" :min="0" :max="intervalData[1]" :controls="false"
                     placeholder="最小值"></el-input-number>
    <span>-</span>
    <el-input-number v-model="intervalData[1]" :min="intervalData[0]" :controls="false"
                     placeholder="最大值"></el-input-number>
  </div>
</template>

<script>
export default {
  props: {
    value: {
      type: Array,
    },
  },
  data() {
    return {
      intervalData: this.value,
    };
  },
  watch: {
    value: {
      handler(val) {
        this.intervalData = val;
      },
      deep: true,
    },
    intervalData: {
      handler(val) {
        this.$emit("input", val);
      },
      deep: true,
    },
  },
};
</script>

<style lang="scss" scoped>
.min-max-input-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
  > span {
    display: block;
    margin: 0 30px;
  }
  ::v-deep .el-input-number--medium {
    width: 100%;
  }
}
</style>
