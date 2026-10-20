<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar
        ref="filterBar"
        slot="filter"
        :columns="searchColumns"
        :gridColumns="5"
        labelWidth="90px"
      >
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
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
        :maxHeight="maxHeight"
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
import { mapState, mapMutations } from "vuex";
import pageMixin from "@/mixins/pageMixin";
export default {
  name: "useCashEnv",
  mixins: [pageMixin],
  dicts: ["spot_check_status"],
  data() {
    return {
      searchColumns: [
        {
          label: "抽查时间",
          key: "spotCheckTime",
          type: "daterange",
          startPlaceholder: "开始日期",
          endPlaceholder: "结束日期",
          rangeSeparator: "至",
          format: "yyyy-MM-dd",
          valueFormat: "yyyy-MM-dd",
          unlinkPanels: true,
          span: 2,
        },
        {
          label: "抽查状态",
          key: "spotCheckStatus",
          type: "select",
          options: () => this.dict.type.spot_check_status,
        },
      ],
      btnLists: [
        {
          cname: "新增现金使用环境抽查",
          ename: "addCashUseEnvCheck",
          type: "primary",
        },
      ],
      tableBtn: [
        {
          cname: "编辑",
          ename: "edit",
        },
        {
          cname: "删除",
          ename: "delete",
        },
        {
          cname: "抽查记录",
          ename: "spotCheckRecord",
        },
        {
          cname: "评价明细",
          ename: "evaluationDetail",
        },
      ],
      columns: [
        {
          label: "抽查时间",
          prop: "spotCheckTime",
          // showOverFlowTooltip: true,
          width: 160,
          formatter: ({ spotCheckTime }) => {
            return spotCheckTime[0] + "-" + spotCheckTime[1];
          },
        },
        {
          label: "抽查人员数量",
          prop: "spotCheckPersonNum",
          showOverFlowTooltip: true,
        },
        {
          label: "抽查状态",
          prop: "spotCheckStatus",
          showOverFlowTooltip: true,
          formatter: ({ spotCheckStatus }) => {
            return this.dict.label.spot_check_status[spotCheckStatus];
          },
        },
        {
          label: "确定抽查商户数量",
          prop: "ensureSpotCheckStoreNum",
          showOverFlowTooltip: true,
        },
        {
          label: "实际抽查商户数量",
          prop: "actualSpotCheckStoreNum",
          showOverFlowTooltip: true,
        },
        {
          label: "确定抽查网点数量",
          prop: "ensureSpotCheckPointNum",
          showOverFlowTooltip: true,
        },
        {
          label: "实际抽查网点数量",
          prop: "actualSpotCheckPointNum",
          showOverFlowTooltip: true,
        },
        {
          label: "现金使用环境评价得分",
          prop: "cashUseEnvScore",
          showOverFlowTooltip: true,
        },

        { label: "操作", slot: true },
      ],
      tableData: [
        // {
        //   spotCheckTime: ["2024-10-20", "2024-11-20"],
        //   spotCheckPersonNum: "5",
        //   spotCheckStatus: "1",
        //   ensureSpotCheckStoreNum: "50",
        //   actualSpotCheckStoreNum: "10",
        //   ensureSpotCheckPointNum: "50",
        //   actualSpotCheckPointNum: "8",
        //   cashUseEnvScore: "80",
        // },
      ],
    };
  },
  computed: {
    ...mapState("accountBook", ["cashUseEnvCheckList"]),
  },
  mounted() {
    this.fetchList();
  },
  methods: {
    ...mapMutations("accountBook", [
      "updateCashUseEnvCheckList",
      "updateCashEditStatus",
      "updateNowCashUseCheckInfo",
    ]),
    fetchList() {
      this.tableData = [...this.cashUseEnvCheckList];
      this.total = this.tableData.length;
    },
    addCashUseEnvCheck() {
      this.updateCashEditStatus("add");
      this.updateNowCashUseCheckInfo({});
      this.$router.push({ name: "AddUserCash" });
    },
    edit(row) {
      this.updateCashEditStatus("edit");
      this.updateNowCashUseCheckInfo(row);
      this.$router.push({ name: "AddUserCash" });
    },
    delete(row) {
      let arr = [...this.cashUseEnvCheckList];
      this.updateCashUseEnvCheckList(arr.filter((i) => i.id != row.id));
      this.fetchList();
    },
    spotCheckRecord(row) {
      this.updateNowCashUseCheckInfo(row);
      this.$router.push({ name: "CheckSpot" });
    },
    evaluationDetail(row) {
      this.updateNowCashUseCheckInfo(row);
      this.$router.push({ name: "UseCashEnvEvaDetail" });
    },
  },
};
</script>
    
<style lang="scss" scoped></style>