<template>
  <PageContent>
    <section class="evaluationResultDetails-title">
      <p>
        <span class="evaluationResultDetails-title-main">
          2024年度现金服务工作非现场评价
        </span>
        <span class="evaluationResultDetails-title-subl"
          >地市人行评价结果：</span
        >
        <span class="evaluationResultDetails-title-subr"
          >人民银行金华市分行 89分</span
        >
      </p>
      <el-button @click="$router.back()">返回</el-button>
    </section>

    <section class="evaluationResultDetails-table">
      <section class="evaluationResultDetails-table-title">
        <p class="evaluationResultDetails-table-title-main">
          银行机构类型评价结果清单
        </p>
        <p class="evaluationResultDetails-table-title-sub">
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
  name: "evaluationResultDetails",
  data() {
    return {
      tableData: [
        { a: "建设银行金华分行", b: "彰化", c: "13676543654", d: 89 },
        { a: "招商银行金华分行", b: "刘强", c: "13676543654", d: 78 },
        { a: "工商银行金华分行", b: "王二麻", c: "13676543654", d: 89 },
        { a: "中国银行北京分行", b: "张三", c: "13812345678", d: 85 },
        { a: "农业银行上海分行", b: "李四", c: "13987654321", d: 82 },
        { a: "交通银行广州分行", b: "王五", c: "13765432198", d: 80 },
        { a: "浦发银行深圳分行", b: "赵六", c: "13654321876", d: 84 },
        { a: "邮政储蓄银行杭州分行", b: "钱七", c: "13543218765", d: 79 },
        { a: "光大银行成都分行", b: "孙八", c: "13432187654", d: 87 },
        { a: "民生银行武汉分行", b: "周九", c: "13345678901", d: 83 },
      ],
      columns: [
        {
          label: "银行机构名称",
          prop: "a",
          showOverFlowTooltip: true,
        },
        {
          label: "银行机构联系人",
          prop: "b",
          showOverFlowTooltip: true,
        },
        {
          label: "银行机构联系人电话",
          prop: "c",
          showOverFlowTooltip: true,
        },
        { label: "银行机构评价平均分", showOverFlowTooltip: true, prop: "d" },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "查看得分明细",
          ename: "details",
        },
      ],
    };
  },
  methods: {
    clickHandle(type, row) {
      console.log(type, row);
      this.$router.push(
        "/evaluate/netevaluationManagement/evaluationResultDetailsItem"
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
.evaluationResultDetails {
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
