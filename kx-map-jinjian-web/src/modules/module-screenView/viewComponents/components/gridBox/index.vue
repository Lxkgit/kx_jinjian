<template>
  <div class="gridBox">
    <div class="item" v-for="(item, index) in list" :key="index">
      <img class="image" :src="require(`@/modules/module-screenView/assets/image/${item.pic}`)"
           alt="" />
      <div class="info">
        <div class="title">{{ item.label }}</div>
        <div class="content" :style="{ color: item.color }">
          {{ item.value }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    list: {
      type: Array,
      default: () => [
        {
          label: "四级网络",
          value: "11300",
          pic: "grid-four.png",
          color: "#49aaed",
        },
        {
          label: "五级网络",
          value: "3456",
          pic: "grid-five.png",
          color: "#8bf6ff",
        },
      ],
    },
  },
};
</script>

<style scoped lang="scss">
.gridBox {
  margin: 40px 0;
  width: 100%;
  display: flex;
  align-items: center;
  .item {
    width: 50%;
    box-sizing: border-box;
    padding: 0 5px;
    display: flex;
    align-items: center;
    justify-content: center;
    .image {
      width: 71px;
      height: 65px;
    }
    .info {
      margin-left: 5px;
      display: flex;
      flex-direction: column;
      .title {
        font-size: 16px;
        color: #ffffff;
      }
      .content {
        margin-top: 10px;
        font-family: Number;
        font-size: 21px;
        text-align: center;
      }
    }
  }
}
</style>
