<template>
  <ul class="product-select-wrapper">
    <li @click="clickHandle(item)" :class="{active: activeValue === item.value}"
        v-for="item in typeList" :key="item.value">
      <span>{{item.label}}</span>
    </li>
  </ul>
</template>

<script>
export default ({
  props: {
    value: {
      type: String,
    }
  },
  data() {
    return {
      activeValue: this.value,
      typeList: [
        { label: '乡村振兴贷', value: '1' },
        { label: '安心贷', value: '2' },
        { label: '共富贷', value: '3' },
      ]
    }
  },
  watch: {
    value(val) {
      this.activeValue = val
    },
    activeValue(val) {
      this.$emit('input', val)

    },
  },
  methods: {
    clickHandle({ value }) {
      this.activeValue = value
    }
  },
})
</script>

<style lang="scss" scoped>
.product-select-wrapper {
  width: 100%;
  height: 34px;
  line-height: 34px;
  border-radius: 17px;
  background: linear-gradient(0, rgba(17, 86, 111, 0.5), rgba(35, 55, 63, 0.1));
  margin: 10px 0;
  display: flex;
  padding: 0 16px;
  justify-content: space-between;
  > li {
    font-size: 16px;
    color: #fff;
    padding: 0 6px;
    cursor: pointer;
    &.active {
      font-weight: bold;
      color: #8bf6ff;
      border-bottom: 2px solid #8bf6ff;
    }
  }
}
</style>