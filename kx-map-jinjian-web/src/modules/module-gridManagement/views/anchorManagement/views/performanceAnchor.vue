<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar ref="filterBar" slot="filter" :columns="searchColumns" :gridColumns="4"
                    labelWidth="90px">
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
      <!-- 列表 -->
      <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
               :maxHeight="maxHeight" :allColumns="columns" :total="total"
               :pageNum="formInline.pageNum" :pageSize="formInline.pageSize"
               @currentChange="currentChange" @sizeChange="sizeChange" selection
               @selectionChange="selectionChange">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type) => clickHandle(type, scope.row)"
                        :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>
    </PageContent>
  </div>
</template>

<script>
import service from "../api";
import commonService from "@/modules/module-gridManagement/api";
import pageMixin from "@/mixins/pageMixin";
export default {
  name: 'PerformanceAnchor',
  mixins: [pageMixin],
  dicts: ["grid_address_type"],
  data() {
    return {
      searchColumns: [
        {
          label: "绩效客户号",
          key: "customerNm",
          type: "text",
        },
        {
          label: "锚点名称",
          key: "anchorName",
          type: "text",
        },
        {
          label: "所属网格",
          key: "belongGrids",
          type: "cascader",
          props: {
            value: "code",
            label: "name",
            emitPath: false,
            lazy: true,
            lazyLoad: this.lazyLoad,
            multiple: true,
            checkStrictly: true
          },
          showAllLevels: false,
          clearable: true,
          collapseTags: true,
          options: [],
        },
        {
          label: "所属机构",
          key: "deptIds",
          type: "cascader",
          props: {
            label: "label",
            value: "id",
            children: "children",
            emitPath: false,
            multiple: true,
          },
          filterable: true,
          clearable: true,
          collapseTags: true,
          showAllLevels: false,
          options: this.deptTreeSelect,
        },
        {
          label: "网格管理员",
          key: "gridManager",
          type: "text",
        },
        {
          label: "管户人",
          key: "creditManager",
          type: "text",
        },
      ],
      btnLists: [
        {
          icon: "el-icon-s-promotion",
          cname: "导出",
          ename: "export",
          type: "info",
          disabled: () => this.multiSelected.length === 0,
        },
      ],
      tableBtn: [
        {
          cname: "地图锚点",
          ename: "anchorHandle",
          permi: 'anchorManagement:performanceAnchor:edit',
          isShow: (row) => row.belongGrid != '' && row.belongGrid
        },
      ],
      columns: [
        { label: "锚点名称", prop: "anchorName", showOverFlowTooltip: true, },
        { label: "绩效客户号", prop: "customerNm", showOverFlowTooltip: true, },
        { label: "锚点类型", prop: "anchorType", showOverFlowTooltip: true, },
        { label: "网格地址", prop: "addressDesc", showOverFlowTooltip: true, },
        {
          label: "地址类型",
          prop: "addressType",
          showOverFlowTooltip: true,
          formatter: ({ addressType }) => {
            return this.dict.label.grid_address_type[addressType];
          },
        },
        { label: "所属网格", prop: "belongGridName", showOverFlowTooltip: true, },
        { label: "网格管理员", prop: "gridManager", showOverFlowTooltip: true, },
        { label: "管户人", prop: "creditManager", showOverFlowTooltip: true, },
        { label: "管户人工号", prop: "creditManagerJobNumber", showOverFlowTooltip: true, },
        { label: "所属机构", prop: "branchBank", showOverFlowTooltip: true, },
        { label: "更新日期", prop: "updateTime", showOverFlowTooltip: true, },
        { label: "操作", slot: true },
      ],
      tableData: [],
    };
  },
  mounted() {
    this.fetchList();
    // this.getBelongGrid();
  },
  methods: {
    fetchList() {
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          type: 1,
          ...formInfo,
        };
        this.loading = true;
        service
          .anchorPointList(param)
          .then((res) => {
            const { total, rows } = res;
            this.tableData = rows;
            this.total = total;
            this.loading = false;
            this.multiSelected = [];
          })
          .catch((error) => {
            if (error) {
              this.tableData = []
              this.total = 0
            }
            this.multiSelected = [];
            this.loading = false;
          });
      });
    },
    //获取归属网格下拉
    lazyLoad(node, resolve) {
      const { value } = node;
      const param = {
        parentCode: value,
      };
      service.getPaAddrInfoList(param).then((res) => {
        const { data } = res;
        data.forEach((item) => {
          item.leaf = !item.isLeaf;
        });
        resolve(data);
      });
    },
    export() {
      console.log(this.multiSelected);
    },
    anchorHandle(data) {
      this.$store.commit("gridManagement/SET_ANCHOR_INFO", data);
      this.$router.push({ name: "AnchorEdit" });
    },
    // 获取责任机构下拉
    async deptTreeSelect() {
      const { data } = await commonService.deptTree();
      return data;
    },
  },
};
</script>

<style lang="scss" scoped></style>
