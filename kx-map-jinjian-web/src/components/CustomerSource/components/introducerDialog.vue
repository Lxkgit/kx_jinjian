<template>
  <i-dialog title="介绍人搜索" v-model="showDialog" width="50%" @handleClose="handleClose"
            @handleConfirm="handleConfirm" appendToBody>
    <i-filter-bar ref="filterBar" :columns="searchColumns" :gridColumns="2">
      <div>
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </div>
    </i-filter-bar>
    <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
             :allColumns="columns" selection @selectionChange="selectionChange"
             :showPagination="false" :emptyText="searched&&tableData.length==0?'请先将介绍人建档':'暂无数据'">
    </i-table>
  </i-dialog>
</template>

<script>
import service from '../api';
import dialogMixin from '@/mixins/dialogMixin';
import pageMixin from "@/mixins/pageMixin";
export default ({
  mixins: [dialogMixin, pageMixin],
  data() {
    return {
      searchColumns: [
        {
          label: "介绍人名称",
          key: "name",
          type: "text",
        },
        {
          label: "证件号",
          key: "identity",
          type: "text",
        },
        {
          label: "手机号",
          key: "phone",
          type: "text",
        },
      ],
      columns: [
        { label: "介绍人名称", prop: "name" },
        {
          label: "证件号", prop: "identity", formatter: ({ identity }) => {
            return this.$identityMasking(identity);
          },
        },
        {
          label: "手机号", prop: "phone", formatter: ({ phone }) => {
            return this.$phoneMasking(phone)
          },
        },
      ],
      searched: false
    }
  },
  methods: {
    initData() { },
    fetchList() {
      this.$refs.filterBar.getForm(formInfo => {
        service.queryCustomer(formInfo).then(res => {
          const { data } = res
          this.searched = true
          this.tableData = data
        })
      })
    },
    handleClose() {
      this.tableData = []
      this.showDialog = false
    },
    handleConfirm() {
      if (this.multiSelected.length === 0) {
        this.$modal.msgWarning('请选择介绍人，若介绍人不存在，请先为介绍人建档')
        return
      }
      this.$emit('selectedHandle', this.multiSelected)
      this.handleClose()
    },
    selectionChange(val) {
      if (val.length > 1) {
        this.$refs.table.$refs.elTable.clearSelection()
        this.$refs.table.$refs.elTable.toggleRowSelection(val.pop())
      } else {
        this.multiSelected = val.pop()
      }
    }
  },
})
</script>

<style lang="scss" scoped>
</style>