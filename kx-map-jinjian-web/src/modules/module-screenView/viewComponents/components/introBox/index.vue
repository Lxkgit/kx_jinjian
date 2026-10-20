<template>
  <div class="introBox">
    <div class="detailbox" v-for="(item, index) in list" :key="index">
      <div class="box" :class="item.className">{{ item.value }}</div>
      <div class="intro">{{ item.title }}</div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    list: {
      type: Array,
      default: () => [
        { title: "网格名称", value: "xx网格" },
        { title: "网格主管员", value: "王小明" },
        { title: "网格等级", value: "AAA", className: "level" },
        { title: "所属支行", value: "拱墅支行" },
      ],
    },
  },
};
</script>

<style scoped lang="scss">
.introBox {
  margin: 40px 0;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  .detailbox {
    width: 104px;
    background: url("../../assets/intro-box.png") no-repeat;
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    justify-content: center;
    .box {
      width: 100%;
      height: 45px;
      padding: 10px 0;
      line-height: 18px;
      text-align: center;
      font-size: 16px;
      color: #fff;
      &.level {
        color: red;
        font-weight: bold;
      }
    }
    .intro {
      text-align: center;
      margin-top: 5px;
      width: 100%;
      font-size: 16px;
      color: #a8e3f2;
    }
  }
}
</style>
