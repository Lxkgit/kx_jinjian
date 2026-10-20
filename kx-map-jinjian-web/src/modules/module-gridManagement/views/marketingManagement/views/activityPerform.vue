<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar ref="filterBar" slot="filter" :columns="searchColumns" :gridColumns="4">
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
      <!-- 列表 -->
      <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
               :allColumns="columns" :total="total" :pageNum="formInline.pageNum"
               :pageSize="formInline.pageSize" @currentChange="currentChange"
               @sizeChange="sizeChange">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type)=>clickHandle(type,scope.row)"
                        :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>
    </PageContent>
  </div>
</template>

<script>
import pageMixin from '@/mixins/pageMixin';
import service from '../api'
export default ({
  mixins: [pageMixin],
  dicts: ['act_status'],
  data() {
    return {
      searchColumns: [
        {
          label: '营销活动名称',
          key: 'actName',
          type: 'text',
        },
        {
          label: '营销活动状态',
          key: 'actStatus',
          type: 'select',
          options: () => this.dict.type.act_status,
        }
      ],
      btnLists: [],
      columns: [
        { label: '活动名称', prop: 'actName' },
        {
          label: '活动状态', prop: 'actStatus', formatter: ({ actStatus }) => {
            return this.dict.label.act_status[actStatus]
          }
        },
        { label: '参与人数', prop: 'actParticipateNum' },
        { label: '扫码人数', prop: 'scanCodeNum' },
        { label: '信息收集人数', prop: 'infoCollectNum' },
        {
          label: '信息收集率', prop: 'infoCollectRate', formatter: ({ infoCollectRate }) => {
            return infoCollectRate && `${infoCollectRate}%`
          }
        },
        { label: '信息有效人数', prop: 'infoCollectEffectNum' },
        {
          label: '信息有效率', prop: 'infoCollectEffectRate', formatter: ({ infoCollectEffectRate }) => {
            return infoCollectEffectRate && `${infoCollectEffectRate}%`
          }
        },
        { label: '业务办理人数', prop: 'busProcNum' },
        {
          label: '业务办理率', prop: 'busProcRate', formatter: ({ busProcRate }) => {
            return busProcRate && `${busProcRate}%`
          }
        },
        { label: '操作', slot: true },
      ],
      tableData: [],
      tableBtn: [
        {
          cname: '查看详情',
          ename: 'viewHandle',
        },
      ],
      showAdd: false
    }
  },
  mounted() {
    this.fetchList()
  },
  methods: {
    fetchList() {
      const { pageNum, pageSize } = this.formInline
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          ...formInfo
        }
        this.loading = true
        service.activitiesPerList(param).then(res => {
          const { total, rows } = res
          this.tableData = rows
          this.total = total
          this.loading = false
        })
      })
    },
    addHandle() {
      this.showAdd = true
    },
    viewHandle(row) {
      this.$store.commit('gridManagement/SET_PROD_INFO', row)
      this.$router.push({ name: 'ActivityPerformDetail' })
    },
  },
})
</script>

<style lang="scss" scoped>
</style>