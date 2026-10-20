<template>
  <div>
    <PageContent>
      <el-tabs v-model="activeIndex" @tab-click="handleClick">
        <el-tab-pane label="移交审批" name="1"></el-tab-pane>
        <el-tab-pane label="移交申请" name="2"></el-tab-pane>
        <el-tab-pane label="移交接收" name="3"></el-tab-pane>
      </el-tabs>
      <!-- 过滤器 -->
      <i-filter-bar ref="filterBar" :columns="searchColumns" :gridColumns="4">
        <div v-show="searchColumns.length > 0">
          <el-button type="primary" @click="searchHandle">搜索</el-button>
          <el-button @click="resetHandle">重置</el-button>
        </div>
      </i-filter-bar>
      <!-- 按钮 -->
      <i-button-bar v-show="activeIndex==='1'" :btn-lists="btnLists" @click="clickHandle"
                    ref="btnBar">
      </i-button-bar>
      <!-- 列表 -->
      <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
               :maxHeight="maxHeight" :allColumns="columns" :total="total"
               :pageNum="formInline.pageNum" :pageSize="formInline.pageSize"
               @currentChange="currentChange" @sizeChange="sizeChange" :selection="activeIndex=='1'"
               @selectionChange="selectionChange" :selectable="selectable">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type) => clickHandle(type, scope.row)"
                        :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>
    </PageContent>
    <approvalDialog v-model="showDialog" :status="status" :editInfo="editInfo"
                    :activeIndex="activeIndex"></approvalDialog>
    <approvalBatchDialog v-model="showBatch" :selected="multiSelected"></approvalBatchDialog>
  </div>
</template>

<script>
import service from '../api';
import pageMixin from "@/mixins/pageMixin";
import approvalDialog from '../components/approvalDialog';
import approvalBatchDialog from '../components/approvalBatchDialog';
export default {
  name: 'HandoverManage',
  mixins: [pageMixin],
  dicts: ['approval_status_type'],
  components: {
    approvalDialog,
    approvalBatchDialog
  },
  data() {
    return {
      activeIndex: '1',
      searchColumns: [
        {
          label: "审批状态",
          key: "moveStatus",
          type: "select",
          options: () => this.dict.type.approval_status_type,
        },
        {
          label: "移交人",
          key: "moveOutManagerName",
          type: "text",
          hidden: () => this.activeIndex == '2'
        },
        {
          label: "接收人",
          key: "moveInManagerName",
          type: "text",
          hidden: () => this.activeIndex != '2'
        },
      ],
      btnLists: [
        {
          cname: "批量审批",
          ename: "batchApproval",
          type: "primary",
          disabled: () => this.multiSelected.length === 0 || this.activeIndex != 1
        },
      ],
      maxHeight: window.innerHeight - 360,
      columns: [
        { label: "移交客户名称", prop: "customerName" },
        { label: "所属网格", prop: "belongGridName" },
        { label: "审批部门", prop: "moveJudgeDeptName" },
        { label: "移交人姓名", prop: "moveOutManagerName" },
        { label: "移交人工号", prop: "moveOutManagerCode" },
        { label: "移交人所在机构", prop: "moveOutOrgName", width: 150 },
        { label: "接收人姓名", prop: "moveInManagerName" },
        { label: "接收人工号", prop: "moveInManagerCode" },
        { label: "接收人所在机构", prop: "moveInOrgName", width: 150 },
        {
          label: "审批状态", prop: "moveStatus", formatter: ({ moveStatus }) => {
            return this.dict.label.approval_status_type[moveStatus]
          }
        },
        { label: "操作", slot: true },
      ],
      tableBtn: [
        {
          cname: "审批",
          ename: "approvalHandle",
          isShow: ({ moveStatus }) => moveStatus == "0" && this.activeIndex == 1,
        },
        {
          cname: "详情",
          ename: "detailHandle",
          isShow: ({ moveStatus }) => moveStatus != "0" || this.activeIndex != 1,
        },
      ],
      showDialog: false,
      showBatch: false,
      status: '1',
      editInfo: {},
      tableData: [],
    };
  },
  mounted() {
    this.fetchList()
  },
  methods: {
    fetchList() {
      const { pageNum, pageSize } = this.formInline
      this.loading = true
      this.$refs.filterBar.getForm(formInfo => {
        const param = {
          ...formInfo,
          pageNum,
          pageSize,
          pageType: this.activeIndex
        }
        service.handoverPageList(param).then(res => {
          const { rows, total } = res
          this.tableData = rows
          this.total = total
          this.loading = false
        }).catch((error) => {
          if (error) {
            this.tableData = []
            this.total = 0
          }
          this.loading = false
        })
      })
    },
    selectable(row) {
      const { moveStatus } = row
      return moveStatus == "0"
    },
    handleClick() {
      this.resetHandle()
    },
    approvalHandle(row) {
      this.showDialog = true
      this.status = '1'
      this.editInfo = row
    },
    detailHandle(row) {
      this.showDialog = true
      this.status = '2'
      this.editInfo = row
    },
    batchApproval() {
      this.showBatch = true
    }
  },
};
</script>

<style lang="scss" scoped>
.tabs {
  display: flex;
  align-items: center;
  width: fit-content;
  border: 1px solid #666;
  border-radius: 4px;
  margin: 20px 0 0 20px;
  overflow: hidden;
  .tab {
    padding: 12px 16px;
    border-right: 1px solid #666;
    &:last-child {
      border: none;
    }
    &.active {
      background-color: #409eff;
      color: #fff;
    }
  }
}
</style>
