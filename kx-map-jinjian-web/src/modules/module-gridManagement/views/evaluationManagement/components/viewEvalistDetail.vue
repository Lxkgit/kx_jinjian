<template>
  <i-dialog
    title="评价得分"
    v-model="showDialog"
    width="70%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    appendToBody
  >
    <span style="font-size: 20px; font-weight: 900">人民银行金华市分行</span>
    <div class="statistic-box">
      <div>
        <span class="label">评价年度：</span>
        <span class="value">2025年度</span>
      </div>
      <div>
        <span class="label">综合评价得分：</span>
        <span class="value">100</span>
      </div>
    </div>
    <i-table
        class="table"
      ref="table"
      :loading="loading"
      :data="tableData"
      align="center"
      border
      :allColumns="columns"
      :stripe="false"
      :showPagination="false"
    >
      <template #action="scope">
        <i-table-btns
          :btn-lists="tableBtn"
          @click="(type) => clickHandle(type, scope.row)"
          :data="scope.row"
        >
        </i-table-btns>
      </template>
    </i-table>
  </i-dialog>
</template>
<script>
import dialogMixin from "@/mixins/dialogMixin.vue";
import pageMixin from "@/mixins/pageMixin";
export default {
  mixins: [dialogMixin, pageMixin],
  data() {
    return {
      columns: [
        { label: "评价内容", prop: "content" },
        {
          label: "评价标准",
          prop: "fullMarks",
        },
        {
          label: "评价项目",
          prop: "project",
          width: "160",
        },

        {
          label: "扣分标准",
          prop: "scoreStandards",
        },
        {
          label: "得分",
          prop: "score",
        },
      ],
      tableData: [
        {
          content: "网格划分情况",
          fullMarks: "25",
          project: "是否以一社区（行政村）一网格划分网格",
          scoreStandards: "与网格化管理平台地图中社区、行政村数进行比对，未按要求进行网格划分的扣5分",
          score: "25",
        },
        {
          content: "网格化管理覆盖情况",
          fullMarks: "25",
          project: "是否以银行机构全覆盖、任务与体量相适配的原则分配网格",
          scoreStandards: "未做到当地银行机构全覆盖的扣5分",
          score: "25",
        },
        {
          content: "网格责任网点信息更新情况",
          fullMarks: "25",
          project: "网格信息是否及时更新",
          scoreStandards: "1.银行网点因迁移、变更、撤并等不适合继续承担网格责任网点职责，却仍承担网格职责的，1个网点扣5分，直至该项分数扣完；2.网格长、网格员的姓名、联系方式等信息为及时更新，1人扣0.5分，直至该项分数扣完。",
          score: "25",
        },
        {
          content: "工作报告情况",
          fullMarks: "25",
          project: "银行机构是否每半年向当地人民银行报告网格化工作开展情况",
          scoreStandards: "1家银行机构未报送工作报告扣2分，直至该项分数扣完。",
          score: "25",
        },
      ],
    };
  },
  mounted() {},
  methods: {
    handleConfirm() {},
    handleClose() {},
  },
};
</script>
<style scoped lang="scss">
.statistic-box {
  display: flex;
  align-items: center;
  margin-top: 20px;
  padding: 0 10px;
  font-size: 18px;
  margin-bottom: 15px;
  div:nth-of-type(1) {
    margin-right: 40px;
  }
  .label {
    margin-right: 10px;
  }
}
.table ::v-deep .el-table .el-table__body-wrapper td.el-table__cell{
    border: 0.5px solid #EBEEF5;
}
</style>