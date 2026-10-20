<template>
  <PageContent>
    <div class="top">
      <!-- 机构标题 -->
      <!-- <orgTitleSelect title="人民银行浙江省分行-2025年">
        <template #right>
          <el-button type="primary" @click="showDetail = true"
            >条件切换</el-button
          >
        </template>
      </orgTitleSelect> -->
      <!-- 数据统计 -->
      <div class="top-data">
        <tableTitle title="数据统计" class="top-title"></tableTitle>
        <statisticsItem
          height="92"
          :gridColumns="5"
          :imgList="imgList"
          :dataList="dataList"
          gridRows="1fr"
        ></statisticsItem>
        <!-- <pieChartList style="margin-top: 12px" gridColumns="6"></pieChartList> -->
      </div>
    </div>

    <!-- 过滤器 -->
    <div class="filterBar-box">
      <i-filter-bar
        ref="filterBar"
        slot="filter"
        class="filterBar"
        :columns="searchColumns"
        :gridColumns="3"
      >
      </i-filter-bar>
      <div>
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </div>
    </div>

    <!-- 列表 -->

    <div class="bottom">
      <div class="export">
        <el-button type="primary" @click="exportEvent">导出报表</el-button>
      </div>

      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        stripe
        border
        :allColumns="columns"
        :total="10"
        :pageNum="formInline.pageNum"
        :maxHeight="maxHeight"
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
    </div>
    <filterDetail
      v-model="showDetail"
      @handleConfirm="handleConfirm"
    ></filterDetail>
  </PageContent>
</template>

<script>
import filterDetail from "../../merStatisticsManagement/components/filterDetail.vue";
import pieChartList from "@/modules/module-gridManagement/components/pieChartList";
import pageMixin from "@/mixins/pageMixin";
import statisticsItem from "@/modules/module-gridManagement/components/statisticsItem.vue";
import orgTitleSelect from "@/modules/module-gridManagement/components/orgTitleSelect.vue";
const iconList = [
  {
    name: "线索报送",
    img: require("@/modules/module-gridManagement/assets/images/bankBranch1.png"),
    key: "key1",
  },
  {
    name: "非法集资",
    img: require("@/modules/module-gridManagement/assets/images/bankBranch2.png"),
    key: "key2",
  },
  {
    name: "非法放贷",
    img: require("@/modules/module-gridManagement/assets/images/bankBranch3.png"),
    key: "key3",
  },
  {
    name: "不法中介",
    img: require("@/modules/module-gridManagement/assets/images/bankBranch4.png"),
    key: "key4",
  },
  {
    name: "审核通过",
    img: require("@/modules/module-gridManagement/assets/images/bankBranch5.png"),
    key: "key5",
  },
  {
    name: "审核通过率",
    img: require("@/modules/module-gridManagement/assets/images/bankBranch3.png"),

    key: "key6",
  },
];
const dataInfo = {
  key1: "4721",
  key2: "1235",
  key3: "1098",
  key4: "2388",
  key5: "3612",
  key6: "76.51%",
};
export default {
  mixins: [pageMixin],
  components: {
    pieChartList,
    statisticsItem,
    orgTitleSelect,
    filterDetail,
  },
  data() {
    return {
      imgList: [],
      dataList: {},
      searchColumns: [
        {
          label: "数据维度",
          key: "servePointName",
          type: "select",
          options: [
            {
              label: "月度",
              value: "1",
            },
            {
              label: "季度",
              value: "2",
            },
          ],
        },
        {
          label: "统计时间",
          key: "clueSubmitTime",
          type: "datetimerange",
          clearable: true,
          rangeSeparator: "至",
          startPlaceholder: "开始日期",
          endPlaceholder: "结束日期",
          format: "yyyy-MM-dd HH:mm:ss",
          valueFormat: "yyyy-MM-dd HH:mm:ss",
        },
        {
          label: "生成时间",
          key: "servePointName",
          type: "datetimerange",
          clearable: true,
          rangeSeparator: "至",
          startPlaceholder: "开始日期",
          endPlaceholder: "结束日期",
          format: "yyyy-MM-dd HH:mm:ss",
          valueFormat: "yyyy-MM-dd HH:mm:ss",
        },
      ],
      columns: [
        {
          label: "数据维度",
          prop: "dataDimension",
          showOverFlowTooltip: true,
          width: 120,
        },
        {
          label: "统计时间",
          prop: "statTime",
          showOverFlowTooltip: true,
          width: 150,
        },
        {
          label: "线索报送数量",
          prop: "clueReportCount",
          showOverFlowTooltip: true,
          width: 180,
          sortable: true,
        },
        {
          label: "非法集资（数量）",
          prop: "illegalFundCount",
          showOverFlowTooltip: true,
          width: 200,
          sortable: true,
        },
        {
          label: "非法放贷（数量）",
          prop: "illegalLoanCount",
          showOverFlowTooltip: true,
          width: 200,
          sortable: true,
        },
        {
          label: "不法中介（数量）",
          prop: "illegalAgentCount",
          showOverFlowTooltip: true,
          width: 200,
          sortable: true,
        },
        {
          label: "审核通过数",
          prop: "auditPassCount",
          showOverFlowTooltip: true,
          width: 180,
          sortable: true,
        },
        {
          label: "审核通过率",
          prop: "auditPassRate",
          showOverFlowTooltip: true,
          width: 180,
          sortable: true,
        },
        {
          label: "数据生成时间",
          prop: "dataCreateTime",
          showOverFlowTooltip: true,
          width: 220,
        },
      ],
      tableBtn: [
        {
          cname: "明细",
          ename: "viewHandle",
          permi: "gridManagement:systemManage:view",
        },
      ],
      tableData: [
        {
          dataDimension: "月度",
          statTime: "2026-02",
          clueReportCount: 146,
          illegalFundCount: 39,
          illegalLoanCount: 51,
          illegalAgentCount: 56,
          auditPassCount: 109,
          auditPassRate: "74.66%",
          dataCreateTime: "2026-03-02 23:59:59",
        },
        {
          dataDimension: "月度",
          statTime: "2026-01",
          clueReportCount: 172,
          illegalFundCount: 51,
          illegalLoanCount: 63,
          illegalAgentCount: 58,
          auditPassCount: 134,
          auditPassRate: "77.91%",
          dataCreateTime: "2026-02-03 23:59:59",
        },
        {
          dataDimension: "月度",
          statTime: "2025-11",
          clueReportCount: 224,
          illegalFundCount: 53,
          illegalLoanCount: 78,
          illegalAgentCount: 93,
          auditPassCount: 188,
          auditPassRate: "83.93%",
          dataCreateTime: "2025-12-05 23:59:59",
        },
        {
          dataDimension: "月度",
          statTime: "2025-10",
          clueReportCount: 250,
          illegalFundCount: 67,
          illegalLoanCount: 92,
          illegalAgentCount: 91,
          auditPassCount: 180,
          auditPassRate: "72.00%",
          dataCreateTime: "2025-11-01 23:59:59",
        },
        {
          dataDimension: "月度",
          statTime: "2025-08",
          clueReportCount: 302,
          illegalFundCount: 99,
          illegalLoanCount: 105,
          illegalAgentCount: 98,
          auditPassCount: 235,
          auditPassRate: "77.81%",
          dataCreateTime: "2025-09-03 23:59:59",
        },
        {
          dataDimension: "月度",
          statTime: "2025-07",
          clueReportCount: 328,
          illegalFundCount: 78,
          illegalLoanCount: 121,
          illegalAgentCount: 129,
          auditPassCount: 265,
          auditPassRate: "80.79%",
          dataCreateTime: "2025-08-04 23:59:59",
        },
        {
          dataDimension: "月度",
          statTime: "2025-05",
          clueReportCount: 173,
          illegalFundCount: 51,
          illegalLoanCount: 60,
          illegalAgentCount: 62,
          auditPassCount: 124,
          auditPassRate: "71.68%",
          dataCreateTime: "2025-06-01 23:59:59",
        },
        {
          dataDimension: "月度",
          statTime: "2025-04",
          clueReportCount: 199,
          illegalFundCount: 65,
          illegalLoanCount: 73,
          illegalAgentCount: 61,
          auditPassCount: 149,
          auditPassRate: "74.87%",
          dataCreateTime: "2025-05-02 23:59:59",
        },
        {
          dataDimension: "月度",
          statTime: "2025-02",
          clueReportCount: 251,
          illegalFundCount: 67,
          illegalLoanCount: 87,
          illegalAgentCount: 97,
          auditPassCount: 203,
          auditPassRate: "80.88%",
          dataCreateTime: "2025-03-04 23:59:59",
        },
        {
          dataDimension: "月度",
          statTime: "2025-01",
          clueReportCount: 277,
          illegalFundCount: 83,
          illegalLoanCount: 102,
          illegalAgentCount: 92,
          auditPassCount: 232,
          auditPassRate: "83.75%",
          dataCreateTime: "2025-02-05 23:59:59",
        },
      ],
      maxHeight: 600,
      showDetail: false,
    };
  },
  computed: {},
  mounted() {
    this.imgList = iconList;
    this.dataList = dataInfo;
  },
  methods: {
    exportEvent() {
      this.$message.success("导出成功！");
    },
    viewHandle() {
      this.$router.push({
        name: "PerformanceManage",
      });
    },
    handleConfirm() {},
  },
};
</script>

    <style scoped lang="scss">
.top {
  margin-bottom: 13px;
  &-data {
    margin-top: 12px;
    padding: 12px 16px;
    background: #fff;
  }
  .top-title {
    position: relative;
    margin-bottom: 24px;
  }
  .top-title ::after {
    content: "";
    position: absolute;
    bottom: -8px;
    left: -16px;
    width: calc(100% + 32px);
    height: 1px;
    background: #e7eaf6;
  }
}
.filterBar-box {
  margin-bottom: 12px;
  background: #fff;
  padding: 16px;
  display: flex;
  justify-content: space-between;
  .filterBar {
    width: 65%;
  }
}
.table-title {
  padding: 16px;
  height: 60px;
}
.bottom {
  padding: 0 16px;
  background: #fff;
}
.export {
  padding: 10px 0;
}
</style>
