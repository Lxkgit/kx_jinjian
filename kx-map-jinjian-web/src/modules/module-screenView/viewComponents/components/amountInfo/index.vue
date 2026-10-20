<template>
  <infoBox class="amount-info-wrapper">
    <div class="info-left-wrapper">
      <img :src="require(`@/modules/module-screenView/viewComponents/assets/${infoData.src}.png`)"
           alt="">
      <div class="left-item">
        <span class="left-label">{{infoData.label}}</span>
        <span class="left-value" :style="{'--color': infoData.color}">{{infoData.value}}</span>
      </div>
    </div>
    <div class="info-line"></div>
    <div class="info-right-wrapper">
      <div class="right-item">
        <span class="right-label">较上月</span>
        <span class="right-value" v-number-color="infoData.month"></span>
      </div>
      <div class="right-item">
        <span class="right-label">较年初</span>
        <span class="right-value" v-number-color="infoData.year"></span>
      </div>
    </div>
  </infoBox>
</template>

<script>
import infoBox from '../infoBox'
export default ({
  components: {
    infoBox
  },
  props: {
    infoData: {
      type: Object,
    }
  }
})
</script>

<style lang="scss" scoped>
.amount-info-wrapper {
  width: 100%;
  height: 65px;
  display: flex;
  padding: 8px 12px;
  margin: 10px 0;
  .info-left-wrapper {
    flex: 1;
    display: flex;
    align-items: center;
    img {
      width: 56px;
      height: 42px;
      margin-right: 10px;
    }
    .left-item {
      .left-label {
        display: block;
        font-size: 16px;
        color: #ffffff;
      }
      .left-value {
        font-family: Number;
        // background: var(--color);
        font-size: 20px;
        color: var(--color);
        // background-clip: text;
        // -webkit-background-clip: text;
        // -webkit-text-fill-color: transparent;
      }
    }
  }
  .info-line {
    width: 1px;
    height: 90%;
    background: linear-gradient(to bottom, #4cf5ff00, #4cf5ff, #4cf5ff00);
    margin: 0 15px;
  }
  .info-right-wrapper {
    flex: 1;
    display: flex;
    align-items: center;
    .right-item {
      width: 50%;
      .right-label {
        display: block;
        color: #9dbec1;
        font-size: 14px;
      }
      .right-value {
        font-size: 20px;
        color: #8bf6ff;
        font-family: Number;
      }
    }
  }
}
</style>