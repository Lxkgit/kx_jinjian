<template>
  <IDialog title="新增客户" v-model="showDialog" width="60%" @handleClose="handleClose"
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
import { customerColumns, companyColumns, merchantColumns } from '../config';
import service from '../api';
import customerService from '@/modules/module-gridManagement/api/customer.js';
export default ({
  mixins: [dialogMixin],
  props: {
    editInfo: {
      type: Object,
    },
    customerType: {
      type: [String, Number],
    },
  },
  data() {
    return {
      searchColumns: [
        {
          label: '客户名称',
          key: 'customerName',
          type: 'text',
        },
        {
          label: '客户内码',
          key: 'nm',
          type: 'text',
        },
      ],
      loading: false,
      tableData: [],
      total: 0,
      formInline: {
        pageNum: 1,
        pageSize: 10,
      },
      multipleSelections: [],
    }
  },
  computed: {
    columns() {
      return this.customerType === '0' ? customerColumns(this) : this.customerType === '1' ? companyColumns(this) : merchantColumns(this)
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
        this.$modal.msgError('客户未选择')
        return
      }
      const param = {
        groupId: this.editInfo.id,
        ids: this.multipleSelections.map(item => item.id)
      }
      service.addMember(param).then(res => {
        this.$modal.msgSuccess('客户新增成功')
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
          ...formInfo,
          customerType: this.customerType,
          commonPoolFlag: 0,
        }
        this.loading = true
        customerService.customerList(param).then(res => {
          const { total, rows } = res
          this.tableData = rows
          this.total = total
          this.loading = false
          this.multipleSelections = []
        }).catch((error) => {
          if (error) {
            this.tableData = []
            this.total = 0
          }
          this.multipleSelections = []
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
      this.multipleSelections = val
    },
  },
})
</script>

<style lang="scss" scoped>
</style>