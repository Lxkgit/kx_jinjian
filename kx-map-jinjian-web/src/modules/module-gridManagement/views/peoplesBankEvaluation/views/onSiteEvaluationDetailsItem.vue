<template>
  <PageContent>
    <section class="onSiteEvaluationDetails-title">
      <p>
        <span class="onSiteEvaluationDetails-title-main"> 2024年现场评价 </span>
        <span class="onSiteEvaluationDetails-title-subl">
          评价对象综合得分
        </span>
        <span class="onSiteEvaluationDetails-title-subr">89分</span>
      </p>
      <el-button @click="$router.back()">返回</el-button>
    </section>

    <section class="onSiteEvaluationDetails-search">
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

    <section class="onSiteEvaluationDetails-content">
      <p class="onSiteEvaluationDetails-content-title">评价详情</p>
      <i-table
        ref="table"
        :data="tableData"
        align="center"
        :stripe="false"
        :allColumns="columns"
        :showPagination="false"
        :spanMethod="spanMethod"
      >
      </i-table>
      <section class="onSiteEvaluationDetails-content-footer">
        <el-button type="primary">确 定</el-button>
        <el-button>取 消</el-button>
      </section>
    </section>
  </PageContent>
</template>
<script>
import tableTopbar from "../components/tableTopbar.vue";
import { setRowSpan } from "../cell/rowSpan.js";
export default {
  name: "onSiteEvaluationDetails",
  components: { tableTopbar },
  data() {
    return {
      searchColumns: [
        {
          label: "评价机构",
          key: "name",
          type: "select",
          options: [],
        },
      ],
      tableData: [
        {
          a: "整治拒收现金宣传情况 (25分)",
          b: "宣传触达率",
          c: "宣传触达率=知晓宣传信息的商户/抽查的商户总数",
          d: "96%至100% (含) 的, 得25分; 92%至96% (含) 的, 得24分; 88%至92% (含) 的, 得23分。以此类推。",
        },
        {
          a: "收取现金和找零情况 (25分)",
          b: "零钞备付率",
          c: "零钞备付率=备付零钞的商户数量/抽查的商户总数",
          d: "96%至100% (含) 的, 得25分; 92%至96% (含) 的, 得24分; 88%至92% (含) 的, 得23分。以此类推。",
        },
        {
          a: "网格化管理覆盖情况 (25分)",
          b: "网格化管理覆盖率",
          c: "网格化管理覆盖率=责任网点到访的商户数量/抽查的商户总数",
          d: "96%至100% (含) 的, 得25分; 92%至96% (含) 的, 得24分; 88%至92% (含) 的, 得23分。以此类推。",
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
          d: "对外支付现金中残缺污损人民币占1%-5% (含) 扣3分; 5%以上扣6分。",
        },
        {
          a: "金融机构零钞服务质量 (25分)",
          b: "网点零钞现金服务质量",
          c: "客户未预约无法进行小面额人民币取现、兑换。",
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
        {
          a: "整治拒收现金宣传情况 (25分)",
          b: "宣传触达率",
          c: "宣传触达率=知晓宣传信息的商户/抽查的商户总数",
          d: "84%至88% (含) 的, 得22分",
        }, // 基于“以此类推”扩展的额外评分范围，保持a/b/c相同
      ],
      columns: [
        {
          label: "评价项目",
          prop: "a",
          showOverFlowTooltip: true,
        },
        {
          label: "评价内容",
          prop: "b",
          showOverFlowTooltip: true,
        },
        {
          label: "评价标准",
          prop: "c",
          showOverFlowTooltip: true,
        },
        {
          label: "得分标准",
          prop: "d",
          showOverFlowTooltip: true,
        },
      ],
      spanHash: {},
    };
  },
  methods: {
    clickHandle(type, item) {
      switch (type) {
        case "add":
          break;
        case "details":
          break;
        case "edit":
          break;
        case "onSite":
          break;
        case "deleta":
          break;
        default:
          break;
      }
    },
    newonSiteEvalua(FormData) {},
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
@mixin leftBar($width: 2px, $height: 14px) {
  font-size: 14px;
  color: #323548;
  position: relative;
  text-indent: 10px;
  &::after {
    position: absolute;
    content: "";
    width: 2px;
    height: 14px;
    border-radius: 1px;
    background: #5e81f4;
    left: 0;
    top: 50%;
    transform: translateY(-50%);
    color: rgba($color: #5b93ff, $alpha: 0.1);
  }
}
.onSiteEvaluationDetails {
  &-title {
    padding: 16px 16px 16px 48px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: url("../assets/address.svg") no-repeat 16px center/24px 24px,
      #fff;
    margin-bottom: 12px;
    p {
      flex-shrink: 1;
      min-width: 0;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    &-main {
      font-size: 20px;
      line-height: 24px;
      color: #323548;
      margin-right: 20px;
    }
    &-subl {
      font-weight: 400;
      font-size: 16px;
      color: #898eac;
    }
    &-subr {
      font-weight: 400;
      font-size: 16px;
      color: #5e81f4;
    }
  }
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
    &-title {
      margin-bottom: 24px;
      @include leftBar();
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
