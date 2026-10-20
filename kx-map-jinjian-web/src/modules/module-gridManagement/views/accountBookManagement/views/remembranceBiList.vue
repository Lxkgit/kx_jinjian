<template>
    <div>
      <PageContent>
        <!-- 过滤器 -->
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
          :maxHeight="maxHeight"
          :allColumns="columns"
          :total="total"
          :pageNum="formInline.pageNum"
          :pageSize="formInline.pageSize"
          @currentChange="currentChange"
          @sizeChange="sizeChange"
          selection
          @selectionChange="selectionChange"
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
        <addremember
          v-model="showDetail"
          :defaultValue="defaultValue"
          :status="status"
        ></addremember>
      </PageContent>
    </div>
  </template>
    
    <script>
  import { mapState, mapGetters, mapMutations } from "vuex";
  import pageMixin from "@/mixins/pageMixin";
  import addremember from "../components/addremember.vue";
  export default {
    name: "surveyRegisteList",
    mixins: [pageMixin],
    // dicts: ["grid_address_type"],
    components: {
      addremember,
    },
    data() {
      return {
        searchColumns: [
          {
            label: "网点名称",
            key: "gridName",
            type: "text",
          },
        ],
        btnLists: [
          {
            cname: "新增纪念币巡查",
            ename: "addList",
            type: "primary",
          },
        ],
        tableBtn: [
          {
            cname: "详情",
            ename: "view",
          },
          {
            cname: "编辑",
            ename: "edit",
          },
          {
            cname: "删除",
            ename: "delete",
          },
        ],
        columns: [
          { label: "网点名称", prop: "orgName", showOverFlowTooltip: true },
          { label: "巡查人员", prop: "inspector", showOverFlowTooltip: true },
          { label: "巡查时间", prop: "inspectionTime", showOverFlowTooltip: true },
          { label: "巡查内容", prop: "inspectionContent", showOverFlowTooltip: true },
          { label: "发现问题", prop: "findProblem", showOverFlowTooltip: true },
          { label: "操作", slot: true },
        ],
        tableData: [],
        showDetail: false,
        status: "add",
        defaultValue: {},
      };
    },
    computed: {
      ...mapGetters("accountBook", ["rememberList"]),
    },
    mounted() {
      this.fetchList();
    },
    methods: {
      ...mapMutations("accountBook", ["updateRememberList"]),
      fetchList() {
        const { pageNum, pageSize } = this.formInline;
        this.$refs.filterBar.getForm((formInfo) => {
          const param = {
            pageSize,
            pageNum,
            ...formInfo,
          };
          console.log(this.rememberList,'');
          this.tableData = this.rememberList;
        });
      },
      export() {},
      addList() {
        this.status = "add";
        this.showDetail = true;
      },
      delete(row) {
        let arr = [];
        arr = this.rememberList.filter((item) => !(item.id == row.id));
        this.updateRememberList([...arr]);
        this.fetchList();
      },
      view(row) {
        this.status = "view";
        this.defaultValue = row;
        this.showDetail = true;
      },
      edit(row) {
        this.status = "edit";
        this.defaultValue = row;
        this.showDetail = true;
      },
    },
  };
  </script>
    
    <style lang="scss" scoped></style>