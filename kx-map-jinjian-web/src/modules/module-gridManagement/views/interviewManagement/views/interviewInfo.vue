<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar ref="filterBar" slot="filter" :columns="searchColumns" :gridColumns="4">
        <div v-show="searchColumns.length > 0">
          <el-button type="primary" @click="searchHandle">搜索</el-button>
          <el-button @click="resetHandle">重置</el-button>
        </div>
      </i-filter-bar>
      <!-- 按钮 -->
      <i-button-bar :btn-lists="btnLists" @click="clickHandle" slot="btn" ref="btnBar">
      </i-button-bar>
      <!-- 列表 -->
      <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe showIndex
               :maxHeight="maxHeight" :allColumns="columns" :total="total"
               :pageNum="formInline.pageNum" :pageSize="formInline.pageSize"
               @currentChange="currentChange" @sizeChange="sizeChange">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type) => clickHandle(type, scope.row)"
                        :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>
    </PageContent>
    <detailInfo v-model="showDetail" :editInfo="editInfo" />
    <exportDialog v-model="showExport" :dataInfo="dataInfo" edportUrl="system/customerVisit/export"
                  fileName="走访统计" />
  </div>
</template>

<script>
import service from "../api";
import optionMixin from "@/mixins/optionMixin";
import pageMixin from "@/mixins/pageMixin";
import detailInfo from "./detailInfo.vue";
import exportDialog from '../components/exportDialog'
export default {
  name: 'InterviewInfo',
  mixins: [pageMixin, optionMixin],
  components: {
    detailInfo,
    exportDialog
  },
  data() {
    return {
      searchColumns: [
        {
          label: "走访人姓名",
          key: "visitsManagerName",
          type: "text",
        },
        {
          label: "归属机构",
          key: "visitsOrgCode",
          value: "",
          type: "cascader",
          props: {
            label: "label",
            value: "orgNum",
            children: "children",
            emitPath: false,
            checkStrictly: true,
          },
          filterable: true,
          clearable: true,
          options: this.deptTreeSelect,
        },
        {
          label: "走访时间",
          type: "daterange",
          value: [],
          key: "daterange",
          startPlaceholder: "开始日期",
          endPlaceholder: "结束日期",
          rangeSeparator: "-",
          format: "yyyy-MM-dd",
          valueFormat: 'yyyy-MM-dd',
          unlinkPanels: true,
        },
      ],
      btnLists: [
        {
          cname: "导出",
          ename: "exportHandle",
          type: "primary",
        },
      ],
      maxHeight: window.innerHeight - 300,
      columns: [
        { label: "走访人", prop: "visitsManagerName" },
        { label: "走访人工号", prop: "visitsManagerCode" },
        { label: "走访次数", prop: "visitsCount" },
        { label: "走访客户数", prop: "visitsCustomerCount" },
        { label: "所属机构", prop: "visitsOrgName", showOverFlowTooltip: true, },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "详情",
          ename: "handleDetail",
          type: "text",
        },
      ],
      showDetail: false,
      editInfo: {},
      showExport: false,
      dataInfo: {},
    };
  },
  mounted() {
    this.fetchList();
  },
  methods: {
    fetchList() {
      this.$refs.filterBar.getForm((formInfo) => {
        const { daterange, ...others } = formInfo
        const param = {
          ...this.formInline,
          ...others,
          visitsStartTime: daterange && daterange[0],
          visitsEndTime: daterange && daterange[1]
        };
        this.loading = true
        service.customerVisitList(param).then((res) => {
          this.tableData = res.rows
          this.total = res.total
          this.loading = false
        }).catch((error) => {
          if (error) {
            this.tableData = []
            this.total = 0
          }
          this.loading = false;
        });
      });
    },
    handleDetail(row) {
      this.showDetail = true;
      this.editInfo = row
    },
    exportHandle() {
      this.$refs.filterBar.getForm((formInfo) => {
        const { daterange, ...others } = formInfo
        const param = {
          ...this.formInline,
          ...others,
          visitsStartTime: daterange && daterange[0],
          visitsEndTime: daterange && daterange[1]
        };
        this.dataInfo = param
        this.showExport = true
      })
    }
  },

};
</script>

<style lang="scss" scoped></style>
