<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar ref="filterBar" slot="filter" :columns="searchColumns" :gridColumns="4">
        <div v-show="searchColumns.length>0">
          <el-button type="primary" @click="searchHandle">搜索</el-button>
          <el-button @click="resetHandle">重置</el-button>
        </div>
      </i-filter-bar>
      <!-- 按钮 -->
      <i-button-bar :btn-lists="btnLists" @click="clickHandle" slot="btn" ref="btnBar">
      </i-button-bar>
      <!-- 列表 -->
      <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
               :maxHeight="maxHeight" :allColumns="columns" :total="total"
               :pageNum="formInline.pageNum" :pageSize="formInline.pageSize"
               @currentChange="currentChange" @sizeChange="sizeChange">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type)=>clickHandle(type,scope.row)"
                        :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>
    </PageContent>
    <addFile v-model="showDialog" :formList="formList" :customerType="customerType"
             :modelId="modelId"></addFile>
  </div>

</template>

<script>
import service from '../api';
import pageMixin from '@/mixins/pageMixin';
import fileConversionMixin from '@/mixins/fileConversionMixin';
import addFile from '../components/addFile';
import { download } from '@/utils/ruoyi';
export default ({
  mixins: [pageMixin, fileConversionMixin],
  components: {
    addFile
  },
  props: {
    customerType: {
      type: String,
    },
  },
  data() {
    return {
      btnLists: [
        {
          cname: '新增客户',
          ename: 'addHandle',
          type: 'primary',
        },
        {
          cname: '模版下载',
          ename: 'downloadHandle',

        },
      ],
      maxHeight: null,
      columns: [],
      searchColumns: [],
      formList: [],
      modelId: null,
      showDialog: false,
    }
  },
  mounted() {
    this.getCustomerProfileHeaders()
  },
  methods: {
    getCustomerProfileHeaders() {
      const param = {
        customType: this.customerType
      }
      service.getCustomerProfileHeaders(param).then(res => {
        const { data: { id, fields } } = res
        this.formList = fields
        this.modelId = id
        this.searchColumns = this.dataConversionForm(JSON.parse(JSON.stringify(fields)))
        this.columns = this.dataConversionColumns(JSON.parse(JSON.stringify(fields)))
        this.$nextTick(() => {
          this.fetchList()
        })
      })
    },
    fetchList() {
      const { pageNum, pageSize } = this.formInline
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          ...formInfo,
          modelId: this.modelId,
          pageNum,
          pageSize
        }
        service.listByMap(param).then(res => {
          const { rows, total } = res
          this.tableData = []
          this.tableData = rows.map(item => JSON.parse(item))
          this.total = total
        })
      })
    },
    addHandle() {
      this.showDialog = true
    },
    downloadHandle() {
      const param = {
        type: this.customerType
      }
      service.modelDownload(param).then((res) => {
        const { msg } = res
        download(msg)
      })
    },
  },
})
</script>

<style lang="scss" scoped>
</style>