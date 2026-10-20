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
    </PageContent>
    <add-store-dialog
      v-model="showAddStore"
      :defaultValue="defaultValue"
      :status="status"
    ></add-store-dialog>
    <visit-list-dialog
      v-model="showVisitList"
      :defaultValue="defaultValue"
      @fetchList="fetchList"
    ></visit-list-dialog>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import pageMixin from "@/mixins/pageMixin";
import addStoreDialog from "../components/addStoreDialog.vue";
import dialogMixin from "@/mixins/dialogMixin.vue";
import visitListDialog from "../components/visitListDialog.vue";
export default {
  name: "surveyRegisteList",
  mixins: [pageMixin, dialogMixin],
  dicts: ["merchant_type", "merchant_classify_type"],
  components: { addStoreDialog, visitListDialog },
  data() {
    return {
      searchColumns: [
        {
          label: "商户名称",
          key: "storeName",
          type: "text",
        },
        {
          label: "最近走访时间",
          key: "date",
          type: "datetimerange",
          startPlaceholder: "开始日期",
          endPlaceholder: "结束日期",
          rangeSeparator: "至",
          format: "yyyy-MM-dd HH:mm:ss",
          unlinkPanels: true,
          span: 2,
        },
        {
          label: "商户分类",
          key: "storeClassify",
          type: "select",
          options: () => this.dict.type.merchant_classify_type,
        },
      ],
      btnLists: [
        { cname: "新增商户摸排", ename: "addStore", type: "primary" },
        { cname: "导出", ename: "export" },
      ],
      tableBtn: [
        {
          cname: "走访登记",
          ename: "toVisit",
        },
        {
          cname: "编辑",
          ename: "editStore",
        },
        {
          cname: "删除",
          ename: "deleteStore",
        },
      ],
      columns: [
        { label: "商户名称", prop: "storeName", showOverFlowTooltip: true },
        { label: "商户地址", prop: "storeAddress", showOverFlowTooltip: true },
        {
          label: "商户类型",
          prop: "storeType",
          showOverFlowTooltip: true,
          formatter: ({ storeType }) => {
            return this.dict.label.merchant_type[storeType];
          },
        },
        {
          label: "商户联系人",
          prop: "storeLinkMan",
          showOverFlowTooltip: true,
        },
        { label: "走访次数", prop: "visitNum", showOverFlowTooltip: true },
        {
          label: "最近走访时间",
          prop: "lastVisitTime",
          showOverFlowTooltip: true,
        },
        {
          label: "商户分类",
          prop: "storeClassify",
          showOverFlowTooltip: true,
          formatter: ({ storeClassify }) => {
            return this.dict.label.merchant_classify_type[storeClassify];
          },
        },
        { label: "操作", slot: true },
      ],
      tableData: [],
      showAddStore: false,
      defaultValue: {},
      status: "add",
      showVisitList: false,
    };
  },
  computed: {
    ...mapState("accountBook", [
      "bankList",
      "gridPointList",
      "nowGridInfo",
      "storeList",
    ]),
  },
  mounted() {
    this.fetchList();
  },
  methods: {
    ...mapMutations("accountBook", ["updeteStoreList", "updateTargetStoreNum"]),
    fetchList() {
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          ...formInfo,
        };
        let arr = this.storeList.filter(
          (item) => item.gridId == this.nowGridInfo.id
        );
        this.total = arr.length;
        this.tableData = [...arr];

        // this.loading = true;
        // service
        //   .anchorPointList(param)
        //   .then((res) => {
        //     const { total, rows } = res;
        //     this.tableData = rows;
        //     this.total = total;
        //     this.loading = false;
        //     this.multiSelected = [];
        //   })
        //   .catch((error) => {
        //     if (error) {
        //       this.tableData = [];
        //       this.total = 0;
        //     }
        //     this.multiSelected = [];
        //     this.loading = false;
        //   });
      });
    },
    addStore() {
      this.status = "add";
      this.showAddStore = true;
    },
    export() {},
    toVisit(row) {
      this.defaultValue = row;
      this.showVisitList = true;
    },
    editStore(row) {
      this.defaultValue = row;
      this.status = "edit";
      this.showAddStore = true;
    },
    deleteStore(row) {
      this.makeSure("是否要删除该商户?", () => {
        let arr = this.storeList;
        arr = arr.filter((item) => item.id != row.id);
        this.updeteStoreList([...arr]);
        this.updateTargetStoreNum();
        this.$message.success("删除商户成功!");
        this.fetchList();
      });
    },
  },
};
</script>

<style lang="scss" scoped></style>