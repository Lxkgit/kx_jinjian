<template>
  <div class="smallBox">
    <div class="left">
      <div class="icon"></div>
    </div>
    <div class="right">
      <div class="title">{{ info.title }}</div>
      <div class="valueBox"><span class="value">{{ info.value }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "smallBox",
  props: {
    info: {
      type: Object,
      default: () => {
        return {
          title: '投诉事件数量',
          value: "3603"
        };
      },
    },

  },
  data() {
    return {};
  },

};
</script>

<style lang="scss" scoped>
.smallBox {
  display: flex;
  width: 430px;
  justify-content: left;
  padding: 10px 8px 10px 10px;
  background: linear-gradient(0deg, rgba(255, 0, 0, 0.4) 0%, rgba(107, 188, 228, 0) 100%), linear-gradient(0deg, rgba(250, 93, 93, 0.2) 0%, rgba(228, 107, 127, 0) 100%), linear-gradient(180deg, rgba(255, 61, 61, 0) 0%, rgba(255, 61, 61, 0.3) 100%);
  box-sizing: border-box;
  margin-bottom: 20px;

  .left {
    width: 52px;
    height: 44px;
    margin-right: 12px;

    .icon {
      width: 52px;
      height: 44px;
      background: url('../../assets/cardOneIcon.png') no-repeat;
      background-size: 100%;
    }
  }

  .right {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;

    .title {
      font-size: 16px;
      color: #fff;
    }

    .valueBox {
      align-items: baseline;
      line-height: 22px;

      .value {
        font-size: 22px;
        font-weight: bold;
        text-align: right;
        display: flex;
        align-items: center;
        letter-spacing: 1.22px;
        background: linear-gradient(180deg, #FFFFFF 0%, #FFFFFF 24%, #FF0000 91%, #FF0011 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        background-clip: text;
      }
    }
  }
}
</style>
