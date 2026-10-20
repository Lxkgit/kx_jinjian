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
  dicts: ["self_evaluate_status"],
  data() {
    return {
      searchColumns: [
        {
          label: "名称",
          key: "name",
          type: "text",
        },
        {
          label: "自评状态",
          key: "selfEvaluation",
          type: "select",
          options: () => this.dict.type.self_evaluate_status,
        },
      ],
      maxHeight: window.innerHeight - 410,
      tableBtn: [
        {
          cname: "详情",
          ename: "viewDetail",
        },
        {
          cname: "工作评价",
          ename: "workEvaluate",
        },
        {
          cname: "删除",
          ename: "deleteEvaluate",
        },
      ],
      columns: [
        {
          label: "名称",
          prop: "evaluateName",
          showOverFlowTooltip: true,
          width: 230,
        },
        {
          label: "自评年度",
          prop: "selfEvaluateYear",
          showOverFlowTooltip: true,
        },
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
          label: "自评进度",
          prop: "selfEvaluateProgress",
          showOverFlowTooltip: true,
        },
        {
          label: "上级评价进度",
          prop: "superiorEvaluateProgress",
          showOverFlowTooltip: true,
        },
        {
          label: "下发时间",
          prop: "sendTime",
          showOverFlowTooltip: true,
          width: 150,
        },
        { label: "操作", slot: true, width: 150 },
      ],
      tableData: [
        {
          evaluateName: "2024年金华市银行机构网格工作评价",
          selfEvaluateYear: "2024年",
          selfEvaluateStatus: "1",
          selfEvaluateStartTime: "2024-01-01 15:00",
          selfEvaluateEndTime: "2024-02-01 17:00",
          selfEvaluateProgress: "6/19",
          superiorEvaluateProgress: "6/19",
          sendTime: "2024-02-01 15:00",
        },
        {
          evaluateName: "2023年金华市银行机构网格工作评价",
          selfEvaluateYear: "2024年",
          selfEvaluateStatus: "3",
          selfEvaluateStartTime: "2024-01-01 15:00",
          selfEvaluateEndTime: "2024-02-29 17:00",
          selfEvaluateProgress: "17/18",
          superiorEvaluateProgress: "17/18",
          sendTime: "2024-02-01 16:00",
        },
      ],
    };
  },
  methods: {
    fetchList() {
      this.$refs.filterBar.getForm((formInfo) => {
        // this.loading = true;
        const data = {
          ...this.formInline,
          ...formInfo,
        };
      });
    },
    viewDetail(row) {
      this.$router.push({
        name: "StaetupReview",
      });
    },
    workEvaluate(row) {
      this.$router.push({ name: "JobEvaluation" });
    },
    deleteEvaluate(row) {},
  },
  mounted() {
    this.fetchList();
  },
};
</script>

<style lang="scss" scoped></style>
