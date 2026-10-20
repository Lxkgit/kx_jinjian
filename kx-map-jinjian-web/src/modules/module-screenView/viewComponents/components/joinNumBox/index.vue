<template>
  <info-box class="joinBox">
    <div class="box-top">
      <div class="row-item" v-for="item in config" :key="item.label">
        <div class="value">{{ joinMsg[item.key] }}</div>
        <div class="label">{{ item.label }}</div>
      </div>
    </div>

    <titleWithProgress :ratio="joinMsg.ratio" class="progressBox">
      <div class="progress-title">{{ joinMsg.progressTitle }}</div>
    </titleWithProgress>
  </info-box>
</template>

<script>
import InfoBox from "../infoBox.vue";
import titleWithProgress from "../progressInfo/titleWithProgress.vue";
export default {
  name: "joinNumBox",
  components: {
    InfoBox,
    titleWithProgress,
  },
  props: {
    config: {
      type: Array,
      default: () => [
        { label: "扫码人数", key: "scanNum" },
        { label: "信息手机人数", key: "phoneNum" },
      ],
    },
    joinMsg: {
      type: Object,
      default: () => {
        return {
          scanNum: "1644",
          phoneNum: "1345",
          ratio: "66",
          progressTitle: "活动参与率",
        };
      },
    },
  },
  data() {
    return {};
  },
  methods: {},
};
</script>

<style lang="scss" scpoed>
.joinBox {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  .box-top {
    width: 85%;
    height: 35%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    .row-item {
      height: 100%;
      display: flex;
      flex-direction: column;
      justify-content: space-around;
      .value {
        font-size: 26px;
        font-family: Number;
        color: #09dfff;
        &::after {
          content: "人";
          color: #9dbec1;
          font-size: 18px;
          font-family: text;
          margin-left: 10px;
        }
      }
      .label {
        font-size: 16px;
        color: #fff;
      }
    }
  }

  .progressBox {
    width: 85%;
    margin-top: 15px;
    .progress-title {
      color: #fff;
      font-size: 16px;
    }
  }
}
</style>