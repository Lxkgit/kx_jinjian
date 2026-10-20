<template>
  <PageContent>
    <section class="onSiteEvaluationList-search">
      <i-filter-bar
        :columns="searchColumns"
        ref="filterBar"
        slot="filter"
        :gridColumns="4"
        :buttonSpan="2"
        label-width="80px"
      >
        <!-- 搜索与重置按钮 -->
        <el-button type="primary">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
    </section>

    <section class="onSiteEvaluationList-content">
      <tableTopbar
        leftTitle="数据明细"
        buttonText="新增现场评价"
        @click="clickHandle('add')"
        style="margin-bottom: 16px"
      />
      <i-table
        ref="table"
        :data="tableData"
        align="center"
        :stripe="false"
        :allColumns="columns"
        :showPagination="false"
      >
        <template #action="scope">
          <i-table-btns
            :btn-lists="tableBtn"
            :count="4"
            @click="(type) => clickHandle(type, scope.row)"
            :data="scope.row"
          >
          </i-table-btns>
        </template>
      </i-table>
    </section>
    <addOnSiteEvaluationDialog
      :dialogVisible.sync="showDialog"
      @submit="newonSiteEvalua"
    />
  </PageContent>
</template>
<script>
import tableTopbar from "../components/tableTopbar.vue";
import addOnSiteEvaluationDialog from "../components/addOnSiteEvaluationDialog.vue";
export default {
  name: "onSiteEvaluationList",
  components: { tableTopbar, addOnSiteEvaluationDialog },
  data() {
    return {
      searchColumns: [
        {
          label: "评价标题",
          key: "name",
          type: "text",
        },
        {
          label: "评价年度",
          key: "year",
          type: "monthrange",
          format: "yyyy",
        },
      ],
      tableData: [
        {
          a: "2024现场评价",
          b: "2024年",
          c: "衢州市",
          d: "98",
          e: "金华人行、建设银行金华分行...",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2023现场评价",
          b: "2023年",
          c: "温州市",
          d: "85",
          e: "工商银行金华市分行、中国银行金华市分行",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2025现场评价",
          b: "2025年",
          c: "杭州市",
          d: "92",
          e: "工商银行杭州市分行、招商银行杭州市分行",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2022现场评价",
          b: "2022年",
          c: "宁波市",
          d: "88",
          e: "中国银行宁波市分行、建设银行宁波市分行",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2021现场评价",
          b: "2021年",
          c: "嘉兴市",
          d: "95",
          e: "农业银行嘉兴市分行、交通银行嘉兴市分行",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2020现场评价",
          b: "2020年",
          c: "湖州市",
          d: "81",
          e: "工商银行湖州市分行、中国银行湖州市分行",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2026现场评价",
          b: "2026年",
          c: "绍兴市",
          d: "96",
          e: "建设银行绍兴市分行、招商银行绍兴市分行",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2024现场评价",
          b: "2024年",
          c: "金华市",
          d: "89",
          e: "农业银行金华市分行、交通银行金华市分行",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2023现场评价",
          b: "2023年",
          c: "台州市",
          d: "84",
          e: "工商银行台州市分行、建设银行台州市分行",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2022现场评价",
          b: "2022年",
          c: "丽水市",
          d: "91",
          e: "中国银行丽水市分行、招商银行丽水市分行",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2021现场评价",
          b: "2021年",
          c: "舟山市",
          d: "87",
          e: "交通银行舟山市分行、工商银行舟山市分行",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2020现场评价",
          b: "2020年",
          c: "衢州市",
          d: "80",
          e: "农业银行衢州市分行、建设银行衢州市分行...",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2025现场评价",
          b: "2025年",
          c: "温州市",
          d: "90",
          e: "中国银行温州市分行、交通银行温州市分行",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2026现场评价",
          b: "2026年",
          c: "杭州市",
          d: "97",
          e: "工商银行杭州市分行、招商银行杭州市分行",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2024现场评价",
          b: "2024年",
          c: "宁波市",
          d: "93",
          e: "建设银行宁波市分行、农业银行宁波市分行",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2023现场评价",
          b: "2023年",
          c: "嘉兴市",
          d: "86",
          e: "交通银行嘉兴市分行、中国银行嘉兴市分行",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2022现场评价",
          b: "2022年",
          c: "湖州市",
          d: "94",
          e: "工商银行湖州市分行、招商银行湖州市分行",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2021现场评价",
          b: "2021年",
          c: "绍兴市",
          d: "82",
          e: "建设银行绍兴市分行、交通银行绍兴市分行",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2020现场评价",
          b: "2020年",
          c: "台州市",
          d: "99",
          e: "农业银行台州市分行、中国银行台州市分行",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
        {
          a: "2025现场评价",
          b: "2025年",
          c: "丽水市",
          d: "100",
          e: "工商银行丽水市分行、建设银行丽水市分行",
          f: "2025-03-12 18:00",
          g: "人民银行金华市分行",
        },
      ],
      columns: [
        {
          label: "评价标题",
          prop: "a",
          showOverFlowTooltip: true,
        },
        {
          label: "评价年度",
          prop: "b",
          showOverFlowTooltip: true,
        },
        {
          label: "评价对象地市",
          prop: "c",
          showOverFlowTooltip: true,
        },
        { label: "评价对象综合得分", prop: "d" },
        { label: "评价参与机构", prop: "e" },
        { label: "创建时间", prop: "f" },
        { label: "创建机构", prop: "g" },
        { label: "操作", slot: true, width: "250px" },
      ],
      tableBtn: [
        {
          cname: "详情",
          ename: "details",
        },
        {
          cname: "编辑",
          ename: "edit",
        },
        {
          cname: "现场评价明细",
          ename: "onSite",
        },
        {
          cname: "删除",
          ename: "delete",
          danger: true,
        },
      ],
      showDialog: false,
    };
  },
  methods: {
    clickHandle(type, item) {
      switch (type) {
        case "add":
          this.showDialog = true;
          break;
        case "details":
          this.showDialog = true;

          break;
        case "edit":
          this.showDialog = true;
          break;
        case "onSite":
          this.$router.push(
            "/evaluate/evaluationManagement/onSiteEvaluationDetails"
          );
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
  },
};
</script>
<style lang="scss" scoped>
.onSiteEvaluationList {
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
  }
}
</style>
