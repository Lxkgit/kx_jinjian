<template>
  <div class="gridDetailInfo">
    <div class="item" v-for="(item, index) in list" :key="index">
      <div class="content">
        <div class="value" v-number-conver="item.value"></div>
        <div class="label">
          {{item.label}}{{ Number(item.value)>99999999?` (万${item.unit}) `:` (${item.unit}) ` }}
        </div>
      </div>
      <div class="line" v-if="(index + 1) % 3 !== 0"></div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    list: {
      type: Array,
      default: () => [
        { label: "网格人口（人）", value: "867" },
        { label: "经济总量（元）", value: "232234" },
        { label: "小区数量（个）", value: "867" },
        { label: "小区人口（人）", value: "464" },
        { label: "工业园数量（个）", value: "987" },
        { label: "企业数量（个）", value: "75" },
        { label: "金融机构数量（个）", value: "14" },
        { label: "商圈个数（个）", value: "1234" },
      ],
    },
  },
};
</script>

<style scoped lang="scss">
.gridDetailInfo {
  margin: 10px auto;
  width: 100%;
  background: linear-gradient(
    0deg,
    rgba(53, 78, 88, 0.5),
    rgba(35, 55, 63, 0.08)
  );
  border: 1px solid;
  border-image: linear-gradient(180deg, #66cef5, #66cef500, #7ef4ff) 1 1;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  .item {
    width: 33.333%;
    height: 80px;
    display: flex;
    align-items: center;
    .content {
      width: calc(100% - 1px);
      display: flex;
      flex-direction: column;
      align-items: center;
      .value {
        font-family: Number;
        font-size: 20px;
        color: #ffffff;
      }
      .label {
        margin-top: 5px;
        font-size: 13px;
        color: #a8e3f2;
      }
      &:nth-child(3n + 3) {
        width: 100%;
      }
    }

    .line {
      width: 1px;
      height: 36px;
      background: linear-gradient(to bottom, #4cf5ff00, #4cf5ff, #4cf5ff00);
    }
  }
}
</style>
