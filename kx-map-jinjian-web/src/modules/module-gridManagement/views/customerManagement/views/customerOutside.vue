<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-filter-bar ref="filterBar" slot="filter" :columns="searchColumns" :gridColumns="5">
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
    <!-- fillingEdit -->
    <fillingEdit v-model="showEdit" clientType="1" :fillingInfo="fillingInfo"></fillingEdit>
    <!-- distributeEdit -->
    <distributeEdit v-model="showDistribute" status="2" :editInfo="editInfo"></distributeEdit>
  </div>

</template>

<script>
import fillingMixin from '@/modules/module-gridManagement/mixins/fillingMixin';
import pageMixin from '@/mixins/pageMixin';
import customerMixin from '@/modules/module-gridManagement/mixins/customerMixin.vue';
const TYPE_STYLE = {
  customerType: 1,
  bankInterFlag: 0,
  commonPoolFlag: 0,
}
export default ({
  mixins: [pageMixin, fillingMixin, customerMixin],
  data() {
    return {
      searchColumns: [
        {
          label: '客户名称',
          key: 'customerName',
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
          cname: '客户调动',
          ename: 'distributeBatch',
          type: 'primary',
          disabled: () => this.multiSelected.length === 0
        },
      ],
      tableBtn: [
        {
          cname: '查看',
          ename: 'customerViewHandle',
        },
        {
          cname: '调动',
          ename: 'distributeHandle',
        },
        {
          cname: '建档',
          ename: 'buildHandle',
          isShow: 'isBuild'
        },
        {
          cname: '更新',
          ename: 'buildHandle',
          isShow: 'isUpdate'
        },
      ],
      columns: [
        { label: '客户名称', prop: 'customerName' },
        { label: '证件类型', prop: 'cardType' },
        { label: '证件号码', prop: 'cardNum' },
        { label: '联系方式', prop: 'contactPhone' },
        { label: '客户地址', prop: 'customerAddress', showOverFlowTooltip: true },
        { label: '所属网格', prop: 'belongGridDesc' },
        { label: '责任机构', prop: 'dutyDeptDesc' },
        { label: '网格员', prop: 'gridUserDesc' },
        { label: '管户人', prop: 'businessManageUserDesc' },
        {
          label: '建档状态', prop: 'filesStatus', formatter: ({ filesStatus }) => {
            return this.dict.label.files_status[filesStatus]
          }
        },
        { label: '操作', slot: true },
      ],
      TYPE_STYLE,
    }
  },
  mounted() {
    this.fetchList()
  },
  methods: {

  },
})
</script>

<style lang="scss" scoped>
</style>