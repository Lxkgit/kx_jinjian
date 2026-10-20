<template>
  <div class="schedule-list-wrapper">
    <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe size="mini"
             :allColumns="columns" :showPagination="false" showIndex :maxHeight="maxHeight">
      <template #action="scope">

      </template>
    </i-table>
  </div>
</template>

<script>
import service from '../api';
export default ({
  data() {
    return {
      loading: false,
      tableData: [],
      maxHeight: '100%',
      columns: [
        { label: '事项类型', prop: 'matterTypeName' },
        { label: '事项说明', prop: 'matterContent' },
        { label: '状态', prop: 'statusName' },
        { label: '发起人', prop: 'promoterUserName' },
        { label: '发起时间', prop: 'promoterTime' },
        { label: '经办人', prop: 'operatorUserName' },
        // { label: '操作', slot: true },
      ]
    }
  },
  mounted() {
    this.fetchList()
  },
  methods: {
    fetchList() {
      this.loading = true
      service.toDoList().then(res => {
        const { rows } = res
        this.loading = false
        this.tableData = rows
      })
    },
  },
})
</script>

<style lang="scss" scoped>
.schedule-list-wrapper {
  height: 100%;
  ::v-deep .el-table th.el-table__cell {
    background: #e3e5e8;
  }
  ::v-deep .el-table .el-table__header-wrapper th,
  ::v-deep .el-table .el-table__body-wrapper td.el-table__cell {
    height: 40px;
  }
}
</style>