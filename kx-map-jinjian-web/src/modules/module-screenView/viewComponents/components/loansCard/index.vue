<template>
  <info-box class="card-wrapper">
    <span class="card-num">{{ cardNum }}</span>
    <span class="card-label">{{ cardLabel }}</span>
    <span class="sub-wrapper">
      <span class="sub-label">{{ subLabel }}</span>
      <span class="sub-num">{{ subNum }}</span>
    </span>
  </info-box>
</template>

<script>
import infoBox from "../infoBox.vue";
export default ({
  props: {
    cardNum: String,
    cardLabel: String,
    subLabel: String,
    subNum: String,
  },
  components: {
    infoBox
  },
})
</script>

<style lang="scss" scoped>
.card-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 7px 10px;
  .card-num {
    font-weight: bold;
    font-family: Number;
    // background: linear-gradient(
    //   0deg,
    //   rgb(62, 203, 255) 0%,
    //   rgb(222, 222, 222) 100%
    // );
    font-size: 20px;
    color: rgb(62, 203, 255);
    // background-clip: text;
    // -webkit-background-clip: text;
    // -webkit-text-fill-color: transparent;
  }
  .card-label {
    font-size: 15px;
    color: #fff;
    line-height: 1.5;
  }
  .sub-wrapper {
    width: 90%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    .sub-label {
      color: #9dbec1;
    }
    .sub-num {
      font-size: 16px;
      color: #ffdb6c;
      font-family: Number;
      font-weight: bold;
    }
  }
}
</style>