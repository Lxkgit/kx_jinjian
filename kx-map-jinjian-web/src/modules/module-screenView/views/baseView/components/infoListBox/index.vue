<template>
  <div class="info-box">
    <div class="left" v-if="leftKey">
      <slot name="img"></slot>
      <div class="leftTitle" :title="info[leftKey]">
        {{ info[leftKey] || "" }}
      </div>
    </div>
    <div class="right leftLine">
      <div class="row" v-for="item in columns" :key="item.label">
        <span class="label">{{ item.label }}:</span>
        <span class="value" :style="item.valueStyle" :title="info[item.key]">{{
          info[item.key] || "—"
        }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "infoListBox",
  props: {
    info: {
      type: Object,
      default: () => {},
    },
    leftImg: {
      type: String,
    },
    leftKey: {
      type: String,
    },
    columns: {
      type: Array,
      default: [],
    },
  },
  data() {
    return {};
  },
};
</script>

<style lang="scss" scoped>
.info-box {
  width: 100%;
  color: #fff;
  background: url("~@/modules/module-screenView/assets/image/infoBox-bg.png")
    no-repeat;
  background-size: 100% 100%;
  // height: 130px;
  padding: 15px 0;
  display: flex;
  align-items: center;
  .left {
    margin: 0 10px 0 10px;
    display: flex;
    align-items: center;
    width: 100px;
    // position: relative;
    .leftTitle {
      // font-size: 16px;
      width: 60px;
      word-break: break-all;
      overflow: hidden;
      display: -webkit-box;
      -webkit-line-clamp: 4;
      -webkit-box-orient: vertical;
    }
    // &::after {
    //   content: "";
    //   position: absolute;
    //   right: -17.5px;
    //   top: 0;
    //   transform: translateX(-50%);
    //   width: 2px;
    //   height: 100%;
    //   // height: 60px;
    //   background: linear-gradient(to bottom, #2c2929, #4cf5ff, #2c2929);
    //   border-radius: 50%;
    //   // background: red;
    // }
  }
  .right {
    margin-left: 20px;
    .row {
      margin: 7.5px 0;
      display: flex;
      .label {
        min-width: fit-content;
      }
      .value {
        margin-left: 5px;
        word-break: break-all;
        overflow: hidden;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
      }
    }
  }
  .leftLine {
    position: relative;
    &::after {
      content: "";
      position: absolute;
      left: -17.5px;
      top: 0;
      transform: translateX(-50%);
      width: 2px;
      height: 100%;
      // height: 60px;
      background: linear-gradient(to bottom, #2c2929, #4cf5ff, #2c2929);
      border-radius: 50%;
      // background: red;
    }
  }
}
</style>