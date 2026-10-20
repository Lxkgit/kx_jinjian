<template>
  <PageContent>
    <!-- 过滤器 -->
    <i-filter-bar ref="filterBar" slot="filter" :columns="searchColumns" :gridColumns="3">
      <el-button type="primary" @click="searchHandle">搜索</el-button>
      <el-button @click="resetHandle">重置</el-button>
    </i-filter-bar>
    <!-- 按钮 -->
    <i-button-bar :btn-lists="btnLists" @click="clickHandle" slot="btn" ref="btnBar">
    </i-button-bar>
    <!-- 列表 -->
    <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
             :maxHeight="maxHeight" :allColumns="columns" :total="total"
             :pageNum="formInline.pageNum" :pageSize="formInline.pageSize"
             @currentChange="currentChange" @sizeChange="sizeChange">
    </i-table>
    <!-- 查看 -->
    <logDetail v-model="showDetail" :detailInfo="detailInfo"></logDetail>
    <export-dialog v-model="showDialog" :defaultValue="defaultValue"
                   :categoryId="categoryId"></export-dialog>
  </PageContent>
</template>

<script>
import service from "../api";
import logDetail from "../components/logDetail";
import pageMixin from "@/mixins/pageMixin";
import exportDialog from '../components/exportDialog'
const TYPE_NAME = {
  0: "辖区变更记录",
  1: "网格员变更记录",
  2: "锚点变更记录",
};
export default {
  name: 'AnchorChange',
  mixins: [pageMixin],
  components: {
    logDetail,
    exportDialog
  },
  dicts: ["grid_address_type","anchor_type"],
  data() {
    return {
      searchColumns: [
        {
          label: "锚点名称",
          key: "anchorName",
          type: "text",
        },
        {
          label: "原网格地址类型",
          type: "select",
          key: "oldAddressTypeCodes",
          placeholder: "请选择网格地址类型",
          options: () => this.dict.type.grid_address_type,
        },
        {
          label: "变更后网格地址类型",
          type: "select",
          key: "newAddressTypeCodes",
          placeholder: "请选择网格地址类型",
          options: () => this.dict.type.grid_address_type,
        },
        {
          label: "操作人",
          key: "operatorName",
          type: "text",
        },
      ],
      btnLists: [
        {
          cname: "导出",
          ename: "exportHandle",
          type: "primary",
        },
      ],
      columns: [
        { label: "锚点名称", prop: "anchorName" },
        { label: "锚点类型", prop: "typeName" },
        { label: "原网格地址", prop: "oldAddress" },
        {
          label: "原网格地址类型", prop: "oldAddressType", formatter: ({ oldAddressType }) => {
            return this.dict.label.grid_address_type[oldAddressType]
          }
        },
        { label: "变更后网格地址", prop: "newAddress" },
        {
          label: "变更后网格地址类型",
          prop: "newAddressType",
          formatter: ({ newAddressType }) => {
            return this.dict.label.grid_address_type[newAddressType]
          }
        },
        { label: "操作人", prop: "operatorName" },
        {
          label: "调整时间", prop: "createTime", formatter: ({ createTime }) => {
            return this.parseTime(createTime)
          }
        },
      ],
      maxHeight: window.innerHeight - 370,
      showDetail: false,
      detailInfo: {},
      categoryId: 2,
      tableData: [],
      showDialog: false,
      defaultValue: {}
    };
  },
  mounted() {
    this.fetchList();
  },
  methods: {
    fetchList() {
      const { pageNum, pageSize } = this.formInline;
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          ...formInfo,
        };
        this.loading = true;
        service
          .listAnchorChangeLog(param)
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
    exportHandle() {
      this.$refs.filterBar.getForm((formInfo) => {
        // const param = {
        //   logType: this.activeName,
        //   ...formInfo,
        //   startTime:
        //     formInfo.date &&
        //     this.parseTime(formInfo.date[0], "{y}-{m}-{d} {h}:{i}:{s}"),
        //   endTime:
        //     formInfo.date &&
        //     this.parseTime(formInfo.date[1], "{y}-{m}-{d} {h}:{i}:{s}"),
        // };
        // this.download(
        //   service.paChangeLogExport(),
        //   {
        //     ...param,
        //   },
        //   `${TYPE_NAME[this.activeName]}_${new Date().getTime()}.xlsx`
        // );
        this.showDialog = true
        this.defaultValue = {
          ...formInfo,
          pageSize: this.formInline.pageSize,
          total: this.total
        }
      });
    },
    viewHandle(data) {
      this.detailInfo = data;
      this.showDetail = true;
    },
  },
};
</script>

<style lang="scss" scoped>
</style>