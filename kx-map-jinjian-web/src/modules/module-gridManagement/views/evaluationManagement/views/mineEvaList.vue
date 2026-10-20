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
      <viewEvalistDetail v-model="showDialog"></viewEvalistDetail>
    </PageContent>
  </div>
</template>
      
<script>
import pageMixin from "@/mixins/pageMixin";
import config from "../config.js";
import { mapGetters, mapMutations } from "vuex";
import viewEvalistDetail from "../components/viewEvalistDetail.vue";
export default {
  mixins: [pageMixin],
  components: {viewEvalistDetail},
  data() {
    return {
      btnLists: [
        {
          cname: "新建年度自评",
          ename: "startUpHandle",
          type: "primary",
        },
      ],
      tableBtn: [
        {
          cname: "上报查看",
          ename: "viewHandle",
          isShow: ({ status }) => status == '1',
        },
        {
          cname: "评价查看",
          ename: "viewEvalist",
          isShow: ({ status }) => status == '1',
        },
        {
          cname: "材料上报",
          ename: "reportHandle",
          isShow: ({ status }) => status == '0',
        },
      ],
      columns: [
        { label: "名称", prop: "name" },
        { label: "评价年度", prop: "evaluationYear" },
        {
          label: "材料上报状态",
          prop: "status",
          formatter: ({ status }) => {
            return ["未上报", "已上报"][status];
          },
        },
        { label: "材料上报开始时间", prop: "evaluationStart" },
        { label: "材料上报结束时间", prop: "evaluationEnd" },
        { label: "评价得分", prop: "startupTime" },
        { label: "操作", slot: true, width: "200px" },
      ],
      showDialog: false,
      searchColumns: [
        {
          label: "名称",
          key: "name",
          type: "text",
        },
        {
          label: "评价年度",
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
    ...mapMutations("accountBook", ["updateReportInfo"]),
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
    reportHandle(row) {
      this.updateReportInfo({
        status:'report'
      })
      this.$router.push({
        name: "SelfEvaluate",
      });
    },
    viewHandle(row) {
      this.updateReportInfo({
        status:'view'
      })
      this.$router.push({
        name: "SelfEvaluate",
      });
    },
    viewEvalist(row) {
      this.showDialog = true
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