<template>
  <div class="loans-info-wrapper">
    <span class="info-title">{{loansData.title}}</span>
    <div class="info-content-wrapper">
      <img :src="require(`@/modules/module-screenView/assets/image/${loansData.src}.png`)">
      <div class="left-info">
        <span class="info-value">{{ loansData.households }}</span>
        <span class="info-label">户数</span>
      </div>
      <div class="right-info">
        <span class="info-value">{{ loansData.money }}</span>
        <span class="info-label">金额</span>
      </div>
    </div>
    <badnessCard :badnessCardData="loansData"></badnessCard>
  </div>
</template>

<script>
import badnessCard from '../badnessCard'
export default ({
  components: {
    badnessCard
  },
  props: {
    loansData: {
      type: Object,
    }
  }
})
</script>

<style lang="scss" scoped>
.loans-info-wrapper {
  margin-bottom: 10px;
  .info-title {
    position: relative;
    display: inline-block;
    padding-left: 10px;
    font-size: 14px;
    color: #00f6ff;
    &::before {
      content: "";
      width: 6px;
      height: 6px;
      background: #00f6ff;
      border-radius: 50%;
      position: absolute;
      top: 50%;
      left: 0;
      transform: translateY(-50%);
    }
  }
  .info-content-wrapper {
    position: relative;
    width: 100%;
    height: 76px;
    background: url("../../assets/ck-bg.png") no-repeat;
    background-size: 100% 100%;
    text-align: center;
    > img {
      width: 70px;
      height: 70px;
      position: absolute;
      top: 50%;
      left: 49%;
      transform: translate(-50%, -50%);
    }
    .left-info {
      position: absolute;
      top: 50%;
      left: 25%;
      transform: translate(-50%, -50%);
    }
    .right-info {
      position: absolute;
      top: 50%;
      left: 75%;
      transform: translate(-50%, -50%);
    }
    .info-value {
      display: block;
      font-family: Number;
      font-weight: bold;
      font-size: 22px;
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
    .info-label {
      line-height: 1.5;
      text-align: center;
      font-weight: 500;
      font-size: 16px;
      color: #8bf6ff;
    }
  }
}
</style>