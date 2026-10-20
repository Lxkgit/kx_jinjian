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
               @sizeChange="sizeChange" showIndex selection @selectionChange="selectionChange">
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
  dicts: ['product_type'],
  data() {
    return {
      searchColumns: [
        {
          label: '产品名称',
          key: 'productName',
          type: 'text',
        },
        {
          label: '产品类型',
          key: 'productType',
          type: 'select',
          options: () => this.dict.type.product_type,
        }
      ],
      btnLists: [],
      columns: [
        { label: '产品名称', prop: 'productName' },
        {
          label: '产品类型', prop: 'productType', formatter: ({ productType }) => {
            return this.dict.label.product_type[productType]
          }
        },
        { label: '产品业务总量', prop: 'productBusTotal' },
        { label: '专属二维码扫描次数', prop: 'scanTotal' },
        { label: '专属二维码业务量', prop: 'scanBusTotal' },
        { label: '业务转化率', prop: 'busConversionRatio' },
        { label: '操作', slot: true },
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
        service.performList(param).then(res => {
          const { total, rows } = res
          this.tableData = rows
          this.tableData.forEach(v=>{
            v.busConversionRatio = `${v.busConversionRatio}%`
          })
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
      this.$router.push({ name: 'PerformanceDetail' })
    },
  },
})
</script>

<style lang="scss" scoped>
</style>