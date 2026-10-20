<template>
  <div class="blue-overview-card-wrapper">
    <div class="card-logo-wrapper">
      <div class="card-logo">
        <img src="../assets/overview_2.png" alt="">
      </div>
      <span class="logo-title">{{ title }}</span>
    </div>
    <div class="card-content">
      <span class="item-value fs24">{{ totalData&&totalData.value }}</span>
      <span class="item-unit">{{ totalData&&totalData.unit }}</span>
    </div>
    <div class="card-content">
      <div class="card-item" v-for="(item,index) in detailData" :key="index">
        <span class="item-label">{{ item.label }}</span>
        <span class="item-value">{{ item.value }}</span>
        <span class="item-unit">{{ item.unit }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default ({
  props: {
    title: {
      type: String,
    },
    totalData: {
      type: Object,
    },
    detailData: {
      type: Array,
    },
  },
})
</script>

<style lang="scss" scoped>
.blue-overview-card-wrapper {
  width: 100%;
  background: rgba($color: #7bc6ff, $alpha: 0.1);
  border-radius: 10px;
  padding: 8px 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  margin-bottom: 7px;

  .card-logo-wrapper {
    padding-right: 7%;
    border-right: 1px #cfe8ff solid;
    .card-logo {
      width: 26px;
      height: 26px;
      margin-bottom: 9px;
      > img {
        width: 100%;
        height: 100%;
      }
    }
    .logo-title {
      color: #44a5ff;
      white-space: nowrap;
    }
  }
  .card-content {
    text-align: center;
    .card-item {
      line-height: 1.5;
      white-space: nowrap;
    }
    .item-label {
      display: inline-block;
      color: #555555;
      margin-right: 9px;
    }
    .item-value {
      margin-right: 9px;
      display: inline-block;
      font-size: 16px;
      font-weight: bold;
      color: #44a5ff;
      &.fs24 {
        font-size: 24px;
      }
    }
    .item-unit {
      display: inline-block;
      font-size: 12px;
      color: #999999;
    }
  }
}
@media (min-width: 700px) {
}
</style>