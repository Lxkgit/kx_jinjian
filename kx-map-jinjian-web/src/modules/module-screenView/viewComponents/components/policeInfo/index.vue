<template>
  <div class="police">
    <div class="police-left">
      <img src="@/modules/module-screenView/assets/image/baozheng.png" alt="" />
      <div class="policeName">{{ dataItem.policeName }}</div>
    </div>
    <div class="police-right">
      <div class="police-item">
        <div class="item-left">反假联系人:</div>
        <div class="item-right">{{ dataItem.unFakerName }}</div>
      </div>
      <div class="police-item">
        <div class="item-left">联系电话:</div>
        <div class="item-right">{{ dataItem.unFakerPhone }}</div>
      </div>
      <div class="police-item">
        <div class="item-left">地址:</div>
        <div class="item-right">{{ dataItem.policeAddress }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    dataItem: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
};
</script>

<style scoped lang="scss">
.police {
  width: 100%;
  // height: 120px;
  background: rgba(30, 62, 69, 0.8);
  border-radius: 15px;
  margin-top: 10px;
  padding: 5px;
  display: flex;
  .police-left {
    display: flex;
    align-items: center;
    color: #fefefe;
    img {
      width: 50px;
    }
    .policeName {
      width: 60px;
      font-size: 18px;
      font-weight: bold;
      margin-left: 10px;
      line-height: 28px;
    }
  }
  .police-right {
    font-size: 14px;
    margin-left: 30px;
    .police-item {
      margin: 15px 0;
      display: flex;
      .item-left {
        color: #9dbec1;
        min-width: 60px;
      }
      .item-right {
        color: #fefefe;
        padding-left: 10px;
      }
    }
    .police-item:nth-of-type(1) .item-right {
      color: #fcbe3f;
    }
    position: relative;
    &::before {
      content: "";
      position: absolute;
      left: -20px;
      top: 50%;
      transform: translateY(-50%);
      width: 2px;
      height: 60px;
      background: linear-gradient(to bottom, #2c2929, #4cf5ff, #2c2929);
    }
  }
}
</style>