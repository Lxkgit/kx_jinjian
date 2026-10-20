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
    <!-- distributeEdit -->
    <distributeEdit v-model="showDistribute" :editInfo="editInfo"></distributeEdit>
  </div>

</template>

<script>
import pageMixin from '@/mixins/pageMixin';
import customerMixin from '@/modules/module-gridManagement/mixins/customerMixin.vue';
const TYPE_STYLE = {
  customerType: 3,
  commonPoolFlag: 1,
}
export default ({
  mixins: [pageMixin, customerMixin],
  data() {
    return {
      maxHeight: window.innerHeight - 370,
      searchColumns: [
        {
          label: '客户类型',
          key: 'customerCategory',
          type: 'select',
          options: () => this.dict.type.custom_type,
        },
        {
          label: '商户名称',
          key: 'customerName',
          type: 'text',
        },
        {
          label: '商户经营者',
          key: 'merchantManager',
          type: 'text',
        },
        {
          label: '统一社会信用代码',
          key: 'creditCode',
          type: 'text',
        },
        {
          label: '责任机构',
          key: 'dutyDeptId',
          value: '',
          type: 'cascader',
          props: {
            label: 'label',
            value: 'id',
            children: 'children',
            emitPath: false,
          },
          options: this.deptTreeSelect
        },
        {
          label: '所属网格',
          key: 'belongGrid',
          value: '',
          type: 'cascader',
          props: {
            value: 'code',
            label: 'name',
            emitPath: false,
            lazy: true,
            lazyLoad: this.lazyLoad
          }
        },
        {
          label: '建档状态',
          key: 'filesStatus',
          type: 'select',
          options: () => this.dict.type.files_status,
        }
      ],
      btnLists: [
        {
          cname: '客户分配',
          ename: 'distributeBatch',
          type: 'primary',
          disabled: () => this.multiSelected.length === 0
        },
      ],
      tableBtn: [
        {
          cname: '查看',
          ename: 'merchantViewHandle',
        },
        {
          cname: '分配',
          ename: 'distributeHandle',
        },
      ],
      columns: [
        { label: '商户名称', prop: 'customerName' },
        { label: '统一社会信用代码', prop: 'creditCode' },
        { label: '客户类型', prop: 'customerCategory' },
        { label: '商户经营者', prop: 'merchantManager' },
        { label: '联系方式', prop: 'contactPhone' },
        { label: '商户地址', prop: 'customerAddress' },
        { label: '所属网格', prop: 'belongGridDesc' },
        { label: '责任机构', prop: 'dutyDeptDesc' },
        { label: '网格员', prop: 'gridUserDesc' },
        { label: '管户人', prop: 'businessManageUserDesc' },
        { label: '操作', slot: true },
      ],
      TYPE_STYLE
    }
  },
  mounted() {
    this.fetchList()
  },
  methods: {
    viewHandle() {
      this.$router.push({ name: 'MerchantDetail' })
    }
  },
})
</script>

<style lang="scss" scoped>
</style>