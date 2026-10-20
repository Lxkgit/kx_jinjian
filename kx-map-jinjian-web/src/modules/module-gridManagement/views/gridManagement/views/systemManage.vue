<template>
  <PageContent>
    <!-- 过滤器 -->
    <div class="filterBar-box">
        <i-filter-bar
        ref="filterBar"
        slot="filter"
        class="filterBar"
        :columns="searchColumns"
        :gridColumns="3"
      >
        </i-filter-bar>
        <div>
          <el-button type="primary" @click="searchHandle">搜索</el-button>
          <el-button @click="resetHandle">重置</el-button>
        </div>
      </div>
    <!-- 按钮 -->
    <!-- <i-button-bar :btn-lists="btnLists" @click="clickHandle" slot="btn" ref="btnBar"> -->
    <!-- </i-button-bar> -->
    <!-- 列表 -->
    <table-title  title="网格类型列表" class="table-title"></table-title>
    <div class="i-table">
      <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe border
             :allColumns="columns" :total="total" :pageNum="formInline.pageNum"
             :maxHeight="maxHeight" :pageSize="formInline.pageSize" @currentChange="currentChange"
             @sizeChange="sizeChange">
      <template #action="scope">
        <i-table-btns :btn-lists="tableBtn" @click="(type)=>clickHandle(type,scope.row)"
                      :data="scope.row">
        </i-table-btns>
      </template>
    </i-table>
    </div>
    
    <!-- systemEdit -->
    <systemEdit v-model="showEdit" :editInfo="editInfo" :status="status"></systemEdit>
  </PageContent>
</template>

<script>
import systemEdit from '../components/systemEdit';
import pageMixin from '@/mixins/pageMixin';
import service from '@/modules/module-gridManagement/views/gridManagement/api';
export default ({
  mixins: [pageMixin],
  components: {
    systemEdit
  },
  data() {
    return {
      searchColumns: [
        {
          label: '网格类型',
          key: 'dictLabel',
          type: 'text',
          placeholder: '请输入网格类型',
        },
      ],
      maxHeight: window.innerHeight - 338,
      btnLists: [
        // {
        //   cname: '新增',
        //   ename: 'addHandle',
        //   type: 'primary',
        // },
      ],
      tableBtn: [
        {
          cname: '详情',
          ename: 'viewHandle',
          permi: 'gridManagement:systemManage:view'
        },
        {
          cname: '网格管理',
          ename: 'manageHandle',
          permi: 'gridManagement:systemManage:edit'
        },
        // {
        //   cname: '编辑',
        //   ename: 'editHandle',
        // },
        // {
        //   cname: '删除',
        //   ename: 'deleteHandle',
        // },
      ],
      columns: [
        { label: '网格类型', prop: 'dictLabel' },
        { label: '说明', prop: 'remark', showOverFlowTooltip: true },
        { label: '创建人', prop: 'createBy' },
        { label: '创建时间', prop: 'createTime' },
        { label: '操作', slot: true },
      ],
      showEdit: false,
      status: 'add',
      editInfo: {},
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
        service.gridTypeList(param).then(res => {
          const { total, rows } = res
          this.tableData = rows
          this.total = total
          this.loading = false
        })
      })

    },
    addHandle() {
      this.status = 'add'
      this.showEdit = true
    },
    editHandle(data) {
      this.editInfo = data
      this.status = 'update'
      this.showEdit = true
    },
    deleteHandle(data) {
      const { dictCode } = data
      this.$modal.confirm('该操作将永久删除该数据, 是否确认?').then(() => {
        service.gridTypeDelete({ dictCode }).then(res => {
          this.$modal.msgSuccess('删除成功!')
          this.fetchList()
        })
      }).catch(() => {

      })
    },
    viewHandle(data) {
      const { dictCode } = data
      const param = {
        typeCode: dictCode
      }
      this.$modal.loading("正在初始化数据，请稍后...")
      service.initAddrInfoType(param).then(res => {
        this.$modal.closeLoading()
        this.$store.commit('gridManagement/SET_MAP_STATUS', '1')
        this.$store.commit('gridManagement/SET_SYSTEM_INFO', data)
        this.$router.push({ name: 'GirdEditManage' })
      }).catch(() => {
        this.$modal.closeLoading()
      })
    },
    manageHandle(data) {
      const { dictCode } = data
      const param = {
        typeCode: dictCode
      }
      this.$modal.loading("正在初始化数据，请稍后...")
      service.initAddrInfoType(param).then(res => {
        this.$modal.closeLoading()
        this.$store.commit('gridManagement/SET_MAP_STATUS', '0')
        this.$store.commit('gridManagement/SET_SYSTEM_INFO', data)
        this.$router.push({ name: 'GirdEditManage' })
      }).catch(() => {
        this.$modal.closeLoading()
      })

    },
  },
})
</script>

<style lang="scss" scoped>
.filterBar-box {
  background: #fff;
  padding: 16px;
  display: flex;
  height: 68px;
  justify-content: space-between;
  .filterBar {
    width: 65%;
  }
}
.table-title {
  padding: 16px;
  height: 68px;
  margin-top: 12px;
}
.i-table {
  height: calc(100vh - 64px - 56px - 68px - 68px);
  background: #fff;
  padding:0 16px;
}
</style>