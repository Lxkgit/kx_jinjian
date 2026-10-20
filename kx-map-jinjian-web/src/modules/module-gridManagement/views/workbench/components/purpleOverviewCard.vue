<template>
  <div class="purple-overview-card-wrapper">
    <div class="card-logo-wrapper">
      <div class="card-logo">
        <img src="../assets/overview_1.png" alt="">
      </div>
      <span class="logo-title">{{ title }}</span>
    </div>
    <div class="card-content" v-for="(item,index) in detailData" :key="index">
      <div>
        <span class="item-value fs24">{{ item.value }}</span>
        <span class="item-unit">{{ item.unit }}</span>
      </div>
      <div class="item-label">{{ item.label }}</div>
    </div>
  </div>
</template>

<script>
export default ({
  props: {
    title: {
      type: String,
    },
    detailData: {
      type: Array,
    },
  },
})
</script>

<style lang="scss" scoped>
.purple-overview-card-wrapper {
  width: 100%;
  background: rgba($color: #8798ff, $alpha: 0.1);
  border-radius: 10px;
  padding: 15px;
  padding-right: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  margin-bottom: 7px;
  .card-logo-wrapper {
    padding-right: 28px;
    border-right: 1px #d8deff solid;
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
      color: #6a82ff;
    }
  }
  .card-content {
    line-height: 1.5;
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
      color: #6a82ff;
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
</style>