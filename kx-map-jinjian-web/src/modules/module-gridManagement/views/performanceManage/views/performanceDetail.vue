<template>
  <PageContent>
    <SubTitle :title="prodInfo.productName"></SubTitle>
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
        { label: '客户经理名称', prop: 'managerName' },
        { label: '个人业务总量', prop: 'personBusTotal' },
        { label: '扫码总数', prop: 'scanTotal' },
        { label: '扫码办理总数', prop: 'scanBusTotal' },
        { label: '业务转化率', prop: 'busConversionRatio' },
      ],
      tableData: [],
      tableBtn: [
        {
          cname: '营销人员详情',
          ename: 'viewHandle',
        },
      ],
      showAdd: false
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
        productId: this.prodInfo.id
      }
      this.loading = true
      service.performDetail(param).then(res => {
        const { total, rows } = res
        this.tableData = rows
        this.tableData.forEach(v=>{
          v.busConversionRatio = `${v.busConversionRatio}%`
        })
        this.total = total
        this.loading = false
      })
    },
  },
})
</script>

<style lang="scss" scoped>
</style>