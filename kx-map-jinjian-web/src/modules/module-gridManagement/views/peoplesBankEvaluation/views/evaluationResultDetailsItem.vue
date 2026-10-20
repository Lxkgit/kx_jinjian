<template>
  <PageContent>
    <section class="evaluationResultDetailsItem-title">
      <p>
        <span class="evaluationResultDetailsItem-title-main">
          2024年度现金服务工作非现场评价
        </span>
        <span class="evaluationResultDetailsItem-title-subl"
          >地市人行评价结果：</span
        >
        <span class="evaluationResultDetailsItem-title-subr"
          >人民银行金华市分行 89分</span
        >
      </p>
      <el-button @click="$router.back()">返回</el-button>
    </section>

    <section class="evaluationResultDetailsItem-table">
      <section class="evaluationResultDetailsItem-table-title">
        <p class="evaluationResultDetailsItem-table-title-main">
          建设银行金华分行
        </p>
        <p class="evaluationResultDetailsItem-table-title-sub">
          <span>最终得分：</span>
          <span>89分</span>
        </p>
      </section>
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        :stripe="false"
        :allColumns="columns"
        :spanMethod="spanMethod"
        :showPagination="false"
      >
      </i-table>
    </section>
  </PageContent>
</template>
<script>
import { setRowSpan } from "../cell/rowSpan.js";
export default {
  name: "evaluationResultDetailsItem",
  data() {
    return {
      tableData: [
        {
          a: "银行网点现金供应渠道建设情况 (42分)",
          b: "人工现金柜台配备率 (10分)",
          c: "人工现金柜台配备率=评价年度现金网点人工现金柜台数量-弹性人工现金柜台数量/评价年度开办现金业务的网点数",
          d: "大于100%的, 得10分; 低于100%的, 得0分",
          e: "80%",
          f: "0分",
        },
        {
          a: "银行网点现金供应渠道建设情况 (42分)",
          b: "自助现金机具配备率 (8分)",
          c: "自助现金机具配备率=评价年度自助现金机具配备数量/评价年度现金网点数",
          d: "大于100%的, 得8分; 96%至100% (含)的, 得7分; 92%至96% (含)的, 得6分; 88%至92% (含)的, 得5分（以此类推）",
          e: "79%",
          f: "1分",
        },
        {
          a: "银行网点现金供应渠道建设情况 (42分)",
          b: "小面额现金存取自助机具配备数 (3分)",
          c: "平均每个现金网点有小面额现金存取功能的自助机具配备数=评价年度有小面额现金存取功能的自助现金机具配备数量/评价年度现金网点数",
          d: "大于100%的, 得3分; 96%至100% (含)的, 得2分; 92%至96% (含)的, 得1分; 小于88%的不得分",
          e: "98%",
          f: "2分",
        },
        {
          a: "银行网点现金供应渠道建设情况 (42分)",
          b: "小面额自助兑换机(整换零)配备数 (3分)",
          c: "平均每个现金网点有小面额自助兑换机(整换零)配备数=评价年度小面额自助兑换机(整换零)配备数/评价年度现金网点数",
          d: "大于100%的, 得3分; 96%至100% (含)的, 得2分; 92%至96% (含)的, 得1分; 小于88%的不得分",
          e: "93%",
          f: "3分",
        },
        {
          a: "银行网点现金供应渠道建设情况 (42分)",
          b: "硬币兑换机(零换整)配备率 (3分)",
          c: "平均每个现金网点有硬币兑换机(零换整)配备数=评价年度硬币兑换机(零换整)配备数/评价年度现金网点数",
          d: "大于100%的, 得3分; 96%至100% (含)的, 得2分; 92%至96% (含)的, 得1分; 小于88%的不得分",
          e: "94%",
          f: "2分",
        },
        {
          a: "银行网点现金供应渠道建设情况 (42分)",
          b: "小面额人民币(含零钱包)兑换率 (9分)",
          c: "平均每个现金网点小面额人民币(含零钱包)兑换次数=评价年度小面额人民币(含零钱包)兑换次数/评价年度现金网点次数",
          d: "大于100%的, 得9分; 96%至100% (含)的, 得8分; 92%至96% (含)的, 得7分; 88%至92% (含)的, 得6分（以此类推）",
          e: "80%",
          f: "1分",
        },
        {
          a: "银行网点现金供应渠道建设情况 (42分)",
          b: "硬币兑换机配备增长率 (3分)",
          c: "平均每个现金网点有硬币兑换机(零换整)配备数=评价年度硬币兑换机(零换整)配备数/评价年度现金网点数",
          d: "大于100%的, 得3分; 96%至100% (含)的, 得2分; 92%至96% (含)的, 得1分; 小于88%的不得分",
          e: "94%",
          f: "2分",
        },
      ],
      columns: [
        {
          label: "评价内容",
          prop: "a",
          showOverFlowTooltip: true,
        },
        {
          label: "评价项目",
          prop: "b",
          showOverFlowTooltip: true,
        },
        {
          label: "评价标准",
          prop: "c",
          showOverFlowTooltip: true,
        },
        { label: "得分标准", showOverFlowTooltip: true, prop: "d" },
        { label: "计算值", showOverFlowTooltip: true, prop: "e" },
        { label: "计算值", showOverFlowTooltip: true, prop: "f" },
      ],
      spanHash: {},
    };
  },
  mounted() {
    //模仿请求接收数据
    this.spanHash = setRowSpan(this.tableData, ["a", "b", "c", "d", "e", "f"]);
    console.log(this.spanHash);
  },
  methods: {
    spanMethod({ row, column, rowIndex }) {
      return (
        this.spanHash[column.property] &&
        this.spanHash[column.property][rowIndex]
      );
    },
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
.evaluationResultDetailsItem {
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
      white-space: nowrap; /* 必须添加 */
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
  &-table {
    padding: 24px 16px;
    background: #fff;
    &-title {
      display: flex;
      flex-flow: row nowrap;
      align-items: center;
      justify-content: flex-start;
      gap: 0 11px;
      margin-bottom: 24px;
      &-main {
        @include leftBar();
      }
      &-sub {
        height: 20px;
        line-height: 20px;
        font-size: 14px;
        text-indent: 24px;
        background: url("../assets/supdetails.png") no-repeat left center/20px
          20px;
        span:first-child {
          color: #ffc327;
        }
        span:last-child {
          color: #f45e5e;
        }
      }
    }
  }
}
::v-deep .el-table .el-table__body-wrapper td.el-table__cell {
  border: 0.5px solid #ebeef5;
}
/* 深度选择器覆盖 */
::v-deep .el-table--enable-row-hover .el-table__body tr:hover > td {
  cursor: default;
  background-color: transparent !important;
}
</style>
