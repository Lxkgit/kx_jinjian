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
    <!-- buildEdit -->
    <buildEdit v-model="showEdit" :status="status" :editInfo="editInfo"></buildEdit>
  </div>

</template>

<script>
import service from '../api';
import buildEdit from '../components/buildEdit';
import pageMixin from '@/mixins/pageMixin';
export default ({
  mixins: [pageMixin],
  dicts: ['aggregate_point_type'],
  components: {
    buildEdit
  },
  data() {
    return {
      searchColumns: [
        {
          label: '楼宇名称',
          key: 'buildingName',
          type: 'text',
        },
        {
          label: '楼宇类型',
          key: 'buildingType',
          type: 'select',
          options: () => this.dict.type.aggregate_point_type
        },
        {
          label: '归属网格',
          key: 'belongGrid',
          type: 'cascader',
          props: {
            value: 'code',
            label: 'name',
            emitPath: false,
            lazy: true,
            lazyLoad: this.lazyLoad
          }
        },
      ],
      btnLists: [
        {
          cname: '新增楼宇',
          ename: 'addHandle',
          type: 'primary',
        },
      ],
      tableBtn: [
        {
          cname: '查看',
          ename: 'viewHandle',
        },
        {
          cname: '编辑',
          ename: 'editHandle',
        },
        {
          cname: '删除',
          ename: 'deleteHandle',
        },
      ],
      columns: [
        { label: '楼宇类型', prop: 'buildingTypeName' },
        { label: '楼宇名称', prop: 'buildingName' },
        { label: '层数', prop: 'floorNum' },
        { label: '单元数', prop: 'unitNum' },
        { label: '地址', prop: 'buildingAddress' },
        {
          label: '经纬度', prop: 'lonlat', formatter: ({ lon, lat }) => {
            return `${lon},${lat}`
          }
        },
        { label: '归属网格', prop: 'belongGridName' },
        { label: '操作', slot: true },
      ],
      showEdit: false,
      editInfo: {},
      status: 'add',
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
        service.buildingAnchorPointList(param).then(res => {
          const { total, rows } = res
          this.tableData = rows
          this.total = total
          this.loading = false
          this.multiSelected = []
        }).catch((error) => {
          if (error) {
            this.tableData = []
            this.total = 0
          }
          this.multiSelected = [];
          this.loading = false
        })
      })
    },
    lazyLoad(node, resolve) {
      const { value } = node
      const param = {
        parentCode: value
      }
      service.getPaAddrInfoList(param).then(res => {
        const { data } = res
        data.forEach(item => {
          item.leaf = !item.isLeaf
        })
        resolve(data)
      })
    },
    addHandle() {
      this.status = 'add'
      this.showEdit = true
    },
    editHandle(data) {
      this.editInfo = data
      this.status = 'edit'
      this.showEdit = true
    },
    viewHandle() {
      this.$router.push({ name: 'BuildAnchorDetail' })
    },
    deleteHandle(data) {
      const { id } = data
      this.$modal.confirm('该操作将永久删除该楼宇, 是否确认?').then(() => {
        service.buildingAnchorPointDelete({ id }).then(() => {
          this.$modal.msgSuccess('删除成功')
          this.searchHandle()
        })
      }).catch(() => {

      })
    },
  },
})
</script>

<style lang="scss" scoped>
</style>