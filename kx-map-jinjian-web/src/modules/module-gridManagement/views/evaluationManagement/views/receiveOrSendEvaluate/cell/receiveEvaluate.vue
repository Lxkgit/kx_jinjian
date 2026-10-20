<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar
        ref="filterBar"
        slot="filter"
        :columns="searchColumns"
        :gridColumns="4"
      >
        <div v-show="searchColumns.length > 0">
          <el-button type="primary" @click="searchHandle">搜索</el-button>
          <el-button @click="resetHandle">重置</el-button>
        </div>
      </i-filter-bar>
      <!-- 按钮 -->
      <i-button-bar
        :btn-lists="btnLists"
        @click="clickHandle"
        slot="btn"
        ref="btnBar"
      >
      </i-button-bar>
      <!-- 列表 -->
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        stripe
        :allColumns="columns"
        :total="total"
        :pageNum="formInline.pageNum"
        :pageSize="formInline.pageSize"
        @currentChange="currentChange"
        @sizeChange="sizeChange"
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
    </PageContent>
  </div>
</template>

<script>
import pageMixin from "@/mixins/pageMixin";
export default {
  mixins: [pageMixin],
  dicts: ["self_evaluate_status", "final_evaluate_status"],
  data() {
    return {
      searchColumns: [
        {
          label: "名称",
          key: "evaluateName",
          type: "text",
        },
        {
          label: "自评状态",
          key: "selfEvaluationStatus",
          type: "select",
          options: () => this.dict.type.self_evaluate_status,
        },
      ],
      maxHeight: window.innerHeight - 410,
      tableBtn: [
        {
          cname: "自评",
          ename: "toSelfEvaluation",
        },
        {
          cname: "上级评价",
          ename: "superiorEvaluation",
          isShow: (data) => data.superiorEvaluateProgress == "2",
        },
        {
          cname: "下发",
          ename: "sendEvaluation",
        },
      ],
      columns: [
        { label: "名称", prop: "evaluateName", showOverFlowTooltip: true },
        { label: "自评年度", prop: "evaluateYear", showOverFlowTooltip: true },
        {
          label: "自评状态",
          prop: "selfEvaluateStatus",
          showOverFlowTooltip: true,
          formatter: ({ selfEvaluateStatus }) => {
            return this.dict.label.self_evaluate_status[selfEvaluateStatus];
          },
        },
        {
          label: "自评开始时间",
          prop: "selfEvaluateStartTime",
          showOverFlowTooltip: true,
          width: 150,
        },
        {
          label: "自评结束时间",
          prop: "selfEvaluateEndTime",
          showOverFlowTooltip: true,
          width: 150,
        },
        {
          label: "上级评价进度",
          prop: "superiorEvaluateProgress",
          showOverFlowTooltip: true,
          formatter: ({ superiorEvaluateProgress }) => {
            return this.dict.label.final_evaluate_status[
              superiorEvaluateProgress
            ];
          },
        },
        { label: "操作", slot: true },
      ],
      tableData: [
        {
          evaluateName: "2024年金华市银行机构网格工作评价",
          evaluateYear: "2024年",
          selfEvaluateStatus: "2",
          selfEvaluateStartTime: "2024-01-01 15:00",
          selfEvaluateEndTime: "2024-02-01 17:00",
          superiorEvaluateProgress: "2",
        },
        {
          evaluateName: "2024年金华市银行机构网格工作评价",
          evaluateYear: "2023年",
          selfEvaluateStatus: "1",
          selfEvaluateStartTime: "2024-01-01 15:00",
          selfEvaluateEndTime: "2024-02-01 17:00",
          superiorEvaluateProgress: "1",
        },
      ],
    };
  },
  methods: {
    fetchList() {
      this.$refs.filterBar.getForm((formInfo) => {
        const data = {
          ...this.formInline,
          ...formInfo,
        };
      });
    },
    toSelfEvaluation() {
      this.$router.push({ name: "SelfEvaluate" });
    },
    superiorEvaluation() {
      this.$router.push({ name: "SuperiorEvaluate" });
    },
    sendEvaluation() {
      this.$router.push({ name: "StaetupReview" });
      
    }
  },
  mounted() {
    this.fetchList();
  },
};
</script>

<style lang="scss" scoped></style>
