<template>
  <PageContent>
    <section class="onSiteEvaluationDetails-title">
      <p>
        <span class="onSiteEvaluationDetails-title-main"> 2024年现场评价 </span>
      </p>
      <p>
        <span class="onSiteEvaluationDetails-title-sub">
          （2024年度-农村商业银行-中国建设银行）
        </span>
        <el-button type="primary" @click="clickHandle('change')"
          >条件切换</el-button
        >
      </p>
    </section>

    <section class="onSiteEvaluationDetails-search">
      <i-filter-bar
        :columns="searchColumns"
        ref="filterBar"
        slot="filter"
        :gridColumns="4"
        :buttonSpan="3"
        label-width="120px"
      >
        <!-- 搜索与重置按钮 -->
        <el-button type="primary">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
    </section>

    <section class="onSiteEvaluationDetails-content">
      <tableTopbar
        leftTitle="数据明细"
        buttonText="导出"
        elButtonType="default"
        @click="clickHandle('export')"
        style="margin-bottom: 16px"
      />
      <i-table
        ref="table"
        :data="tableData"
        align="center"
        :stripe="false"
        :allColumns="columns"
        :showPagination="false"
        @sortChange="sortChange"
      >
      </i-table>
    </section>
    <changeDialog :dialogVisible.sync="showDialog" @submit="changeDialog" />
  </PageContent>
</template>
<script>
import changeDialog from "../components/changeDialog.vue";
import tableTopbar from "../components/tableTopbar.vue";
export default {
  name: "onSiteEvaluationDetails",
  components: { tableTopbar, changeDialog },
  data() {
    return {
      searchColumns: [
        {
          label: "地市分行名称",
          key: "name",
          type: "text",
          options: [],
        },
      ],
      tableData: [
        {
          a: "杭州分行",
          b: "35",
          c: "35",
          d: "35",
          e: "123,243",
          f: "1,765",
          g: "27,867,765",
          h: "1,765",
          i: "1,765",
          j: "1,765",
          k: "1,765",
          l: "1,765",
        },
        {
          a: "绍兴分行",
          b: "63",
          c: "63",
          d: "63",
          e: "123,243",
          f: "1,765",
          g: "27,867,765",
          h: "1,765",
          i: "1,765",
          j: "1,765",
          k: "1,765",
          l: "1,765",
        },
        {
          a: "杭州分行",
          b: "35",
          c: "35",
          d: "35",
          e: "123,243",
          f: "1,765",
          g: "27,867,765",
          h: "1,765",
          i: "1,765",
          j: "1,765",
          k: "1,765",
          l: "1,765",
        },
        {
          a: "绍兴分行",
          b: "63",
          c: "63",
          d: "63",
          e: "123,243",
          f: "1,765",
          g: "27,867,765",
          h: "1,765",
          i: "1,765",
          j: "1,765",
          k: "1,765",
          l: "1,765",
        },
        {
          a: "杭州分行",
          b: "35",
          c: "35",
          d: "35",
          e: "123,243",
          f: "1,765",
          g: "27,867,765",
          h: "1,765",
          i: "1,765",
          j: "1,765",
          k: "1,765",
          l: "1,765",
        },
        {
          a: "绍兴分行",
          b: "63",
          c: "63",
          d: "63",
          e: "123,243",
          f: "1,765",
          g: "27,867,765",
          h: "1,765",
          i: "1,765",
          j: "1,765",
          k: "1,765",
          l: "1,765",
        },
        {
          a: "杭州分行",
          b: "35",
          c: "35",
          d: "35",
          e: "123,243",
          f: "1,765",
          g: "27,867,765",
          h: "1,765",
          i: "1,765",
          j: "1,765",
          k: "1,765",
          l: "1,765",
        },
        {
          a: "绍兴分行",
          b: "63",
          c: "63",
          d: "63",
          e: "123,243",
          f: "1,765",
          g: "27,867,765",
          h: "1,765",
          i: "1,765",
          j: "1,765",
          k: "1,765",
          l: "1,765",
        },
      ],
      columns: [
        {
          label: "地市分行名称",
          prop: "a",
          showOverFlowTooltip: true,
          sortable: true,
        },
        {
          label: "综合评价得分",
          prop: "b",
          showOverFlowTooltip: true,
          sortable: true,
        },
        {
          label: "非现场评价得分",
          prop: "c",
          showOverFlowTooltip: true,
          sortable: true,
        },
        {
          label: "现场评价得分",
          prop: "d",
          showOverFlowTooltip: true,
          sortable: true,
        },
        {
          label: "参与评价银行机构数",
          prop: "e",
          showOverFlowTooltip: true,
          sortable: true,
        },
        {
          label: "国有商业银行机构数",
          prop: "f",
          showOverFlowTooltip: true,
          sortable: true,
        },
        {
          label: "股份制商业银行机构数",
          prop: "g",
          showOverFlowTooltip: true,
          sortable: true,
        },
        {
          label: "中国邮政储蓄银行机构数",
          prop: "h",
          showOverFlowTooltip: true,
          sortable: true,
        },
        {
          label: "城市商业银行机构数",
          prop: "i",
          showOverFlowTooltip: true,
          sortable: true,
        },
        {
          label: "农村商业银行机构数",
          prop: "j",
          showOverFlowTooltip: true,
          sortable: true,
        },
        {
          label: "村镇银行机构数",
          prop: "k",
          showOverFlowTooltip: true,
          sortable: true,
        },
        {
          label: "外资银行机构数",
          prop: "l",
          showOverFlowTooltip: true,
          sortable: true,
        },
      ],
      showDialog: false,
    };
  },
  methods: {
    clickHandle(type, item) {
      switch (type) {
        case "change":
          this.showDialog = true;
          break;
        case "export":
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
    sortChange({ column, prop, order }) {
      console.log({ column, prop, order });
    },
    changeDialog() {},
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
    background: url("../assets/city.svg") no-repeat 16px center/24px 24px, #fff;
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
    &-sub {
      font-size: 16px;
      color: #898eac;
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
      border-bottom: 0.5px solid #ebeef5;
    }
    &-title {
      margin-bottom: 24px;
      @include leftBar();
    }
  }
}
</style>
