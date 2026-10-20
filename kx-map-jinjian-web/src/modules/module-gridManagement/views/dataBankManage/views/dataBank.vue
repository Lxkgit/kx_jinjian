<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar ref="filterBar" slot="filter" :columns="searchColumns" :gridColumns="4">
        <el-button type="primary" @click="searchHandle">搜索</el-button>
        <el-button @click="resetHandle">重置</el-button>
      </i-filter-bar>
      <!-- 按钮 -->
      <i-button-bar :btn-lists="btnLists" @click="clickHandle" slot="btn" ref="btnBar">
      </i-button-bar>
      <!-- 列表 -->
      <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
               :allColumns="columns" :total="total" :pageNum="formInline.pageNum"
               :maxHeight="maxHeight" :pageSize="formInline.pageSize" @currentChange="currentChange"
               @sizeChange="sizeChange" showIndex selection @selectionChange="selectionChange">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type)=>clickHandle(type,scope.row)"
                        :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>
    </PageContent>
    <addData v-model="showAdd"></addData>
  </div>
</template>

<script>
import pageMixin from '@/mixins/pageMixin';
import addData from '../components/addData'
import service from '../api'
import { download } from '@/utils/ruoyi'
export default ({
  mixins: [pageMixin],
  components: {
    addData
  },
  data() {
    return {
      searchColumns: [
        {
          label: '标准化资料名称',
          key: 'fileName',
          type: 'text',
        },
      ],
      btnLists: [
        {
          cname: '新增标准化资料',
          ename: 'addHandle',
          type: 'primary',
        },
        {
          cname: '批量删除',
          ename: 'deleteBatchHandle',
          disabled: () => this.multiSelected.length === 0
        },
      ],
      columns: [
        { label: '资料名称', prop: 'fileName' },
        { label: '创建时间', prop: 'createTime' },
        { label: '创建人', prop: 'createBy' },
        { label: '下载次数', prop: 'downNum' },
        { label: '操作', slot: true },
      ],
      tableData: [[]],
      tableBtn: [
        {
          cname: '删除',
          ename: 'deleteHandle',
        },
        {
          cname: '查看',
          ename: 'viewHandle',
        },
        {
          cname: '下载',
          ename: 'downloadHandle',
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
        service.standardList(param).then(res => {
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
    deleteHandle(row) {
      const param = {
        ids: [row.id]
      }
      this.$modal.confirm('该操作将永久删除该数据, 是否确认?').then(() => {
        service.standardDel(param).then(res => {
          this.$modal.msgSuccess('删除成功!')
          this.fetchList()
        })
      }).catch(() => {

      })
    },
    deleteBatchHandle() {
      const param = {
        ids: this.multiSelected.map(item => item.id)
      }
      this.$modal.confirm('该操作将永久删除所选数据, 是否确认?').then(() => {
        service.standardDel(param).then(res => {
          this.$modal.msgSuccess('删除成功!')
          this.fetchList()
        })
      }).catch(() => {

      })
    },
    viewHandle(row) {
      const { id } = row
      service.standardPreview({ id }).then(res => {
        const { data } = res
        window.open(data)
      })
    },
    downloadHandle(row) {
      const { id, fileUrl } = row
      this.download('system/standard/data/down/file', { id }, this.imageName(fileUrl))
    },
    imageName(imageURL) {
      return imageURL.substring(imageURL.lastIndexOf('/') + 1)
    },
  },
})
</script>

<style lang="scss" scoped>
</style>