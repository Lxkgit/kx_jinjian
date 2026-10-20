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
               :maxHeight="maxHeight" :pageSize="formInline.pageSize" @currentChange="currentChange"
               @sizeChange="sizeChange">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type)=>clickHandle(type,scope.row)">
          </i-table-btns>
        </template>
      </i-table>
    </PageContent>
    <businessAssign v-model="showDialog"></businessAssign>
  </div>
</template>

<script>
import pageMixin from '@/mixins/pageMixin';
import optionMixin from "@/mixins/optionMixin";
import businessAssign from '../components/businessAssign'
export default ({
  mixins: [pageMixin, optionMixin],
  components: {
    businessAssign
  },
  data() {
    return {
      searchColumns: [
        {
          label: '客户名称',
          key: 'modelType',
          type: 'text',
        },
        {
          label: "所属网格",
          key: "belongGridCodes",
          value: "",
          type: "cascader",
          clearable: true,
          filterable: true,
          collapseTags: true,
          props: {
            value: "code",
            label: "name",
            emitPath: false,
            multiple: true,
            lazy: true,
            lazyLoad: this.lazyLoad,
          },
        },
        {
          label: "所属机构",
          key: "branchBanks",
          value: "",
          type: "cascader",
          filterable: true,
          collapseTags: true,
          props: {
            label: "label",
            value: "id",
            children: "children",
            emitPath: false,
            multiple: true,
          },
          clearable: true,
          options: this.deptTreeSelect,
        },
        {
          label: '商机类型',
          key: 'modelType1',
          type: 'select',
          options: () => []
        },
        {
          label: '业务扩展方向',
          key: 'modelType2',
          type: 'select',
          options: () => []
        },
        {
          label: '商机概览',
          key: 'modelType3',
          type: 'select',
          options: () => []
        },
        {
          label: '商机是否有效',
          key: 'modelType4',
          type: 'select',
          options: () => []
        },
      ],
      columns: [
        { label: '客户名称', prop: 'name1' },
        { label: '所属网格', prop: 'name2' },
        { label: '所属机构', prop: 'name3' },
        { label: '商机类型', prop: 'name6' },
        { label: '业务扩展方向', prop: 'name7' },
        { label: '商机概览', prop: 'name8' },
        { label: '发布时间', prop: 'name9' },
        { label: '结束时间', prop: 'name10' },
        { label: '反馈状态', prop: 'name12' },
        { label: '操作', slot: true, width: 150 }
      ],
      maxHeight: window.innerHeight - 320,
      tableData: [[]],
      tableBtn: [
        {
          cname: "商机详情",
          ename: "viewHandle",
        },
        {
          cname: "商机认领",
          ename: "claimHandle",
        },
        {
          cname: "商机分配",
          ename: "assignHandle",
        },
      ],
      showDialog: false,
    }
  },
  methods: {
    fetchList() { },
    viewHandle() { },
    claimHandle() {
      this.$modal.confirm('是否认领该商机?').then(res => {

      })
    },
    assignHandle() {
      this.showDialog = true
    },
  },
})
</script>

<style lang="scss" scoped>
</style>