<template>
  <IDialog :title="title" v-model="showDialog" @handleClose="handleClose"
           @handleConfirm="handleConfirm">
    <i-form ref="myForm" :columns="columns" label-width="80px" />
  </IDialog>
</template>

<script>
import service from '../api';
import dialogMixin from '@/mixins/dialogMixin.vue';
const TITLE_TYPE = {
  add: '新增',
  update: '编辑'
}
export default ({
  mixins: [dialogMixin],
  props: {
    status: {
      type: String,
      default: 'add'
    },
    editInfo: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      columns: [
        {
          label: '标签分组',
          key: 'labelGroupName',
          value: '',
          type: 'text',
          disabled: true,
        },
        {
          label: '标签名称',
          key: 'labelName',
          value: '',
          type: 'text',
          rules: [
            { required: true, message: '标签名称不能为空' }
          ]
        },
      ]
    }
  },
  computed: {
    title() {
      return TITLE_TYPE[this.status]
    }
  },
  methods: {
    initData() {
      this.$nextTick(() => {
        this.$refs.myForm.setForm(this.editInfo)
      })
    },
    handleClose() {
      this.$refs.myForm.reset()
      this.showDialog = false
    },
    handleConfirm() {
      this.$refs.myForm.getForm(async formInfo => {
        try {
          let param = {}
          if (this.status === 'add') {
            param = {
              ...formInfo,
              labelGroupId: this.editInfo.id
            }
          } else {
            param = {
              ...formInfo,
              id: this.editInfo.labelId,
              labelGroupId: this.editInfo.id,
            }
          }
          const res = await service.labelAddOrEdit(param)
          this.$message.success(`${TITLE_TYPE[this.status]}成功!`);
          this.handleClose()
          this.$parent.fetchList()
        } catch {

        }
      })
    }
  },
})
</script>

<style lang="scss" scoped>
</style>