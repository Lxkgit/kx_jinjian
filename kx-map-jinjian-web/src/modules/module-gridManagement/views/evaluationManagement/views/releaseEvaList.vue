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
import DarkEvaluate from "./darkEvaluate.vue";
import { mapGetters, mapMutations } from "vuex";
export default {
  mixins: [pageMixin],
  components: {},
  data() {
    return {
      btnLists: [
        {
          cname: "新建评价",
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
          cname: "材料上报进度明细",
          ename: "openHandle",
        },
        {
          cname: "暗访进度明细",
          ename: "darkHandle",
        },
      ],
      columns: [
        { label: "名称", prop: "name" },
        { label: "评价年度", prop: "evaluationYear" },
        { label: "材料上报开始时间", prop: "evaluationStart" },
        { label: "材料上报结束时间", prop: "evaluationEnd" },
        { label: "材料上报进度", prop: "progress" },
        { label: "暗访评价进度", prop: "superiorProgress" },
        { label: "创建时间", prop: "startupTime" },
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
          label: "评价状态",
          key: "dutyBranch",
          type: "select",
          options: [
            { label: "进行中", value: 0 },
            { label: "已结束", value: 1 },
            { label: "未开始", value: 2 },
          ],
        },
        {
          label: "评价年度",
          key: "isAllSurveyNeed",
          type: "select",
        },
      ],
    };
  },
  computed: {
    ...mapGetters('accountBook',["releaseEvaList"]),
  },
  watch: {
    releaseEvaList: {
      handler(val) {
        this.tableData = val;
      },
      deep: true,
      immediate:true
    }
  },
  mounted() {
    // this.fetchList();
  },
  methods: {
    ...mapMutations('accountBook',['updateStaetupInfo']),
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
      this.updateStaetupInfo({
        ...row,
        status:'view'
      })
      this.$router.push({
        name: "StaetupReview",
      });
    },
    darkHandle(row) {
      this.$router.push({
        name: "DarkEvaluate",
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
      this.updateStaetupInfo({
        status:'add'
      })
      this.$router.push({
        name: "StaetupReview",
      });
    },
  },
};
</script>
    
    <style lang="scss" scoped>
</style>