<template>
  <PageContent>
    <!-- 过滤器 -->
    <div class="filterBar-box">
      <i-filter-bar ref="filterBar" slot="filter" :columns="searchColumns" class="filterBar" :gridColumns="4">
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>

    </div>

    <div class="i-table">
      <!-- 按钮 -->
      <i-button-bar style="margin-bottom: 0" :btn-lists="btnLists" @click="clickHandle" slot="btn" ref="btnBar"
        class="button-bar">
      </i-button-bar>
      <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe class="warning-table" :allColumns="columns"
        :total="total" :pageNum="formInline.pageNum" :maxHeight="maxHeight" :pageSize="formInline.pageSize"
        @currentChange="currentChange" @sizeChange="sizeChange">
        <!-- 风险等级插槽 -->
        <template #riskLevel="scope">
          <el-tag :style="{
            color: getRiskLevelColor(scope.row.riskLevel),
            backgroundColor: getRiskLevelBgColor(scope.row.riskLevel),
            borderRadius: '5px'
          }">
            {{ scope.row.riskLevel }}
          </el-tag>
        </template>
        <!-- 当前节点插槽 -->
        <template #currentNode="scope">
          <el-tag :style="{
            color: '#689BEA',
            backgroundColor: '#E6F4FF',
            borderRadius: '5px'
          }">
            {{ myEnum[scope.row.currentNode] }}
          </el-tag>
        </template>
        <!-- 线索编号插槽 -->
        <template #clueNo="scope">
          <span style="cursor: pointer; color: #409eff; text-decoration: underline" @click="showDetailDialog = true">{{
            scope.row.clueNo }}</span>
        </template>
        <!-- 操作插槽 -->
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type) => clickHandle(type, scope.row)" :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>
    </div>

    <addEventDialog v-model="showAddDialog" @success="handlerSuccess"></addEventDialog>
    <eventDetailDialog v-model="showDetailDialog" @close="showDetailDialog = false" :defaultValue="defaultValue">
    </eventDetailDialog>
    <firstReviewDialog v-model="showFirstReviewDialog" @success="handlerSuccess"></firstReviewDialog>
    <analysisDialog v-model="showAnalysisDialog" @success="handlerSuccess"></analysisDialog>
    <transferDialog v-model="showTransferDialog" @success="handlerSuccess"></transferDialog>
    <handleDialog v-model="showHandleDialog" @success="handlerSuccess"></handleDialog>
    <superviseDialog v-model="showSuperviseDialog" @success="handlerSuccess"></superviseDialog>
    <closeReviewDialog v-model="showCloseReviewDialog" @success="handlerSuccess"></closeReviewDialog>
  </PageContent>
</template>

<script>
import pageMixin from "@/mixins/pageMixin";
import addEventDialog from "../components/addEventDialog.vue";
import eventDetailDialog from "../components/eventDetailDialog.vue";
import firstReviewDialog from "../components/firstReviewDialog.vue";
import analysisDialog from "../components/analysisDialog.vue";
import transferDialog from "../components/transferDialog.vue";
import handleDialog from "../components/handleDialog.vue";
import superviseDialog from "../components/superviseDialog.vue";
import closeReviewDialog from "../components/closeReviewDialog.vue";
export default {
  components: { addEventDialog, eventDetailDialog, firstReviewDialog, analysisDialog, transferDialog, handleDialog, superviseDialog, closeReviewDialog },
  mixins: [pageMixin],
  data() {
    return {
      defaultValue: {},
      loading: false,
      maxHeight: window.innerHeight - 350,
      showAddDialog: false,
      showDetailDialog: false,
      showFirstReviewDialog: false,
      showAnalysisDialog: false,
      showTransferDialog: false,
      showHandleDialog: false,
      showSuperviseDialog: false,
      showCloseReviewDialog: false,
      // 搜索条件
      searchColumns: [
        {
          label: "线索编号",
          key: "clueNo",
          type: "text",
          clearable: true,
          placeholder: "请输入线索编号"
        },
        {
          label: "来源机构",
          key: "clueSourceOrg",
          type: "select",
          clearable: true,
          options: [
            { label: "中国建设银行股份有限公司杭州文晖支行", value: "中国建设银行股份有限公司杭州文晖支行" },
            { label: "中国建设银行股份有限公司宁波江北支行", value: "中国建设银行股份有限公司宁波江北支行" },
            { label: "中国建设银行股份有限公司湖州吴兴支行", value: "中国建设银行股份有限公司湖州吴兴支行" },
            { label: "中国建设银行股份有限公司温州鹿城支行", value: "中国建设银行股份有限公司温州鹿城支行" }
          ],
          placeholder: "请选择来源机构"
        },
        {
          label: "报送时间",
          key: "clueSubmitTime",
          type: "datetimerange",
          clearable: true,
          rangeSeparator: "至",
          startPlaceholder: "开始日期",
          endPlaceholder: "结束日期",
          format: "yyyy-MM-dd HH:mm:ss",
          valueFormat: "yyyy-MM-dd HH:mm:ss"
        },
        {
          label: "风险等级",
          key: "riskLevel",
          type: "select",
          clearable: true,
          options: [
            { label: "低风险", value: "低风险" },
            { label: "中风险", value: "中风险" },
            { label: "高风险", value: "高风险" }
          ],
          placeholder: "请选择风险等级"
        },
        {
          label: "分拨单位",
          key: "transferOrg",
          type: "text",
          clearable: true,
          placeholder: "请输入分拨单位"
        }
      ],
      // 顶部按钮
      btnLists: [
        {
          cname: "新增线索",
          ename: "addHandle",
          type: "primary"
        },
      ],
      // 列表头
      columns: [
        { label: "线索编号", prop: "clueNo", slot: true, fixed: "left", width: 180 },
        { label: "来源机构", prop: "clueSourceOrg", width: 180 },
        { label: "报送时间", prop: "clueSubmitTime", width: 180 },
        { label: "线索基本信息", prop: "clueBasicInfo", width: 200 },
        { label: "风险等级", prop: "riskLevel", slot: true, width: 120 },
        { label: "分拨单位", prop: "transferOrg", width: 180 },
        { label: "分拨时间", prop: "transferTime", width: 180 },
        { label: "当前节点", prop: "currentNode", slot: true, width: 150 },
        { label: "操作", slot: true, fixed: "right", width: 200 }
      ],
      tableData: [],
      // 行操作
      tableBtn: [
        { cname: "详情", ename: "detailHandle", icon: "el-icon-view" },
        { cname: "初审", ename: "firstReview", icon: "el-icon-edit", isShow: ({ currentNode }) => { return currentNode === 1 } },
        { cname: "研判", ename: "analysis", icon: "el-icon-data-analysis", isShow: ({ currentNode }) => { return currentNode === 2 } },
        { cname: "移送", ename: "transfer", icon: "el-icon-right", isShow: ({ currentNode }) => { return currentNode === 3 } },
        { cname: "处置", ename: "handle", icon: "el-icon-setting", isShow: ({ currentNode }) => { return currentNode === 4 } },
        { cname: "督办", ename: "supervise", icon: "el-icon-warning", isShow: ({ currentNode }) => { return currentNode === 5 } },
        { cname: "销号审核", ename: "closeReview", icon: "el-icon-check", isShow: ({ currentNode }) => { return currentNode === 6 } }
      ],
      total: 0,
      myEnum: {
        0: "线索上报",
        1: "线索初审",
        2: "线索研判",
        3: "线索移送",
        4: "联合处置",
        5: "督办反馈",
        6: "销号管理",
        7: "已办结",

      }
    };
  },
  methods: {
    // 获取列表数据
    async fetchList() {
      try {
        this.loading = true;
        // 模拟数据
        this.tableData = [
          {
            clueNo: "XS-20260301-001",
            clueSourceOrg: "朝阳区金融办",
            clueSubmitTime: "2026-03-01 09:30:00",
            clueBasicInfo: "涉嫌以养老服务名义非法诈骗",
            riskLevel: "高",
            transferOrg: "朝阳区处非办",
            transferTime: "2026-03-01 10:00:00",
            currentNode: 1
          },
          {
            clueNo: "XS-20260302-002",
            clueSourceOrg: "群众举报",
            clueSubmitTime: "2026-03-02 14:30:00",
            clueBasicInfo: "在社区发放传单，宣传高",
            riskLevel: "中",
            transferOrg: "浦东新区处非办",
            transferTime: "2026-03-02 15:00:00",
            currentNode: 2
          },
          {
            clueNo: "XS-20260303-003",
            clueSourceOrg: "网格员巡查",
            clueSubmitTime: "2026-03-03 10:00:00",
            clueBasicInfo: "无证经营，疑似开展P2P",
            riskLevel: "低",
            transferOrg: "南山区金融办",
            transferTime: "2026-03-03 11:00:00",
            currentNode: 3
          },
          {
            clueNo: "XS-20260304-004",
            clueSourceOrg: "网络监测",
            clueSubmitTime: "2026-03-04 09:00:00",
            clueBasicInfo: "宣传虚拟货币交易",
            riskLevel: "高",
            transferOrg: "西湖区金融办",
            transferTime: "2026-03-04 09:30:00",
            currentNode: 4
          },
          {
            clueNo: "XS-20260305-005",
            clueSourceOrg: "信访局转办",
            clueSubmitTime: "2026-03-05 11:00:00",
            clueBasicInfo: "预收学费跑路，涉及金额诈骗",
            riskLevel: "中",
            transferOrg: "鼓楼区教育局",
            transferTime: "2026-03-05 11:30:00",
            currentNode: 5
          },
          {
            clueNo: "XS-20260306-006",
            clueSourceOrg: "农业农村局",
            clueSubmitTime: "2026-03-06 13:00:00",
            clueBasicInfo: "超范围吸收社员资金",
            riskLevel: "低",
            transferOrg: "锦江区处非办",
            transferTime: "2026-03-06 13:30:00",
            currentNode: 6
          },
          {
            clueNo: "XS-20260307-007",
            clueSourceOrg: "经侦大队",
            clueSubmitTime: "2026-03-07 15:00:00",
            clueBasicInfo: "P2P网贷存量风险",
            riskLevel: "高",
            transferOrg: "江汉区金融办",
            transferTime: "2026-03-07 15:30:00",
            currentNode: 7
          },
          {
            clueNo: "XS-20260308-008",
            clueSourceOrg: "某街道办事处",
            clueSubmitTime: "2026-03-08 16:00:00",
            clueBasicInfo: "疑似非法集资",
            riskLevel: "中",
            transferOrg: "区处非办",
            transferTime: "2026-03-08 16:30:00",
            currentNode: 0
          }
        ];
        this.total = this.tableData.length;
        this.loading = false;
      } catch (error) {
        console.error("获取列表失败:", error);
        this.loading = false;
      }
    },
    // 获取风险等级颜色
    getRiskLevelColor(level) {
      const colorMap = {
        高: "#D32932",
        中: "#DA7F34",
        低: "#3DA018",
        High: "#D32932",
        Medium: "#DA7F34",
        Low: "#3DA018"
      };
      return colorMap[level] || "#333";
    },
    // 获取风险等级背景颜色
    getRiskLevelBgColor(level) {
      const bgColorMap = {
        高: "#FFF2F1",
        中: "#FFF7E6",
        低: "#F6FFEE",
        High: "#FFF2F1",
        Medium: "#FFF7E6",
        Low: "#F6FFEE"
      };
      return bgColorMap[level] || "#f5f7fa";
    },
    // 搜索方法
    searchHandle() {
      this.formInline.pageNum = 1;
      this.fetchList();
    },
    resetHandle() {
      this.$refs.filterBar.reset();
      this.formInline.pageNum = 1;
      this.fetchList();
    },
    currentChange(pageNum) {
      this.formInline.pageNum = pageNum;
      this.fetchList();
    },
    sizeChange(pageSize) {
      this.formInline.pageSize = pageSize;
      this.formInline.pageNum = 1;
      this.fetchList();
    },
    exportHandle() {
      this.$message.success("已导出数据！");
    },
    clickHandle(type, row) {
      switch (type) {
        case "addHandle":
          this.showAddDialog = true;
          break;
        case "detailHandle":
          this.defaultValue = row
          this.showDetailDialog = true;
          break;
        case "firstReview":
          this.showFirstReviewDialog = true;
          break;
        case "analysis":
          this.showAnalysisDialog = true;
          break;
        case "transfer":
          this.showTransferDialog = true;
          break;
        case "handle":
          this.showHandleDialog = true;
          break;
        case "supervise":
          this.showSuperviseDialog = true;
          break;
        case "closeReview":
          this.showCloseReviewDialog = true;
          break;
        case "exportHandle":
          this.exportHandle();
          break;
      }
    },
    handlerSuccess() {
      this.formInline.pageNum = 1;
      this.fetchList();
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.fetchList();
    });
  }
};
</script>

<style scoped lang="scss">
.button-bar {
  padding: 20px 0;
}

.filterBar-box {
  margin-bottom: 12px;
  background: #fff;
  padding: 16px;
  padding-bottom: 0;
  padding: 15px;
  display: flex;
  justify-content: space-between;

  .filterBar {
    width: 100%;
  }
}

.table-title {
  padding: 16px;
  height: 68px;
}

.i-table {
  height: calc(100vh - 64px - 56px - 68px - 10px);
  background: #fff;
  padding: 0 16px;
  padding-bottom: 10px;
}

.warning-table {
  ::v-deep .el-table th.el-table__cell {
    height: 54px;
    padding: 0;
    background: #fafafa !important;
    color: #303133;
    font-weight: 600;
  }

  ::v-deep .el-table td.el-table__cell {
    height: 64px;
    padding: 0;
    color: #303133;
  }

  ::v-deep .el-table .cell {
    line-height: 20px;
  }

  ::v-deep .el-table__fixed {
    box-shadow: 4px 0 8px rgba(0, 0, 0, 0.04);
  }

  ::v-deep .el-table__fixed-right {
    box-shadow: -4px 0 8px rgba(0, 0, 0, 0.04);
  }
}
</style>
