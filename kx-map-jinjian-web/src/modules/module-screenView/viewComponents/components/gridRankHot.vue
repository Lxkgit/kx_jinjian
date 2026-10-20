<template>
  <div>
    <gridRankList :columns="columns" :tableData="tableData" :rows="2" :height="height" :contentHeight='contentHeight'
      :intervalColor="intervalColor">
      <template v-slot:hot="slotProps">
        <div class="box"> <i v-for="(_, index) in 5" :key="index"
            :class="{ 'disabled': index >= slotProps.row.hot }"></i></div>
      </template>
    </gridRankList>
  </div>
</template>

<script>
import gridRankList from "./gridRankList";
export default {
  components: {
    gridRankList,
  },
  props: {
    tableData: {
      type: Array,
      default: () => [
        {
          city: "丽水市",
          number: 67,
          hot: "4",
        },
        {
          city: "绍兴市",
          number: 62,
          hot: "4",
        },
        {
          city: "舟山市",
          number: 58,
          hot: "3",
        },

        {
          city: "杭州市",
          number: 57,
          hot: "3",
        },
        {
          city: "金华市",
          number: 52,
          hot: "3",
        },
        {
          city: "宁波市",
          number: 49,
          hot: "2",
        },
        {
          city: "温州市",
          number: 45,
          hot: "2",
        },
        {
          city: "衢州市",
          number: 44,
          hot: "2",
        },
        {
          city: "湖州市",
          number: 40,
          hot: "2",
        },
        {
          city: "嘉兴市",
          number: 38,
          hot: "1",
        },








      ],
    },
  },
  data() {
    return {
      background:
        "linear-gradient(180deg,rgba(7, 192, 219, 0.3),rgba(7, 192, 219, 0.8)) ",
      intervalColor: "rgba(150, 252, 253, 0.1)",
      height: 50,
      contentHeight: 500,
      columns: [
        { label: "城市", prop: "city", flex: 1 },
        { label: "投诉数量", prop: "number", flex: 1 },
        { label: "热度", prop: "hot", flex: 2, slot: true },

      ],
    };
  },
};
</script>

<style lang="scss" scoped>
.box {
  display: flex;
  align-items: center;
  justify-content: center;

  i {
    width: 20px;
    height: 20px;
    vertical-align: baseline;
    background: url(../assets/hot.png) no-repeat;
    background-size: 100%;
    display: inline-block;
    margin-right: 2px;

    &.disabled {
      background: url(../assets/hot_disabled.png) no-repeat;
      background-size: 100%;
    }
  }
}
</style>