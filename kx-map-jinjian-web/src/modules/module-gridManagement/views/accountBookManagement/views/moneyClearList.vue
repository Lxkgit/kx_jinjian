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
      <addmoneyClearList
        v-model="showDetail"
        :defaultValue="defaultValue"
        :status="status"
      ></addmoneyClearList>
    </PageContent>
  </div>
</template>
    
    <script>
import { mapState, mapGetters, mapMutations } from "vuex";
import pageMixin from "@/mixins/pageMixin";
import addmoneyClearList from "../components/addmoneyClearList.vue";
export default {
  name: "surveyRegisteList",
  mixins: [pageMixin],
  // dicts: ["grid_address_type"],
  components: {
    addmoneyClearList,
  },
  data() {
    return {
      searchColumns: [
        {
          label: "记录人",
          key: "gridName",
          type: "text",
        },
      ],
      btnLists: [
        {
          cname: "新增全额清分",
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
        { label: "记录人", prop: "records", showOverFlowTooltip: true },
        { label: "记录内容", prop: "recordContent", showOverFlowTooltip: true },
        { label: "摸排日期", prop: "feelDate", showOverFlowTooltip: true },
        { label: "记录时间", prop: "recordTime", showOverFlowTooltip: true },
        { label: "操作", slot: true },
      ],
      tableData: [],
      showDetail: false,
      status: "add",
      defaultValue: {},
    };
  },
  computed: {
    ...mapGetters("accountBook", ["amountClearList"]),
  },
  mounted() {
    this.fetchList();
  },
  methods: {
    ...mapMutations("accountBook", ["updateAmountClearList"]),
    fetchList() {
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          ...formInfo,
        };
        this.tableData = this.amountClearList;
      });
    },
    export() {},
    addList() {
      this.status = "add";
      this.showDetail = true;
    },
    delete(row) {
      let arr = [];
      arr = this.amountClearList.filter((item) => !(item.id == row.id));
      this.updateAmountClearList([...arr]);
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