<template>
  <div>
    <PageContent>
      <!-- 按钮 -->
      <i-button-bar :btn-lists="btnLists" @click="clickHandle" slot="btn" ref="btnBar">
      </i-button-bar>
      <!-- 列表 -->
      <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe
               :allColumns="columns" :showPagination="false">
        <template #action="scope">
          <i-table-btns :btn-lists="tableBtn" @click="(type)=>clickHandle(type,scope.row)"
                        :data="scope.row">
          </i-table-btns>
        </template>
      </i-table>
    </PageContent>
    <modelView v-model="showDialog" :formList="formList" customerType="3"></modelView>
  </div>
</template>

<script>
import service from '../api';
import pageMixin from '@/mixins/pageMixin';
import modelView from '../components/modelView';
export default ({
  mixins: [pageMixin],
  dicts: ['table_model_type'],
  components: {
    modelView
  },
  data() {
    return {
      btnLists: [
        {
          cname: '新增模版',
          ename: 'addHandle',
          type: 'primary',
          permi: 'fillingManagement:formManage:add',
        },
      ],
      tableBtn: [
        {
          cname: '关闭',
          ename: 'closeHandle',
          permi: 'fillingManagement:formManage:switch',
          isShow: ({ status }) => status === '1'
        },
        {
          cname: '开启',
          ename: 'openHandle',
          permi: 'fillingManagement:formManage:switch',
          isShow: ({ status }) => status !== '1'
        },
        {
          cname: '编辑',
          ename: 'editHandle',
          permi: 'fillingManagement:formManage:edit',
          isShow: ({ editable }) => editable
        },
        {
          cname: '预览',
          ename: 'previewHandle',
          permi: 'fillingManagement:formManage:view',
        },
        {
          cname: '删除',
          ename: 'deleteHandle',
          permi: 'fillingManagement:formManage:delete',
          isShow: ({ editable }) => editable
        }
      ],
      columns: [
        { label: '模版名称', prop: 'modelName' },
        {
          label: '模版类型', prop: 'modelType', formatter: ({ modelType }) => {
            return this.dict.label.table_model_type[modelType]
          }
        },
        { label: '创建时间', prop: 'createTime' },
        {
          label: '状态', prop: 'status', formatter: ({ status }) => {
            return status == '0' ? '关闭' : '开启'
          }
        },
        { label: '创建人', prop: 'createBy', },
        { label: '操作', slot: true },
      ],
      showDialog: false,
      formList: [],
      modelType: null,
    }
  },
  mounted() {
    this.fetchList()
  },
  methods: {
    fetchList() {
      this.loading = true
      service.listModel().then(res => {
        const { data } = res
        this.loading = false
        this.tableData = data
      }).catch((error) => {
        if (error) {
          this.tableData = []
          this.total = 0
        }
        this.loading = false

      })
    },
    closeHandle(row) {
      const { id, status } = row
      this.$confirm(`是否${status == '0' ? '开启' : '关闭'}该模版?`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info",
      })
        .then(() => {
          return service.startFilingModelInfo({ id })
        })
        .then(() => {
          this.fetchList()
        })
        .catch(function () { });
    },
    modelSwitch(row) {
      const { id, status } = row
      this.$modal.confirm(`是否${status == '0' ? '开启' : '关闭'}该模版?`).then(() => {
        service.startFilingModelInfo({ id }).then(res => {
          this.$modal.msgSuccess(`${status == '0' ? '开启' : '关闭'}成功`)
          this.fetchList()
        })
      })
    },
    openHandle(row) {
      this.modelSwitch(row)
    },
    closeHandle(row) {
      this.modelSwitch(row)
    },
    addHandle() {
      this.$store.commit('gridManagement/SET_MODEL_STATUS', 'add')
      this.$router.push({ name: 'ModelEdit' })
    },
    editHandle(row) {
      const { id } = row
      this.$store.commit('gridManagement/SET_MODEL_STATUS', 'edit')
      this.$store.commit('gridManagement/SET_MODEL_ID', id)
      this.$router.push({ name: 'ModelEdit' })
    },
    deleteHandle(row) {
      const { id, status } = row
      if (status === '1') {
        this.$modal.msgWarning(`该模版已开启不能删除!`)
        return
      }
      this.$modal.confirm(`此操作将永久删除该条记录, 是否继续?`).then(() => {
        service.deleteFilingModelInfo(id).then(res => {
          this.$modal.msgSuccess(`删除成功`)
          this.fetchList()
        })
      })
    },
    previewHandle(row) {
      const { id, modelType } = row
      this.modelType = modelType
      this.$store.commit('gridManagement/SET_MODEL_ID', id)
      this.showDialog = true
    },
  },
})
</script>

<style lang="scss" scoped>
</style>