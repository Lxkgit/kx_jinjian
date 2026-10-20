<template>
  <PageContent>
    <section class="evaluationRules-title">
      <span>2024年度现金服务工作非现场评价</span>
      <el-button @click="$router.back()">返回</el-button>
    </section>
    <section class="evaluationRules-details">
      <header>地市分行评分中银行机构类型分值占比</header>
      <ul class="evaluationRules-details-body">
        <li
          class="evaluationRules-details-body-item"
          v-for="(item, index) in details"
          :key="item.label"
        >
          <img :src="getSrc(item.src)" alt="" />
          <span class="evaluationRules-details-body-item-label">{{
            item.label
          }}</span>
          <span class="evaluationRules-details-body-item-value">{{
            item.value
          }}</span>
        </li>
      </ul>
    </section>

    <section class="evaluationRules-table">
      <tableTopbar
        leftTitle="银行机构评分细则"
        buttonText="导出"
        elButtonType="default"
        @click="exportTablecell"
        style="margin-bottom: 12px"
      />
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
import tableTopbar from "../components/tableTopbar.vue";
import { setRowSpan } from "../cell/rowSpan.js";
export default {
  name: "evaluationRules",
  components: { tableTopbar },
  data() {
    return {
      details: [
        {
          label: "国有商业银行",
          value: "20%",
          src: "bank_type_1.png",
        },
        {
          label: "股份制商业银行",
          value: "10%",
          src: "bank_type_2.png",
          shadow: "",
        },
        {
          label: "中国邮政储蓄银行",
          value: "10%",
          src: "bank_type_3.png",
          shadow: "",
        },
        {
          label: "城市商业银行",
          value: "20%",
          src: "bank_type_4.png",
          shadow: "",
        },
        {
          label: "农村商业银行",
          value: "20%",
          src: "bank_type_5.png",
          shadow: "",
        },
        { label: "村镇银行", value: "10%", src: "bank_type_6.png", shadow: "" },
        { label: "外资银行", value: "10%", src: "bank_type_7.png", shadow: "" },
      ],
      tableData: [
        {
          a: "网点现金供应渠道设情况（42分）",
          b: "人工现金柜台配备率（10分）",
          c: "人工现金柜台配备率=评价年度现金网点人工现金柜台数量-弹性人工现金柜台数量/评价年度开办现金业务的网点数",
          d: "大于100%的，得10分；低于100%的，得0分",
        },
        {
          a: "网点现金供应渠道设情况（42分）",
          b: "自助现金机具配备率（8分）",
          c: "自助现金机具配备率=评价年度自助现金机具配备数量/评价年度现金网点数",
          d: "大于100%的，得8分；96%至100%（含）的，得7分；92%至96%（含）的，得6分；88%至92%（含）的，得5分。以此类推",
        },
        {
          a: "网点现金供应渠道设情况（42分）",
          b: "平均每个现金网点有小面额现金存取功能的自助机具配备率（3分）",
          c: "平均每个现金网点有小面额现金存取功能的自助机具配备数=评价年度有小面额现金存取功能的自助现金机具配备数量/评价年度现金网点数。评价比率=平均每个现金网点有小面额现金存取功能的自助机具配备数/全省同类别银行机构平均每个现金网点有小面额现金存取功能的自助机具配备数",
          d: "大于100%的，得3分；96%至100%（含）的，得2分；92%至96%（含）的，得1分；小于88%的不得分",
        },
        {
          a: "网点现金供应渠道设情况（42分）",
          b: "平均每个现金网点有小面额自助兑换机（整换零）配备率（3分）",
          c: "平均每个现金网点有小面额自助兑换机（整换零）配备数=评价年度小面额自助兑换机（整换零）配备数/评价年度现金网点数。评价比率=平均每个现金网点有小面额自助兑换机（整换零）配备数/全省同类别银行机构平均每个现金网点数",
          d: "大于100%的，得3分；96%至100%（含）的，得2分；92%至96%（含）的，得1分；小于88%的不得分",
        },
        {
          a: "网点现金供应渠道设情况（42分）",
          b: "平均每个现金网点有硬币兑换机（零换整）配备增长率（3分）",
          c: "平均每个现金网点有硬币兑换机（零换整）配备数=评价年度硬币兑换机（零换整）配备数/评价年度现金网点数。评价比率=平均每个现金网点有硬币兑换机（零换整）配备数/全省同类别银行机构平均每个现金网点有硬币",
          d: "大于100%的，得3分；96%至100%（含）的，得2分；92%至96%（含）的，得1分；小于88%的不得分",
        },
        {
          a: "网点现金供应渠道设情况（42分）",
          b: "平均每个现金网点小面额人民币（含零钱包）兑换率（9分）",
          c: "平均每个现金网点小面额人民币（含零钱包）兑换次数=评价年度小面额人民币（含零钱包）兑换次数/评价年度现金网点率（含零钱包）兑换次数/全省同类别银行机构平均每个现金网点小面额人民币（含零钱包）兑换次数",
          d: "大于100%的，得9分；96%至100%（含）的，得8分；92%至98%（含）的，得7分；88%至92%（含）的，得6分；以此类推",
        },
        // 以下为补充行以满足10行要求
        {
          a: "网点现金供应渠道设情况（42分）",
          b: "人工现金柜台配备率（10分）",
          c: "人工现金柜台配备率=评价年度现金网点人工现金柜台数量-弹性人工现金柜台数量/评价年度开办现金业务的网点数",
          d: "大于100%的，得10分；低于100%的，得0分",
        },
        {
          a: "网点现金供应渠道设情况（42分）",
          b: "自助现金机具配备率（8分）",
          c: "自助现金机具配备率=评价年度自助现金机具配备数量/评价年度现金网点数",
          d: "大于100%的，得8分；96%至100%（含）的，得7分；92%至96%（含）的，得6分；88%至92%（含）的，得5分。以此类推",
        },
        {
          a: "网点现金供应渠道设情况（42分）",
          b: "平均每个现金网点有小面额现金存取功能的自助机具配备率（3分）",
          c: "平均每个现金网点有小面额现金存取功能的自助机具配备数=评价年度有小面额现金存取功能的自助现金机具配备数量/评价年度现金网点数",
          d: "大于100%的，得3分；96%至100%（含）的，得2分；92%至96%（含）的，得1分；小于88%的不得分",
        },
        {
          a: "网点现金供应渠道设情况（42分）",
          b: "平均每个现金网点有小面额自助兑换机（整换零）配备率（3分）",
          c: "平均每个现金网点有小面额自助兑换机（整换零）配备数=评价年度小面额自助兑换机（整换零）配备数/评价年度现金网点数",
          d: "大于100%的，得3分；96%至100%（含）的，得2分；92%至96%（含）的，得1分；小于88%的不得分",
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
      ],
      spanHash: {},
    };
  },
  methods: {
    getSrc(src) {
      return require(`../assets/${src}`);
    },
    exportTablecell() {},
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
    left: 16px;
    top: 50%;
    transform: translateY(-50%);
    color: rgba($color: #5b93ff, $alpha: 0.1);
  }
}
.evaluationRules {
  &-title {
    padding: 16px 16px 16px 48px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    background: url("../assets/address.svg") no-repeat 16px center/24px 24px,
      #fff;
    margin-bottom: 12px;
    span {
      font-size: 20px;
      line-height: 24px;
      color: #323548;
    }
  }
  &-details {
    background: #fff;
    margin-bottom: 12px;
    > header {
      padding: 16px;
      border-bottom: 1px solid #e7eaf6;
      @include leftBar();
    }

    &-body {
      padding: 24px 22px;
      display: grid;
      grid-template-columns: repeat(auto-fill, 215px);
      gap: 12px;
      &-item {
        display: grid;
        grid-template-columns: 60px 1fr;
        grid-auto-rows: max-content;
        gap: 8px 12px;
        padding: 16px;
        border-radius: 8px;
        background: #ffffff;
        border: 1px solid #ebeef5;
        img {
          width: 100%;
          grid-column: 1;
          grid-row: span 2;
        }
        &-label {
          font-size: 14px;
          line-height: 20px;
          color: #666666;
          white-space: nowrap;
        }
        &-value {
          font-size: 22px;
          line-height: 32px;
          color: #333333;
        }
      }
    }
  }
  &-table {
    padding: 24px 16px;
    background: #fff;
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
