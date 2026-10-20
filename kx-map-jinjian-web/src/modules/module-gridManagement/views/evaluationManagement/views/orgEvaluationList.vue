<template>
  <div>
    <PageContent>
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
    </PageContent>
  </div>
</template>
  
  <script>
import pageMixin from "@/mixins/pageMixin";
import config from "../config.js";
export default {
  mixins: [pageMixin],
  components: {},
  data() {
    return {
      btnLists: [
        {
          cname: "启动自评",
          ename: "startUpHandle",
          type: "primary",
        },
      ],
      tableBtn: [
        {
          cname: "详情",
          ename: "viewHandle",
        },
        {
          cname: "工作评价",
          ename: "openHandle",
        },
        {
          cname: "删除",
          ename: "deleteHandle",
        },
      ],
      columns: [
        { label: "名称", prop: "name" },
        { label: "自评年度", prop: "evaluationYear" },
        { label: "自评状态", prop: "evaluationStatus" },
        { label: "自评开始时间", prop: "evaluationStart" },
        { label: "自评结束时间", prop: "evaluationEnd" },
        { label: "自评进度", prop: "progress" },
        { label: "上级评价进度", prop: "superiorProgress" },
        { label: "启动时间", prop: "startupTime" },
        { label: "操作", slot: true },
      ],
      showDialog: false,
      searchColumns: [
        {
          label: "名称",
          key: "name",
          type: "text",
        },
        {
          label: "自评状态",
          key: "dutyBranch",
          type: "select",
          options: [
            { label: "进行中", value: 0 },
            { label: "已结束", value: 1 },
            { label: "未开始", value: 2 },
          ],
        },
        {
          label: "自评年度",
          key: "isAllSurveyNeed",
          type: "select",
        },
      ],
    };
  },
  mounted() {
    this.fetchList();
  },
  methods: {
    fetchList() {
      this.tableData = config.list;
      // this.loading = true
      // service.listModel().then(res => {
      //   const { data } = res
      //   this.loading = false
      //   this.tableData = data
      // }).catch((error) => {
      //   if (error) {
      //     this.tableData = []
      //     this.total = 0
      //   }
      //   this.loading = false
      // })
    },
    viewHandle(row) {
      this.$router.push({
        name: "StaetupReview",
      });
    },
    openHandle(row) {
      this.$router.push({
        name: "JobEvaluation",
      });
    },
    deleteHandle(row) {
      this.makeSure("确认删除吗？", () => {
        this.tableData = this.tableData.filter((item) => item.id !== row.id);
      });
    },
    startUpHandle() {
      console.log(111);
      this.$router.push({
        name: "StaetupReview",
      });
    },
  },
};
</script>
  
  <style lang="scss" scoped>
</style>