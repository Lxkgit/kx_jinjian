<template>
  <div class="ftpBox">
    <div class="title">{{ title }}</div>
    <div class="value">{{ value }}</div>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: "FTP",
    },
    value: {
      type: String,
      default: "0",
    },
  },
};
</script>

<style scoped lang="scss">
.ftpBox {
  margin: 10px auto;
  width: 100%;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: url("../../assets/ftp-box.png") no-repeat center;
  background-size: 100% 100%;

  .title {
    font-size: 18px;
    color: #8bf6ff;
    margin-right: 10px;
  }
  .value {
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
}
</style>
