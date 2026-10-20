<template>
  <div>
    <SplitPageContent :hasLeftbg="false">
      <template #operate>
        <div class="tree-wrapper">
          <ul>
            <h2>银行机构</h2>
            <li
              v-for="(item, index) in bankList"
              :key="item.bankName"
              :class="[index === activeBankIndex ? 'active' : '']"
              @click="clickTree(item, index)"
            >
              <span :title="item.bankName"
                >{{ item.bankName }}({{ item.num }})</span
              >
            </li>
          </ul>
        </div>
      </template>
      <!-- 过滤器 -->
      <i-filter-bar
        ref="filterBar"
        slot="filter"
        :columns="searchColumns"
        :gridColumns="4"
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
        showSummary
        :summaryMethod="summaryMethod"
        @currentChange="currentChange"
        @sizeChange="sizeChange"
        @sortChange="sortChange"
      >
        <template #cashServiceNum="{ row }">
          <el-link
            type="primary"
            :underline="false"
            @click="openCashServiceDialog(row)"
            >{{ row.cashServiceNum }}</el-link
          >
        </template>
        <template #dotDynamicNum="{ row }">
          <el-link
            type="primary"
            :underline="false"
            @click="openDotDynamicDialog(row)"
            >{{ row.dotDynamicNum }}</el-link
          >
        </template>
        <template #complaintsNum="{ row }">
          <el-link
            type="primary"
            :underline="false"
            @click="openComplaintDialog(row)"
            >{{ row.complaintsNum }}</el-link
          >
        </template>
        <template #gridManegerNum="{ row }">
          <el-link type="primary" :underline="false">{{
            row.gridManegerNum
          }}</el-link>
        </template>
        <template #gridMerchantNum="{ row }">
          <el-link type="primary" :underline="false">{{
            row.gridMerchantNum
          }}</el-link>
        </template>
        <template #action="scope">
          <i-table-btns
            :btn-lists="tableBtn"
            @click="(type) => clickHandle(type, scope.row)"
            :data="scope.row"
          >
          </i-table-btns>
        </template>
      </i-table>
    </SplitPageContent>
    <cash-service-dialog
      v-model="showCashServiceDialog"
      :defaultValue="defaultValue"
    ></cash-service-dialog>
    <complaint-dialog
      v-model="showComplaintDialog"
      :defaultValue="defaultValue"
    ></complaint-dialog>
    <dot-dynamic-dialog
      v-model="showDotDynamicDialog"
      :defaultValue="defaultValue"
    ></dot-dynamic-dialog>
  </div>
</template>

<script>
import pageMixin from "@/mixins/pageMixin";
import cashServiceDialog from "../components/cashServiceDialog.vue";
import complaintDialog from "../components/complaintDialog.vue";
import dotDynamicDialog from "../components/dotDynamicDialog.vue";
export default {
  name: "surveyRegisteList",
  mixins: [pageMixin],
  dicts: ["had_survey_need"],
  components: {
    cashServiceDialog,
    complaintDialog,
    dotDynamicDialog,
  },
  data() {
    return {
      searchColumns: [
        {
          label: "网点名称",
          key: "dotName",
          type: "text",
        },
        {
          label: "月份范围",
          key: "yearRange",
          type: "monthrange",
        },
      ],
      btnLists: [
        // {
        //   icon: "el-icon-s-promotion",
        //   cname: "导出",
        //   ename: "export",
        //   type: "info",
        //   disabled: () => this.multiSelected.length === 0,
        // },
      ],
      tableBtn: [],
      columns: [
        { label: "银行机构", prop: "bankName", showOverFlowTooltip: true },
        { label: "网点名称", prop: "networkPoint", showOverFlowTooltip: true },
        {
          label: "责任网格数量",
          prop: "cashServiceNum",
          showOverFlowTooltip: true,
          sortable: "custom",
          width: 190,
          slot: true,
        },
        {
          label: "网点信息上报",
          prop: "dotDynamicNum",
          showOverFlowTooltip: true,
          sortable: "custom",
          slot: true,
        },
        {
          label: "投诉事件信息",
          prop: "complaintsNum",
          showOverFlowTooltip: true,
          sortable: "custom",
          slot: true,
        },
        {
          label: "网格长数",
          prop: "gridManegerNum",
          showOverFlowTooltip: true,
          sortable: "custom",
          slot: true,
        },
        {
          label: "摸排商户数",
          prop: "gridMerchantNum",
          showOverFlowTooltip: true,
          sortable: "custom",
          slot: true,
        },
      ],
      tableData: [
        {
          idx: 0,
          bankName: "稠州银行",
          networkPoint: "明珠支行",
          cashServiceNum: "2",
          dotDynamicNum: "2",
          complaintsNum: "2",
          gridManegerNum: "1",
          gridMerchantNum: "8",
        },
        {
          idx: 1,
          bankName: "稠州银行",
          networkPoint: "浦江支行营业部",
          cashServiceNum: "1",
          dotDynamicNum: "1",
          complaintsNum: "3",
          gridManegerNum: "1",
          gridMerchantNum: "9",
        },
        {
          idx: 2,
          bankName: "稠州银行",
          networkPoint: "城西支行",
          cashServiceNum: "1",
          dotDynamicNum: "3",
          complaintsNum: "1",
          gridManegerNum: "1",
          gridMerchantNum: "7",
        },
        {
          idx: 3,
          bankName: "稠州银行",
          networkPoint: "江滨支行",
          cashServiceNum: "2",
          dotDynamicNum: "2",
          complaintsNum: "2",
          gridManegerNum: "2",
          gridMerchantNum: "5",
        },
        {
          idx: 4,
          bankName: "稠州银行",
          networkPoint: "金华开发支行",
          cashServiceNum: "2",
          dotDynamicNum: "1",
          complaintsNum: "2",
          gridManegerNum: "2",
          gridMerchantNum: "6",
        },
      ],
      bankList: [
        {
          bankName: "工商银行",
          num: "6",
        },
        {
          bankName: "稠州银行",
          num: "3",
        },
        {
          bankName: "中国银行",
          num: "8",
        },
        {
          bankName: "建设银行",
          num: "9",
        },
        {
          bankName: "农业银行",
          num: "4",
        },
      ],
      activeBankIndex: 0,
      bankNAme: "",
      defaultValue: {},
      showCashServiceDialog: false,
      showDotDynamicDialog: false,
      showComplaintDialog: false,
    };
  },
  computed: {},
  mounted() {
    this.fetchList();
  },
  methods: {
    fetchList() {
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          ...formInfo,
        };
        this.total = this.tableData.length;
      });
    },
    summaryMethod({ columns, data }) {
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0 || index === 1) {
          // 在第一列和第二列之间插入合计
          sums.push("合计");
        } else {
          // 对其他列进行求和
          const sum = data.reduce((prev, curr) => {
            return prev + (parseFloat(curr[column.property]) || 0);
          }, 0);
          sums.push(sum);
        }
      });
      return sums;
    },
    openCashServiceDialog(row) {
      this.defaultValue = { ...row };
      this.showCashServiceDialog = true;
    },
    openDotDynamicDialog(row) {
      this.defaultValue = row;
      this.showDotDynamicDialog = true;
    },
    openComplaintDialog(row) {
      this.defaultValue = row;
      this.showComplaintDialog = true;
    },
    sortChange() {},
    clickTree(item, index) {
      this.activeBankIndex = index;
      this.bankName = item.bankName;
      this.fetchList();
    },
  },
};
</script>

<style lang="scss" scoped>
.tree-wrapper {
  overflow: auto;
  width: 100%;
  height: calc(100vh - 180px);
  padding: 15px;
  box-shadow: 3px 0px 20px 1px #e7e6e6;
  &::-webkit-scrollbar {
    width: 3px;
  }
  &::-webkit-scrollbar-track {
    background: transparent;
  }
  &::-webkit-scrollbar-thumb {
    background: #409eff;
    border-radius: 2px;
  }
  // .tree-content {
  //   width: 100%;
  //   height: calc(100% - 46px);
  //   overflow: auto;
  //   ::v-deep .el-tree-node > .el-tree-node_children {
  //     overflow: visible;
  //   }
  // }
  ul {
    h2 {
      text-align: center;
      font-size: 20px;
      margin-bottom: 10px;
    }
    li {
      font-size: 16px;
      padding: 8px;
      color: #333;
      cursor: pointer;
      display: flex;
      justify-content: space-between;
      span:nth-of-type(1) {
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;
      }
    }
    .active {
      color: #fff;
      background: #409eff;
      border-radius: 4px;
    }
  }
}
</style>