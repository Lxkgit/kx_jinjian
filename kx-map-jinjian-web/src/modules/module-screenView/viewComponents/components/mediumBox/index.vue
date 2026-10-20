<template>
  <div class="medium-box-wrapper">
    <info-box
      class="medium-item"
      v-for="(item, index) in creditData"
      :key="index"
    >
      <img :src="item.img" alt="" />
      <div class="content-wrapper">
        <div class="content-label">{{ item.leftLabel }}</div>
        <div class="content-num">{{ item.leftValue }}</div>
      </div>
      <div class="middle"></div>
      <div class="content-wrapper right">
        <div class="content-label">{{ item.rightLabel }}</div>
        <div class="content-num">{{ item.rightValue }}</div>
      </div>
    </info-box>
  </div>
</template>

<script>
import infoBox from "../infoBox.vue";
export default {
  components: { infoBox },
  name: "mediumBox",
  props: {
    creditData: {
      type: Array,
      default: () => [],
    },
  },
  methods: {},
};
</script>

<style lang="scss" scoped>
.medium-box-wrapper {
  .medium-item {
    width: 100%;
    height: 100px;
    display: flex;
    justify-content: space-around;
    align-items: center;
    padding: 0 14px;
    margin-bottom: 20px;
    > img {
      width: 60px;
      margin-right: 14px;
    }
    .content-wrapper {
      flex: 1;
      .content-label {
        color: #fff;
        font-size: 16px;
        line-height: 1.5;
      }
      .content-num {
        font-family: Number;
        font-weight: bold;
        font-size: 22px;
        // background: linear-gradient(
        //   0deg,
        //   rgb(62, 203, 255) 0%,
        //   rgb(222, 222, 222) 100%
        // );
        color: rgb(62, 203, 255);
        // background-clip: text;
        // -webkit-background-clip: text;
        // -webkit-text-fill-color: transparent;
      }
      &.right {
        flex: 1.5;
        .content-num {
          color: rgb(221, 202, 115);
          // background: linear-gradient(
          //   0deg,
          //   rgb(221, 202, 115) 0%,
          //   rgb(222, 222, 222) 100%
          // );
          // background-clip: text;
          // -webkit-background-clip: text;
          // -webkit-text-fill-color: transparent;
        }
      }
    }
    .middle {
      margin: 0 14px;
      width: 1px;
      height: 70%;
      background-image: linear-gradient(#999, #4cf5ff, #999);
      border-radius: 50%;
    }
  }
}
</style>
