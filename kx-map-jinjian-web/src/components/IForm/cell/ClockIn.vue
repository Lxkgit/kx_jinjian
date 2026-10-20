<template>
  <div>
    <span class="warn-info" v-if="JSON.stringify(clockInData) === '{}' || !clockInData">
      打卡请前往手机端进行操作
    </span>
    <span v-else-if="clockInData.address">{{clockInData.address}}</span>
    <span v-else>{{clockInData}}</span>
  </div>
</template>

<script>
export default ({
  props: {
    value: {
      type: [Object,String],
      // default: () => { }
    }
  },
  data() {
    return {
      clockInData: this.value
    }
  },
  watch: {
    value: {
      handler(val) {
        this.clockInData = val
      },
      deep: true
    },
    clockInData: {
      handler(val) {
        this.$emit('input', val)
      },
      deep: true
    },
  },
})
</script>

<style lang="scss" scoped>
.warn-info {
  color: #999;
}
</style>