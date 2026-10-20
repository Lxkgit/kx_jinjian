<template>
  <PageContent>
    <SubTitle title="二维码情况"></SubTitle>
    <IndexView :indexData="indexData"></IndexView>
    <SubTitle title="扫码人员概况"></SubTitle>
    <!-- 列表 -->
    <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
             :allColumns="columns" :total="total" :pageNum="formInline.pageNum"
             :maxHeight="maxHeight" :pageSize="formInline.pageSize" @currentChange="currentChange"
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
      indexData: [
        { label: '扫码总人数', value: '0', unit: '人', type: 1, key: 'scanTotal' },
        { label: '办理业务总人数', value: '0', unit: '人', type: 1, key: 'scanBusTotal' },
        { label: '业务转化率', value: '0%', unit: '', type: 2, key: 'busConversionRatio' },
      ],
      columns: [
        { label: '扫码人', prop: 'scanPersonName' },
        {
          label: '扫码时间', prop: 'scanTime', formatter: ({ scanTime }) => {
            return this.parseTime(scanTime)
          }
        },
        { label: '手机号码', prop: 'scanPhone' },
        {
          label: '是否办理业务', prop: 'isBuinsess', formatter: ({ isBuinsess }) => {
            const type = {
              0: '是',
              1: '否'
            }
            return type[isBuinsess]
          }
        },
      ]
    }
  },
  computed: {
    productId() {
      return this.$store.getters['gridManagement/productId']
    }
  },
  mounted() {
    this.fetchList()
    this.productStatistic()
  },
  methods: {
    fetchList() {
      this.loading = true
      const param = {
        productId: this.productId,
        ...this.formInline
      }
      service.productStatisticList(param).then(res => {
        const { total, rows } = res
        this.tableData = rows
        this.total = total
        this.loading = false
      })
    },
    productStatistic() {
      const param = {
        productId: this.productId
      }
      service.productStatistic(param).then(res => {
        const { data } = res
        this.indexData.forEach(item => {
          item.value = data[item.key]
        })
      })
    }
  },
})
</script>

<style lang="scss" scoped>
</style>