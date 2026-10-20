<template>
  <IDialog :title="title" width="40%" v-model="showDialog" @handleClose="handleClose"
           @handleConfirm="handleConfirm">
    <i-form ref="myForm" :columns="columns" label-width="80px" />
  </IDialog>
</template>

<script>
import dialogMixin from '@/mixins/dialogMixin.vue';
import service from '../api';
const TYPE = {
  'add': '新增',
  'edit': '编辑'
}
export default ({
  mixins: [dialogMixin],
  props: {
    status: {
      type: String,
      default: 'add'
    },
    arrCode: {
      type: String
    },
    editInfo: {
      type: Object
    },
    infoId: {
      type: Number
    }
  },
  data() {
    const checkPhone = (rule, value, callback) => {
      if (!/^1\d{10}$/.test(value)) {
        callback(new Error('请输入11位手机号'))
      } else {
        callback()
      }
    }
    const checkID = (rule, value, callback) => {
      if (value) {
        if (!/^\d{18}$/.test(value)) {
          callback(new Error('请输入18位身份证号'))
        } else {
          callback()
        }
      } else {
        callback()
      }

    }
    return {
      columns: [
        {
          label: '姓名',
          key: 'cruxName',
          value: '',
          type: 'text',
          rules: [
            { required: true, message: '请填写姓名' }
          ],
        },
        {
          label: '性别',
          key: 'cruxSex',
          value: '',
          type: 'radio',
          options: () => [
            { label: '男', value: '男' },
            { label: '女', value: '女' },
          ],
          rules: [
            { required: true, message: '请选择性别', trigger: 'change' }
          ],
        },
        {
          label: '手机号码',
          key: 'cruxPhone',
          value: '',
          type: 'text',
          rules: [
            { required: true, message: '请填写手机号码' },
            { validator: checkPhone },
          ],
        },
        {
          label: '证件号码',
          key: 'cruxIdentity',
          value: '',
          type: 'text',
          rules: [
            { validator: checkID }
          ]
        },
        {
          label: '地址',
          key: 'cruxAddress',
          value: '',
          type: 'text',
          rules: [
            { required: true, message: '请填写地址' }
          ],
          tip: '地址示例：**省**市**区/县**街道**号**',
        },
        {
          label: '职务',
          key: 'cruxWork',
          value: '',
          type: 'text',
          rules: [
            { required: true, message: '请填写职务' }
          ],
        },
      ]
    }
  },
  computed: {
    title() {
      return `${TYPE[this.status]}网格关键人`
    }
  },
  methods: {
    initData() {
      if (this.status === 'add') {
        return
      }
      this.$nextTick(() => {
        this.$refs.myForm.setForm(this.editInfo)
      })
    },
    handleClose() {
      this.$refs.myForm.reset()
      this.showDialog = false
    },
    handleConfirm() {
      this.$refs.myForm.getForm(formInfo => {
        let param = {
          ...formInfo,
          gridCode: this.arrCode,
          gridMessageId: this.infoId,
          id: this.editInfo?.id
        }
        service.saveGridCruxInfo(param).then(res => {
          this.$modal.msgSuccess(`${this.title}成功`)
          this.$parent.getGridCruxInfo()
          this.handleClose()
        })
      })
    },
  },
})
</script>

<style lang="scss" scoped>
</style>