<template>
  <IDialog :title="title" v-model="showDialog" @handleClose="handleClose"
           @handleConfirm="handleConfirm">
    <el-form :model="formData" :rules="rules" ref="formData" label-width="100px">
      <el-form-item label="已选择客户">
        <el-input disabled v-model="formData.customerNames"></el-input>
      </el-form-item>
      <el-form-item label="调动类型" v-if="status === '2'">
        <el-radio-group v-model="formData.commonPoolFlag">
          <el-radio :label="1">调回公共池</el-radio>
          <el-radio :label="0">网格调动</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item prop="dutyDeptId" label="责任机构" v-if="formData.commonPoolFlag === 1">
        <el-cascader class="form-item" v-model="formData.dutyDeptId" :options="orgOptions"
                     :props="orgProps"></el-cascader>
      </el-form-item>
      <el-form-item prop="belongGrid" label="所属网格" v-if="formData.commonPoolFlag === 0">
        <el-cascader class="form-item" v-model="formData.belongGrid"
                     :props="gridProps"></el-cascader>
      </el-form-item>
    </el-form>
  </IDialog>
</template>

<script>
import dialogMixin from '@/mixins/dialogMixin.vue';
import commonService from '@/modules/module-gridManagement/api';
import service from '../api/customer';
const EDIT_TYPE = {
  1: '分配',
  2: '调动'
}
export default ({
  mixins: [dialogMixin],
  props: {
    status: {
      type: String,
      default: '1'
    },
    editInfo: {
      type: Array,
      default: () => []
    },
  },
  data() {
    return {
      formData: {
        customerNames: '',
        commonPoolFlag: 0,
        dutyDeptId: null,
        belongGrid: null,
        customerIds: [],
      },
      rules: {
        dutyDeptId: [{ required: true, message: '责任机构不能为空' }],
        belongGrid: [{ required: true, message: '所属网格不能为空' }],
      },
      orgOptions: [],
      orgProps: {
        label: 'label',
        value: 'id',
        children: 'children',
        checkStrictly: true,
        emitPath: false,
      },
      gridProps: {
        value: 'code',
        label: 'name',
        emitPath: false,
        lazy: true,
        checkStrictly: true,
        lazyLoad: this.lazyLoad
      },
    }
  },
  computed: {
    title() {
      return EDIT_TYPE[this.status]
    }
  },
  mounted() {
    this.deptTreeSelect()
  },
  methods: {
    initData() {
      if (this.status === '2') {
        this.formData.commonPoolFlag = 1
      } else {
        this.formData.commonPoolFlag = 0
      }
      this.formData.customerNames = this.editInfo.map(item => item.customerName).join(',')
      this.formData.customerIds = this.editInfo.map(item => item.id)
    },
    async deptTreeSelect() {
      const { data } = await commonService.deptTree()
      this.orgOptions = data
    },
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
    handleClose() {
      this.formData = {
        commonPoolFlag: 1,
        customerNames: '',
        dutyDeptId: null,
        belongGrid: null,
        customerIds: [],
      }
      this.$refs.formData.resetFields()
      this.showDialog = false
    },
    handleConfirm() {
      this.$refs.formData.validate((valid) => {
        if (valid) {
          delete this.formData.commonPoolFlag
          service.customerMobilize(this.formData).then(res => {
            this.$modal.msgSuccess(`${this.title}成功`)
            this.handleClose()
            this.$parent.fetchList()
          })
        }
      })
    },
  },
})
</script>

<style lang="scss" scoped>
.form-item {
  width: 100%;
}
</style>