<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar :columns="searchColumns" ref="filterBar" slot="filter" :gridColumns="3"
                    label-width="120px">
        <!-- 搜索与重置按钮 -->
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
      <!-- 数据列表 -->
      <i-table ref="table" :loading="loading" :data="tableData" :max-height="maxHeight"
               align="center" stripe :allColumns="columns" :total="total"
               :pageNum="formInline.pageNum" :pageSize="formInline.pageSize"
               @currentChange="currentChange" @sizeChange="sizeChange">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type) => clickHandle(type, scope.row)"
                        :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>
    </PageContent>
    <teamMember v-model="showDialog" :editInfo="editInfo"></teamMember>
  </div>
</template>

<script>
import pageMixin from "@/mixins/pageMixin.vue";
import teamMember from './components/teamMember'
import service from './api'
export default ({
  mixins: [pageMixin],
  components: {
    teamMember
  },
  data() {
    return {
      searchColumns: [
        {
          label: "营销团队名称",
          key: "mktTeamName",
          type: "text",
          clearable: true,
        },
        {
          label: "工号查询成员",
          key: "teamUserId",
          type: "text",
          clearable: true,
        },
      ],
      columns: [
        { label: '营销团队名称', prop: 'mktTeamName' },
        { label: '单位', prop: 'orgName' },
        { label: '一级部门', prop: 'deptfirst' },
        { label: '二级部门', prop: 'deptsecond' },
        { label: '机构编号', prop: 'orgId' },
        { label: '团队长姓名', prop: 'teamLeader' },
        { label: '团队长工号', prop: 'teamLeaderId' },
        { label: '团队长联系电话', prop: 'leadTelephone' },
        { label: '团队成员数', prop: 'teamNum' },
        { label: '操作', slot: true },
      ],
      maxHeight: window.innerHeight - 270,
      tableData: [],
      tableBtn: [
        {
          cname: "详情",
          ename: "viewHandle",
          type: "text",
        },
      ],
      showDialog: false,
      editInfo: {}
    }
  },
  mounted() {
    this.fetchList()
  },
  methods: {
    fetchList() {
      this.$refs.filterBar.getForm(formInfo => {
        const param = {
          ...formInfo,
          ...this.formInline,
        }
        this.loading = true
        service.teamManage(param).then(res => {
          const { rows, total } = res
          this.tableData = rows
          this.total = total
          this.loading = false
        }).catch(() => {
          this.loading = false
        })
      })
    },
    viewHandle(val) {
      this.editInfo = val
      this.showDialog = true
    }
  },
})
</script>

<style lang="scss" scoped>
</style>