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
      <template #action="scope">
        <i-table-btns :btn-lists="tableBtn" @click="(type) => clickHandle(type, scope.row)"
                      :data="scope.row">
        </i-table-btns>
      </template>
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
import exportDialog from '../components/exportDialog.vue';
const TYPE_NAME = {
  0: "辖区变更记录",
  1: "网格员变更记录",
  2: "锚点变更记录",
};
export default {
  mixins: [pageMixin],
  dicts: ['log_operator_type'],
  components: {
    logDetail,
    exportDialog
  },
  props: {
    categoryId: {
      type: Number,
    },
  },
  data() {
    return {
      searchColumns: [
        {
          label: "操作人员",
          key: "operatorName",
          type: "text",
        },
        {
          label: "操作时间",
          type: "datetimerange",
          value: [],
          key: "date",
          startPlaceholder: "开始日期",
          endPlaceholder: "结束日期",
          rangeSeparator: "至",
          format: "yyyy-MM-dd HH:mm:ss",
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
      tableBtn: [
        {
          cname: "查看",
          ename: "viewHandle",
        },
      ],
      columns: [
        {
          label: "操作类型", prop: "operateType", formatter: ({ operateType }) => {
            return this.dict.label.log_operator_type[operateType]
          }
        },
        { label: "操作内容", prop: "logContent", showOverFlowTooltip: true, },
        { label: "操作人", prop: "operatorName" },
        {
          label: "操作时间",
          prop: "createTime",
          formatter: ({ createTime }) => {
            return this.parseTime(createTime);
          },
        },
        // { label: "ip地址", prop: "ipAddr" },
        // { label: "操作状态", prop: "statusName" },
      ],
      maxHeight: window.innerHeight - 370,
      showDetail: false,
      detailInfo: {},
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
          logType: this.categoryId,
          ...formInfo,
          startTime:
            formInfo.date &&
            this.parseTime(formInfo.date[0], "{y}-{m}-{d} {h}:{i}:{s}"),
          endTime:
            formInfo.date &&
            this.parseTime(formInfo.date[1], "{y}-{m}-{d} {h}:{i}:{s}"),
        };
        this.loading = true;
        service
          .paChangeLogList(param)
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
        //   logType: this.categoryId,
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
        //   `${TYPE_NAME[this.categoryId]}_${new Date().getTime()}.xlsx`
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