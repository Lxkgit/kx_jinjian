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
import { mapGetters, mapMutations } from "vuex";
import DarkEvaluate from "./darkEvaluate.vue";
import { param } from "../../../../../utils";
export default {
  mixins: [pageMixin],
  components: {},
  data() {
    return {
      btnLists: [
        {
          cname: "新建年度评价",
          ename: "startUpHandle",
          type: "primary",
        },
      ],
      tableBtn: [
        // {
        //   cname: "详情",
        //   ename: "viewHandle",
        // },
        {
          cname: "评价明细",
          ename: "openHandle",
        },
        {
          cname: "编辑",
          ename: "editHandle",
        },
        {
          cname: "删除",
          ename: "deleteHandle",
        },
      ],
      columns: [
        { label: "名称", prop: "name" },
        { label: "评价年度", prop: "evaluationYear" },
        { label: "暗访评价进度", prop: "progress" },
        { label: "创建时间", prop: "creatTime" },
        { label: "操作", slot: true, width: "200px" },
      ],
      showDialog: false,
      searchColumns: [
        {
          label: "名称",
          key: "name",
          type: "text",
        },
        // {
        //   label: "自评状态",
        //   key: "dutyBranch",
        //   type: "select",
        //   options: [
        //     { label: "进行中", value: 0 },
        //     { label: "已结束", value: 1 },
        //     { label: "未开始", value: 2 },
        //   ],
        // },
        {
          label: "评价年度",
          key: "isAllSurveyNeed",
          type: "select",
          options: [
            { label: "2025年", value: 0 },
            { label: "2024年", value: 1 },
            { label: "2023年", value: 2 },
            { label: "2022年", value: 3 },
          ],
        },
      ],
    };
  },
  computed: {
    ...mapGetters("accountBook", ["bankEvaluationList"]),
  },
  mounted() {
    this.fetchList();
  },
  methods: {
    ...mapMutations("accountBook", ["updateBankInfo",'updatebankEvaluationList','updateAddBankInfo']),
    fetchList() {
      this.tableData = JSON.parse(JSON.stringify(this.bankEvaluationList));
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
      this.updateBankInfo({
        ...row,
        status: "view",
      });
      this.$router.push({
        name: "AnevalutainDetail",
      });
    },
    editHandle(row) {
      console.log(row)
      this.updateAddBankInfo({
        ...row,
        status: "edit",
      });
      this.$router.push({
        name: "AddAnevalutainDetai",
      });
    },
    openHandle(row) {
      this.$router.push({
        name: "BankDarkEvaluate",
      });
    },
    deleteHandle(row) {
      this.makeSure("确认删除吗？", () => {
        this.tableData = this.tableData.filter((item) => item.id !== row.id);
        this.updatebankEvaluationList(this.tableData);
        this.$modal.msgSuccess("删除成功");
      });
    },
    startUpHandle() {
      this.updateAddBankInfo({
        status: "add",
      });
      this.$router.push({
        name: "AddAnevalutainDetai",
      });
    },
  },
};
</script>
      
      <style lang="scss" scoped>
</style>