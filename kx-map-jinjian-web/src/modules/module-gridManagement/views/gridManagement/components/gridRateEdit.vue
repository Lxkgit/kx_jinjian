<template>
  <IDialog :title="title" width="40%" v-model="showDialog" @handleClose="handleClose"
           @handleConfirm="handleConfirm">
    <i-form ref="myForm" :columns="columns" label-width="130px" />
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
  dicts: ['grid_rate_type'],
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
    return {
      columns: [
        {
          label: '派出所名称',
          key: 'policeName',
          type: 'text',
          rules: [
            { required: true, message: '请输入派出所名称', trigger: 'blur' }
          ],
        },
        {
          label: '联系人姓名',
          key: 'unFakerName',
          type: 'text',
          rules: [
            { required: true, message: '请输入联系人姓名', trigger: 'blur' }
          ],
        },
        {
          label: '联系电话',
          key: 'unFakerPhone',
          type: 'text',
          rules: [
            { required: true, message: '请输入联系电话', trigger: 'blur' }
          ],
        },
        {
          label: '派出所地址',
          key: 'policeAddress',
          type: 'text',
          rules: [
            { required: true, message: '请输入派出所地址', trigger: 'blur' }
          ],
        },
      ]
    }
  },
  computed: {
    title() {
      return `${TYPE[this.status]}派出所信息`
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
        service.saveGridRatingInfo(param).then(res => {
          this.$modal.msgSuccess(`${this.title}成功`)
          this.$parent.getGridRatingInfo()
          this.handleClose()
        })
      })
    },
  },
})
</script>

<style lang="scss" scoped>
</style>
