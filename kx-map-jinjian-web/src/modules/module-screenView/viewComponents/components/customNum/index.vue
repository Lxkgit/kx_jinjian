<template>
  <div class="customNum">
    <div class="left">
      <div class="num">{{ customerTotalNum }}</div>
      <div class="title">客户总数</div>
    </div>
    <div class="right">
      <div class="item" v-for="(item, index) in customerList" :key="index"
           @mouseenter="handleEnter(index)" @mouseleave="handleLeave(index)">
        <div class="dot" :style="{ background: item.color }"></div>
        <div class="type">{{ item.type }}</div>
        <div class="num" :style="{ color: item.color }">{{ item.num }}</div>
        <div class="image" v-show="item.show" @click="$emit('childClick')">
          <i class="el-icon-warning-outline"></i>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    customerTotalNum: {
      type: [String, Number]
    },
    customerList: {
      type: Array,
      default: () => [
        { color: "#148BDB", type: "绩效客户", num: 44854, show: false },
        { color: "#1CEDBB", type: "授信客户", num: 23135, show: false },
        { color: "#10E9DE", type: "建档客户", num: 7194, show: false },
        { color: "#FFFB8B", type: "价值客户", num: 7194, show: false },
      ],
    }
  },
  methods: {
    handleEnter(index) {
      this.customerList[index].show = true;
    },
    handleLeave(index) {
      this.customerList[index].show = false;
    },
  },
};
</script>

<style scoped lang="scss">
.customNum {
  margin-top: 10px;
  width: 100%;
  box-sizing: border-box;
  display: flex;
  .left {
    width: 130px;
    height: 130px;
    background: url("../../assets/custom-total.png") no-repeat center;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    .num {
      font-family: Number;
      font-weight: bold;
      font-size: 14px;
      color: rgb(62, 242, 255);
      // background: linear-gradient(
      //   0deg,
      //   rgb(62, 242, 255) 0%,
      //   rgb(222, 222, 222) 100%
      // );
      // background-clip: text;
      // -webkit-background-clip: text;
      // -webkit-text-fill-color: transparent;
    }
    .title {
      margin-top: 10px;
      font-size: 14px;
      color: #a8e3f2;
    }
  }
  .right {
    padding-right: 10px;
    margin-left: 30px;
    flex: 1;
    display: flex;
    flex-wrap: wrap;
    .item {
      width: 100%;
      padding: 5px 0;
      display: flex;
      align-items: center;
      position: relative;
      .dot {
        width: 6px;
        height: 6px;
      }
      .type {
        margin: 0 10px;
        font-size: 16px;
        color: #daf4ff;
      }
      .num {
        flex: 1;
        margin-right: 10px;
        text-align: right;
        font-family: Number;
        font-size: 18px;
        color: #32a8f8;
      }
      .image {
        position: absolute;
        right: -10px;
        color: #a8e3f2;
        font-size: 16px;
      }
    }
  }
}
</style>
