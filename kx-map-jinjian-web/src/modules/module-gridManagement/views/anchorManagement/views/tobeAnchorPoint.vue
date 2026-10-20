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
               @currentChange="currentChange" @sizeChange="sizeChange" selection
               @selectionChange="selectionChange">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type)=>clickHandle(type,scope.row)"
                        :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>

    </PageContent>
    <!-- associateEdit -->
    <associateEdit v-model="showEdit" :editInfo="editInfo"></associateEdit>
  </div>
</template>

<script>
import service from '../api';
import associateEdit from '../components/associateEdit';
import pageMixin from '@/mixins/pageMixin';
export default ({
  mixins: [pageMixin],
  dicts: ['anchor_type'],
  components: {
    associateEdit
  },
  data() {
    return {
      searchColumns: [
        {
          label: '客户名称',
          key: 'customerName',
          type: 'text',
        },
        {
          label: '客户类型',
          key: 'customerType',
          type: 'select',
          options: () => this.dict.type.anchor_type
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
        // {
        //   cname: '批量锚点',
        //   ename: 'batchAnchor',
        //   type: 'primary',
        //   disabled: () => this.multiSelected.length === 0
        // },
        {
          cname: '批量关联',
          ename: 'batchAssociateAnchor',
          type: 'primary',
          disabled: () => this.multiSelected.length === 0
        },
      ],
      tableBtn: [
        {
          cname: '地图锚点',
          ename: 'anchorHandle',
        },
        {
          cname: '关联楼宇',
          ename: 'associateAnchor',
        },
      ],
      columns: [
        { label: '客户类型', prop: 'customerTypeName' },
        { label: '客户名称', prop: 'customerName' },
        { label: '客户内码', prop: 'customerInterNum' },
        { label: '地址', prop: 'customerAddress' },
        { label: '归属网格', prop: 'belongGridName' },
        { label: '关联楼宇', prop: 'buildingName' },
        { label: '操作', slot: true },
      ],
      showEdit: false,
      editInfo: null,
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
          isAnchor: 0,
          ...formInfo
        }
        this.loading = true
        service.anchorPointList(param).then(res => {
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
    anchorHandle(data) {
      this.$store.commit('gridManagement/SET_ANCHOR_INFO', data)
      this.$router.push({ name: 'AnchorEdit' })
    },
    associateAnchor(data) {
      this.editInfo = [data]
      this.showEdit = true
    },
    batchAssociateAnchor() {
      this.editInfo = this.multiSelected
      this.showEdit = true
    },
  },
})
</script>

<style lang="scss" scoped>
</style>