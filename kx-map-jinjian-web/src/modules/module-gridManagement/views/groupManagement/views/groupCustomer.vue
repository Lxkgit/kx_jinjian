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
        <template #import>
          <el-upload class="upload-demo" :action="upload.action" :limit="1" :file-list="fileList"
                     :show-file-list="false" :on-success="successHandle">
            <el-button type="primary">导入客户</el-button>
          </el-upload>
        </template>
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
    <!-- addCustomer -->
    <addCustomer v-model="showAdd" :customerType="customerType" :editInfo="groupInfo">
    </addCustomer>
  </div>

</template>

<script>
import commonService from '@/modules/module-gridManagement/api';
import service from '../api';
import { customerSearchColumns, companySearchColumns, merchantSearchColumns } from '../config';
import { customerColumns, companyColumns, merchantColumns } from '../config';
import addCustomer from '../components/addCustomer';
const TYPE_MAP = {
  1: '0',
  2: '2',
  3: '1',
}
const PATH_TYPE = {
  0: 'CustomerDetail',
  2: 'MerchantDetail'
}
export default ({
  dicts: ['custom_type', 'customer_type'],
  components: {
    addCustomer
  },
  data() {
    return {
      maxHeight: window.innerHeight - 370,
      btnLists: [
        {
          cname: '新增客户',
          ename: 'addHandle',
          type: 'primary',
        },
        {
          cname: '导入客户',
          ename: 'import',
          type: 'primary',
          slot: true,
        },
        {
          cname: '下载模板',
          ename: 'downloadMode',
          type: 'primary',
          // isShow: false
        },
        {
          cname: '批量移除',
          ename: 'distributeBatch',
          type: 'primary',
          disabled: () => this.multiSelected.length === 0
        },
      ],
      tableBtn: [
        {
          cname: '查看',
          ename: 'viewHandle',
        },
        {
          cname: '移除',
          ename: 'deleteHandle',
        },
      ],
      loading: false,
      total: 0,
      tableData: [],
      formInline: {
        pageNum: 1,
        pageSize: 10,
      },
      multiSelected: [],
      deptOptions: [],
      upload: {
        action: '',
      },
      fileList: [],
      showAdd: false,
    }
  },
  computed: {
    groupInfo() {
      return this.$store.getters['gridManagement/groupInfo']
    },
    customerType() {
      return TYPE_MAP[this.groupInfo.customType]
    },
    searchColumns() {
      return this.customerType === '0' ? customerSearchColumns(this) : this.customerType === '1' ? companySearchColumns(this) : merchantSearchColumns(this)
    },
    columns() {
      return this.customerType === '0' ? customerColumns(this) : this.customerType === '1' ? companyColumns(this) : merchantColumns(this)
    }
  },
  mounted() {
    this.deptTreeSelect()
    this.fetchList()
  },
  methods: {
    lazyLoad(node, resolve) {
      const { value } = node
      const param = {
        parentCode: value
      }
      commonService.getPaAddrInfoList(param).then(res => {
        const { data } = res
        data.forEach(item => {
          item.leaf = !item.isLeaf
        })
        resolve(data)
      })
    },
    async deptTreeSelect() {
      const { data } = await commonService.deptTree()
      this.deptOptions = data
    },
    fetchList() {
      const { pageNum, pageSize } = this.formInline
      this.$refs.filterBar.getForm((formInfo) => {
        const param = {
          pageSize,
          pageNum,
          groupId: this.groupInfo.id,
          ...formInfo,
        }
        this.loading = true
        service.manageList(param).then(res => {
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
          this.multiSelected = []
          this.loading = false
        })
      })
    },

    searchHandle() {
      this.formInline.pageNum = 1
      this.formInline.pageSize = 10
      this.fetchList()
    },
    resetHandle() {
      this.formInline.pageNum = 1
      this.formInline.pageSize = 10
      this.$refs.filterBar.reset()
      this.fetchList()
    },
    clickHandle(type, data) {
      this[type].call(this, data)
    },
    currentChange(val) {
      this.formInline.pageNum = val
      this.fetchList()
    },
    sizeChange(val) {
      this.formInline.pageSize = val
      this.fetchList()
    },
    selectionChange(val) {
      this.multiSelected = val
    },
    /* 按钮操作 */
    addHandle() {
      this.showAdd = true
    },
    viewHandle(data) {
      const { customerType, id } = data
      const name = PATH_TYPE[customerType]
      this.$store.commit('gridManagement/SET_CUSTOMER_ID', id)
      name && this.$router.push({ name })

    },
    deleteHandle(data) {
      const param = {
        groupId: this.groupInfo.id,
        ids: [data.id]
      }
      this.$modal.confirm('该操作将移除该客户成员, 是否确认?').then(() => {
        service.removeMember(param).then(res => {
          this.$modal.msgSuccess('删除成功!')
          this.fetchList()
        }).catch(() => {
          this.$modal.msgWarning('删除失败!')
        })
      })
    },
    distributeBatch() {
      const param = {
        groupId: this.groupInfo.id,
        ids: this.multiSelected.map(item => item.id)
      }
      this.$modal.confirm('该操作将移除所选客户成员, 是否确认?').then(() => {
        service.removeMember(param).then(res => {
          this.$modal.msgSuccess('删除成功!')
          this.fetchList()
        })
      }).catch(() => {
        this.$modal.msgWarning('删除失败!')
      })
    },
    successHandle() { },
    downloadMode() { },
  },
})
</script>

<style lang="scss" scoped>
</style>