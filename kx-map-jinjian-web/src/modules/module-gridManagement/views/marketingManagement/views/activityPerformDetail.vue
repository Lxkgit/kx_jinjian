<template>
  <PageContent>
    <SubTitle :title="prodInfo.actName"></SubTitle>
    <!-- 列表 -->
    <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
             :allColumns="columns" :total="total" :pageNum="formInline.pageNum"
             :pageSize="formInline.pageSize" @currentChange="currentChange"
             @sizeChange="sizeChange">
    </i-table>
  </PageContent>
</template>

<script>
import pageMixin from '@/mixins/pageMixin';
import service from '../api'
export default ({
  mixins: [pageMixin],
  data() {
    return {
      searchColumns: [

      ],
      btnLists: [],
      columns: [
        { label: '姓名', prop: 'managerName' },
        { label: '工号', prop: 'managerNum' },
        { label: '所属机构', prop: 'deptName' },
        { label: '引导参与人数', prop: 'actParticipateNum' },
        { label: '引导扫码人数', prop: 'infoCollectNum' },
        { label: '引导信息填写人数', prop: 'infoCollectNum' },
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
        { label: '引导业务办理人数', prop: 'busProcNum' },
        {
          label: '业务办理率', prop: 'busProcRate', formatter: ({ busProcRate }) => {
            return busProcRate && `${busProcRate}%`
          }
        },
      ],
      tableData: [],
    }
  },
  computed: {
    prodInfo() {
      return this.$store.getters['gridManagement/prodInfo']
    }
  },
  mounted() {
    this.fetchList()
  },
  methods: {
    fetchList() {
      const param = {
        ...this.formInline,
        id: this.prodInfo.id
      }
      this.loading = true
      service.activitiesPerDetail(param).then(res => {
        const { total, rows } = res
        this.tableData = rows
        this.total = total
        this.loading = false
      })
    },
  },
})
</script>

<style lang="scss" scoped>
</style>