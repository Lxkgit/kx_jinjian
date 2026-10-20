<template>
  <PageContent>
    <section class="evaluationResultList-title">
      <p>
        <span class="evaluationResultList-title-main">
          2024年度现金服务工作非现场评价
        </span>
        <span class="evaluationResultList-title-subl">地市人行评价结果：</span>
        <span class="evaluationResultList-title-subr"
          >人民银行金华市分行 89分</span
        >
      </p>
      <el-button @click="$router.back()">返回</el-button>
    </section>

    <section class="evaluationResultList-table">
      <section class="evaluationResultList-table-title">
        <p class="evaluationResultList-table-title-main">
          银行机构类型评价结果清单
        </p>
        <p class="evaluationResultList-table-title-sub">
          参与评价银行机构总数：89个
        </p>
      </section>
      <i-table
        ref="table"
        :loading="loading"
        :data="tableData"
        align="center"
        :stripe="false"
        :allColumns="columns"
        :showPagination="false"
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
    </section>
  </PageContent>
</template>
<script>
export default {
  name: "evaluationResultList",
  data() {
    return {
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
          label: "银行机构类型",
          prop: "a",
          showOverFlowTooltip: true,
        },
        {
          label: "银行机构数量",
          prop: "b",
          showOverFlowTooltip: true,
        },
        {
          label: "评价银行机构类型占比",
          prop: "c",
          showOverFlowTooltip: true,
        },
        { label: "银行机构评价汇总得分", showOverFlowTooltip: true, prop: "d" },
        { label: "银行机构评价平均分", showOverFlowTooltip: true, prop: "e" },
        { label: "最终占比得分", showOverFlowTooltip: true, prop: "f" },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "详查看银行机构情",
          ename: "details",
        },
      ],
    };
  },
  methods: {
    clickHandle(type, row) {
      console.log(type, row);
      this.$router.push(
        "/evaluate/netevaluationManagement/evaluationResultDetails"
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
.evaluationResultList {
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
      gap: 0 8px;
      margin-bottom: 24px;
      &-main {
        @include leftBar();
      }
      &-sub {
        height: 20px;
        line-height: 20px;
        font-size: 14px;
        color: #5e81f4;
        text-indent: 24px;
        background: url("../assets/titlesub.svg") no-repeat left center/20px
          20px;
      }
    }
  }
}
::v-deep .el-table .el-table__body-wrapper td.el-table__cell {
  border-bottom: 0.5px solid #ebeef5;
}
</style>
