<template>
  <div class="info-box">
    <div class="left" v-if="leftKey">
      <div class="icon">
        <slot name="img"></slot>
      </div>
      <div class="leftTitle" :title="info[leftKey]">
        {{ info[leftKey] || "" }}
      </div>
    </div>
    <div class="right ">
      <div class="row" v-for="item in columns" :key="item.label">
        <span class="label">{{ item.label }}</span>
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
      default: () => { },
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
  background: url("../../assets/infobox1.png") no-repeat;
  background-size: 100% 100%;
  height: 136px;
  display: flex;
  align-items: center;
  margin-bottom: 10px;

  &.info-box-2 {
    height: 92px;
    background: url("../../assets/infobox2.png") no-repeat;
    background-size: 100% 100%;
  }

  &.info-box-3 {
    height: 114px;
    background: url("../../assets/infobox3.png") no-repeat;
    background-size: 100% 100%;
  }

  .left {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 120px;

    .icon {
      width: 40px;
      height: 40px;

      img {
        width: 100%;
      }
    }

    // position: relative;
    .leftTitle {
      justify-content: center;
      font-size: 14px;
      color: #01EAFF;
      line-height: 22px;
      width: 80px;
      word-break: break-all;
      text-align: center;
      overflow: hidden;
      display: -webkit-box;
      -webkit-line-clamp: 3;
      -webkit-box-orient: vertical;
    }
  }

  .right {

    padding: 0 10px;
    width: 300px;

    .row {
      margin: 7.5px 0;
      display: flex;
      line-height: 20px;
      justify-content: space-between;

      .label {
        color: #A8D6FF;
        width: 90px;
      }

      .value {

        text-align: right;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
      }
    }
  }

}
</style>