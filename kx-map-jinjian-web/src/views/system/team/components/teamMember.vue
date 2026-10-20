<template>
  <i-dialog title="营销团队详情" v-model="showDialog" width="60%" @handleClose="handleClose"
            :showFooter="false">
    <!-- 过滤器 -->
    <i-filter-bar :columns="searchColumns" ref="filterBar" :gridColumns="3" label-width="120px">
      <!-- 搜索与重置按钮 -->
      <el-button type="primary" @click="searchHandle">搜索</el-button>
      <el-button @click="resetHandle">重置</el-button>
    </i-filter-bar>
    <!-- 数据列表 -->
    <i-table class="table-wrapper" ref="table" :loading="loading" :data="tableData" align="center"
             stripe :allColumns="columns" :total="total" :pageNum="formInline.pageNum"
             :pageSize="formInline.pageSize" @currentChange="currentChange"
             @sizeChange="sizeChange">
    </i-table>
  </i-dialog>
</template>

<script>
import dialogMixin from '@/mixins/dialogMixin.vue';
import pageMixin from "@/mixins/pageMixin.vue";
import service from '../api'
export default ({
  mixins: [dialogMixin, pageMixin],
  props: {
    editInfo: {
      type: Object
    }
  },
  data() {
    return {
      searchColumns: [
        {
          label: "员工姓名",
          key: "userName",
          type: "text",
          clearable: true,
        },
        {
          label: "员工号",
          key: "userId",
          type: "text",
          clearable: true,
        },
      ],
      columns: [
        { label: '员工号', prop: 'userId' },
        { label: '员工姓名', prop: 'userName' },
        { label: '联系电话', prop: 'phonenumber' },
        { label: '所属部门', prop: 'orgName' },
      ],
    }
  },
  methods: {
    initData() {
      this.$nextTick(() => {
        this.fetchList()
      })
    },
    fetchList() {
      this.$refs.filterBar.getForm(formInfo => {
        const { mktTeamId } = this.editInfo
        const param = {
          mktTeamId,
          ...formInfo,
          ...this.formInline,
        }
        this.loading = true
        service.teamManageMember(param).then(res => {
          const { rows, total } = res
          this.tableData = rows
          this.total = total
          this.loading = false
        }).catch(() => {
          this.loading = false
        })
      })
    },
    handleClose() {
      this.$refs.filterBar.reset()
      this.tableData = []
      this.showDialog = false
    },
  },
})
</script>

<style lang="scss" scoped>
.table-wrapper {
  margin-bottom: 32px;
}
</style>