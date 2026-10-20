<template>
  <div class="gridGrnreal">
    <div
      class="item"
      v-for="(item, index) in columnsList"
      :key="index"
      :style="{ 'grid-column': item.gridColumn }"
    >
      <div class="content">
        <div class="label">{{ item.label }}:</div>
        <div class="value" :title="value[item.key]">{{ value[item.key] }}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "gridGeneral",
  props: {
    columnsList: {
      type: Array,
      default: () => [
        { label: "网格名称", value: "XX网格", key: "gridName" },
        { label: "网格类型", value: "农村网格", key: "ruralGridType" },
        { label: "网格主管员", value: "XXX", key: "gridMainManagerName" },
        { label: "所属机构", value: "XXXX", key: "branch" },
        {
          label: "网格概况",
          value: "987",
          key: "gridOverview",
          gridColumn: "1/5",
        },
        {
          label: "网格备注",
          value: "75",
          key: "gridRemark",
          gridColumn: "1/5",
        },
        { label: "网格客户总数", value: "14", key: "gridCustomerTotal" },
        { label: "同业数量", value: "1234", key: "peersNum" },
        { label: "小区数量", value: "1234", key: "communityNum" },
        { label: "社区数量", value: "1234", key: "districtNum" },
        { label: "优质单位数量", value: "1234", key: "hiqUnitsNum" },
        {
          label: "优质单位情况",
          value: "1234",
          key: "hiqUnitsSituation",
          gridColumn: "2/5",
        },
      ],
    },
    value: {
      type: Object,
      default: () => {},
    },
  },
};
</script>

<style scoped lang="scss">
.gridGrnreal {
  margin: 10px auto;
  width: 100%;
  background: linear-gradient(
    0deg,
    rgba(53, 78, 88, 0.5),
    rgba(35, 55, 63, 0.08)
  );
  border: 1px solid;
  border-image: linear-gradient(180deg, #66cef5, #66cef500, #7ef4ff) 1 1;
  display: grid;
  gap: 5px;
  grid-template-columns: repeat(4, 1fr);
  grid-template-rows: repeat(5, 1fr);
  .item {
    height: 60px;
    display: flex;
    align-items: center;
    .content {
      width: calc(100% - 1px);
      display: flex;
      align-items: center;
      .value {
        // font-family: Number;
        font-size: 20px;
        color: #ffffff;
        max-width: 700px;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        overflow: hidden;
        -webkit-line-clamp: 2; /* 控制显示的行数 */
        text-overflow: ellipsis;
      }
      .label {
        // margin-top: 5px;
        min-width: 16px;
        font-size: 20px;
        color: #a8e3f2;
        margin: 0 10px 0 10px;
      }
    }
  }
}
</style>
