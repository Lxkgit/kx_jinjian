<template>
  <IDialog title="关联楼宇" v-model="showDialog" width="60%" @handleClose="handleClose"
           @handleConfirm="handleConfirm">
    <i-filter-bar ref="filterBar" :columns="searchColumns" :gridColumns="4">
      <el-button type="primary" @click="searchHandle">搜索</el-button>
      <el-button @click="resetHandle">重置</el-button>
    </i-filter-bar>
    <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
             :allColumns="columns" :total="total" :pageNum="formInline.pageNum"
             :pageSize="formInline.pageSize" @currentChange="currentChange" @sizeChange="sizeChange"
             selection @selectionChange="selectionChange">
    </i-table>
  </IDialog>
</template>

<script>
import dialogMixin from '@/mixins/dialogMixin.vue';
import service from '../api';
export default ({
  mixins: [dialogMixin],
  dicts: ['aggregate_point_type'],
  props: {
    editInfo: {
      type: Array,
    }
  },
  data() {
    return {
      searchColumns: [
        {
          label: '楼宇类型',
          key: 'buildingType',
          type: 'select',
          options: () => this.dict.type.aggregate_point_type
        },
        {
          label: '楼宇名称',
          key: 'buildingName',
          type: 'text',
        },
      ],
      loading: false,
      tableData: [],
      columns: [
        { label: '楼宇类型', prop: 'buildingTypeName' },
        { label: '楼宇名称', prop: 'buildingName' },
        { label: '地址', prop: 'buildingAddress' },
        { label: '归属网格', prop: 'belongGridName' },
      ],
      total: 0,
      formInline: {
        pageNum: 1,
        pageSize: 10,
      },
      multipleSelections: [],
    }
  },
  methods: {
    initData() {
      this.$nextTick(() => {
        this.fetchList()
      })
    },
    handleClose() {
      this.$refs.filterBar.reset()
      this.tableData = []
      this.showDialog = false
    },
    handleConfirm() {
      if (this.multipleSelections.length === 0) {
        this.$modal.msgError('楼宇未选择')
        return
      }
      this.editInfo.forEach(item => {
        item.buildingId = this.multipleSelections.id
      })
      service.anchorPointEditBatch(this.editInfo).then(res => {
        this.$modal.msgSuccess('关联楼宇成功')
        this.$parent.fetchList()
        this.handleClose()
      })
    },
    fetchList() {
      const { pageNum, pageSize } = this.formInline
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          ...formInfo
        }
        this.loading = true
        service.buildingAnchorPointList(param).then(res => {
          const { total, rows } = res
          this.tableData = rows
          this.total = total
          this.loading = false
          this.multiSelected = []
        }).catch((error) => {
          if (error) {
            this.tableData = []
            this.total = 0
          }
          this.multiSelected = [];
          this.loading = false
        })
      })
    },
    searchHandle() {
      this.formInline.pageNum = 1
      this.formInline.pageSize = 10
      this.fetchList()
    },
    resetHandle() {
      this.formInline.pageNum = 1
      this.formInline.pageSize = 10
      this.$refs.filterBar.reset()
      this.fetchList()
    },
    currentChange(val) {
      this.formInline.pageNum = val
      this.fetchList()
    },
    sizeChange(val) {
      this.formInline.pageSize = val
      this.fetchList()
    },
    selectionChange(val) {
      if (val.length > 1) {
        this.$refs.table.$refs.elTable.clearSelection()
        this.$refs.table.$refs.elTable.toggleRowSelection(val.pop())
      } else {
        this.multipleSelections = val.pop()
      }
    },
  },
})
</script>

<style lang="scss" scoped>
</style>