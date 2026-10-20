<template>
  <PageContent>
    <section class="onSiteTemplateListDetails-search">
      <i-filter-bar
        :columns="searchColumns"
        ref="filterBar"
        slot="filter"
        :gridColumns="4"
        :buttonSpan="3"
        label-width="80px"
      >
        <!-- 搜索与重置按钮 -->
        <el-button type="primary">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
    </section>

    <section class="onSiteTemplateListDetails-content">
      <tableTopbar
        leftTitle="新增详情"
        buttonText="新增规则"
        @click="clickHandle('list')"
        style="margin-bottom: 16px"
      />
      <i-table
        ref="table"
        :data="tableData"
        align="center"
        :stripe="false"
        :allColumns="columns"
        :spanMethod="spanMethod"
        :showPagination="false"
      >
        <template #e="scope">
          <i-table-btns
            :btn-lists="tableBtn"
            @click="(type) => clickHandle(type, scope.row)"
            :data="scope.row"
          >
          </i-table-btns>
        </template>
      </i-table>
      <section class="onSiteTemplateListDetails-content-footer">
        <el-button type="primary">确 定</el-button>
        <el-button>取 消</el-button>
      </section>
    </section>
    <addRulesDialog
      :dialogVisible.sync="showNewRules"
      @submit="NewRulessubmit"
    />
    <addRulesDetailsDialog :dialogVisible.sync="showNewRulesDetails" />
  </PageContent>
</template>
<script>
import tableTopbar from "../components/tableTopbar.vue";
import addRulesDialog from "../components/addRulesDialog.vue";
import addRulesDetailsDialog from "../components/addRulesDetailsDialog.vue";
import { setRowSpan } from "../cell/rowSpan.js";
export default {
  name: "onSiteTemplateListDetails",
  components: { tableTopbar, addRulesDialog, addRulesDetailsDialog },
  data() {
    return {
      searchColumns: [
        {
          label: "模板名称",
          key: "name",
          type: "text",
        },
      ],
      tableData: [
        {
          a: "整治拒收现金宣传情况 (25分)",
          b: "宣传触达率",
          c: "宣传触达率=知晓宣传信息的商户/抽查的商户总数。",
          d: "96%至100% (含)的, 得25分; 92%至96% (含)的, 得24分; 88%至92% (含)的, 得23分。以此类推。",
        },
        {
          a: "收取现金和找零情况 (25分)",
          b: "零钞备付率",
          c: "零钞备付率=备付零钞的商户数量/抽查的商户总数。",
          d: "96%至100% (含)的, 得25分; 92%至96% (含)的, 得24分; 88%至92% (含)的, 得23分。以此类推。",
        },
        {
          a: "网格化管理覆盖情况 (25分)",
          b: "网格化管理覆盖率",
          c: "网格化管理覆盖率=责任网点到访的商户数量/抽查的商户总数。",
          d: "96%至100% (含)的, 得25分; 92%至96% (含)的, 得24分; 88%至92% (含)的, 得23分。以此类推。",
        },
        {
          a: "金融机构零钞服务质量 (25分)",
          b: "网点零钞现金服务质量",
          c: "“零钱包”兑换排队时间超过10分钟。",
          d: "扣3分。",
        },
        {
          a: "金融机构零钞服务质量 (25分)",
          b: "网点零钞现金服务质量",
          c: "现场不能兑换较小数量零钞 (各分支机构确定具体数量), 且不主动说明预约方式的 (电话预约、现场预约、APP/小程序预约)。",
          d: "扣3分。",
        },
        {
          a: "金融机构零钞服务质量 (25分)",
          b: "网点零钞现金服务质量",
          c: "对外支付现金中残缺污损人民币。",
          d: "对外支付现金中残缺污损人民币占1%-5% (含)扣3分; 5%以上扣6分。",
        },
        {
          a: "金融机构零钞服务质量 (25分)",
          b: "网点零钞现金服务质量",
          c: "客户未预约无法进行小额人民币取现、兑换。",
          d: "扣3分",
        },
        {
          a: "金融机构零钞服务质量 (25分)",
          b: "网点零钞现金服务质量",
          c: "外拓零钞服务需要网点员工垫款。",
          d: "扣3分",
        },
        {
          a: "金融机构零钞服务质量 (25分)",
          b: "网点零钞现金服务质量",
          c: "未为老年客户提供便利化现金服务。",
          d: "扣3分",
        },
      ],
      columns: [
        {
          label: "地市分行名称",
          prop: "a",
          showOverFlowTooltip: true,
        },
        {
          label: "服务宣传次数",
          prop: "b",
          showOverFlowTooltip: true,
        },
        {
          label: "服务宣传覆盖网格数",
          prop: "c",
          showOverFlowTooltip: true,
        },
        { label: "服务宣传受众人数", prop: "d" },
        { label: "操作", slot: true, prop: "e" },
      ],
      tableBtn: [
        {
          cname: "添加评价标准",
          ename: "details",
        },
        {
          cname: "删除",
          ename: "delete",
          danger: true,
        },
      ],
      spanHash: {},
      showNewRules: false,
      showNewRulesDetails: false,
    };
  },
  methods: {
    clickHandle(type, item) {
      switch (type) {
        case "list":
          this.showNewRules = true;
          break;
        case "details":
          this.showNewRulesDetails = true;
          break;
        case "deleta":
          break;
        default:
          break;
      }
    },
    NewRulessubmit(FormData) {},
    resetHandle() {
      // Reset search fields
      console.log("Reset search fields");
    },
    spanMethod({ row, column, rowIndex }) {
      return (
        this.spanHash[column.property] &&
        this.spanHash[column.property][rowIndex]
      );
    },
  },
  mounted() {
    //模仿请求接收数据
    this.spanHash = setRowSpan(this.tableData, ["a", "b", "c", "d"]);
    console.log(this.spanHash);
  },
};
</script>
<style lang="scss" scoped>
.onSiteTemplateListDetails {
  &-search {
    padding: 16px;
    background: #fff;
    margin-bottom: 12px;
    ::v-deep .filter-button-wrapper {
      text-align: right;
    }
  }
  &-content {
    flex: 1;
    display: flex;
    flex-flow: column;
    padding: 24px 16px;
    background: #fff;
    ::v-deep .el-table .el-table__body-wrapper td.el-table__cell {
      border: 0.5px solid #ebeef5;
    }
    &-footer {
      margin-top: 20px;
      text-align: center;
    }
  }
}
/* 深度选择器覆盖 */
::v-deep .el-table--enable-row-hover .el-table__body tr:hover > td {
  cursor: default;
  background-color: transparent !important;
}
</style>
