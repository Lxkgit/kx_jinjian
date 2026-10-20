<template>
  <div>
    <SplitPageContent :hasLeftbg="false">
      <template #operate>
        <div class="tree-wrapper">
          <el-select v-model="gridType" class="grid-select" placeholder="请选择网格体系"
                     @change="girdChange">
            <el-option v-for="item in gridOptions" :key="item.dictCode" :value="item.dictCode"
                       :label="item.dictLabel"></el-option>
          </el-select>
          <el-tree ref="tree" class="tree-content" :props="props" node-key="id"
                   :expand-on-click-node="false" :highlight-current="true" :load="loadNode" lazy
                   @node-click="nodeClickHandle">
          </el-tree>
        </div>
      </template>
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
               @sizeChange="sizeChange" selection @selectionChange="selectionChange">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type)=>clickHandle(type,scope.row)">
          </i-table-btns>
        </template>
      </i-table>
    </SplitPageContent>
    <!-- distributeGriderEdit -->
    <distributeGriderEdit v-model="showEdit" :selected="multiSelected" :deptId="deptId">
    </distributeGriderEdit>
  </div>

</template>

<script>
import distributeGriderEdit from '../components/distributeGriderEdit';
import pageMixin from '@/mixins/pageMixin';
import service from '@/modules/module-gridManagement/views/gridManagement/api';
export default ({
  name: 'GriderDistribute',
  mixins: [pageMixin],
  components: {
    distributeGriderEdit
  },
  data() {
    return {
      gridType: null,
      arrCode: null,
      gridOptions: [],
      node_had: undefined,
      resolve_had: undefined,
      props: {
        label: 'name',
        children: 'children',
        isLeaf: (data) => {
          return !data.isLeaf
        }
      },
      searchColumns: [
        {
          label: '责任机构',
          key: 'deptId',
          value: '',
          type: 'cascader',
          props: {
            label: 'label',
            value: 'id',
            children: 'children',
            emitPath: false,
          },
          filterable: true,
          options: this.deptTreeSelect
        },
        {
          label: '网格名称',
          key: 'name',
          type: 'text',
        },
        {
          label: '网格主管员',
          key: 'userName',
          type: 'text',
        },
      ],
      deptId: null,
      btnLists: [
        // {
        //   cname: '批量设置',
        //   ename: 'distributeAll',
        //   type: 'primary',
        //   disabled: () => this.multiSelected.length === 0,
        // },
      ],
      columns: [
        { label: '网格名称', prop: 'name' },
        { label: '网格等级', prop: 'addrLevel' },
        { label: '责任机构', prop: 'deptName' },
        {
          label: '网格主管员', prop: 'userName', formatter: ({ userName, userManagerNum }) => {
            return `${userName ?? ''}${userManagerNum ? `(${userManagerNum})` : ''}`
          }
        },
        {
          label: '网格副管员', prop: 'secondUserName', formatter: ({ secondUserName, secondManagerNum }) => {
            return `${secondUserName ?? ''}${secondManagerNum ? `(${secondManagerNum})` : ''}`
          }
        },
        { label: '操作', slot: true },
      ],
      tableBtn: [
        {
          cname: '设置网格员',
          ename: 'distribute',
          permi: 'gridManagement:griderDistribute:edit',
        }
      ],
      showEdit: false,
      userOptions: [],
      maxHeight: window.innerHeight - 280,
    }
  },
  mounted() {
    this.iterator = this.initFun()
    this.iterator.next()
  },
  methods: {
    * initFun() {
      yield this.gridTypeList()
      yield this.fetchList()
    },
    async deptTreeSelect() {
      const { data } = await service.deptTree()
      return data
    },
    gridTypeList() {
      const param = {
        pageNum: 1,
        pageSize: 100
      }
      service.gridTypeList(param).then(res => {
        const { rows } = res
        this.gridOptions = rows
        this.gridType = rows[0]?.dictCode
        this.girdChange()

      })
    },
    loadNode(node, resolve) {
      const { level, data } = node
      if (level === 0) {
        this.node_had = node
        this.resolve_had = resolve
      }
      if (this.gridType) {
        const param = {
          typeCode: this.gridType,
          parentCode: level > 0 ? data.code : null,
        }
        service.getPaAddrInfoTypeList(param).then(res => {
          const { data } = res
          if (level === 0) {
            this.arrCode = data[0].code
          }
          resolve(data)
          this.iterator.next()
        })
      } else {
        resolve([])
      }
    },
    girdChange() {
      this.node_had.childNodes = [];
      this.loadNode(this.node_had, this.resolve_had);
    },
    fetchList() {
      const { pageNum, pageSize } = this.formInline
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          typeCode: this.gridType,
          code: this.arrCode,
          ...formInfo
        }
        this.loading = true
        service.getAddrInfoTypeBottom(param).then(res => {
          const { total, rows } = res
          this.tableData = rows
          this.total = total
          this.loading = false
          this.multiSelected = []
        }).catch(err => {
          if (err) {
            this.tableData = []
            this.total = 0
          }
          this.multiSelected = []
          this.loading = false
        });
      })
    },
    nodeClickHandle(data) {
      this.arrCode = data.code
      this.resetHandle()
    },
    selectionChange(val) {
      this.multiSelected = val
    },
    distributeAll() {
      this.showEdit = true
    },
    async distribute(row) {
      this.multiSelected = [row]
      const { deptId } = row
      this.deptId = deptId
      // const param = {
      //   deptId
      // }
      // const { data } = await service.getPaAddrInfoTypeUser(param)
      // this.userOptions = data.map(item => {
      //   return {
      //     label: item.nickName,
      //     value: item.userId,
      //   }
      // })
      this.showEdit = true
    },
  },
})
</script>

<style lang="scss" scoped>
.grid-select {
  width: 100%;
  margin-bottom: 10px;
}
.tree-wrapper {
  overflow: hidden;
  width: 100%;
  height: calc(100vh - 180px);
  padding: 15px;
  box-shadow: 3px 0px 20px 1px #e7e6e6;
  .tree-content {
    width: 100%;
    height: calc(100% - 46px);
    overflow: auto;
    ::v-deep .el-tree-node > .el-tree-node_children {
      overflow: visible;
    }
  }
}
</style>