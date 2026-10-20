<template>
  <IDialog :title="title" v-model="showDialog" @handleClose="handleClose"  v-if="showDialog"
           @handleConfirm="handleConfirm">
    <i-form ref="myForm" :columns="columns" label-width="80px" />
  </IDialog>
</template>

<script>
import service from '@/modules/module-gridManagement/views/gridManagement/api';
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
          label: '网格类型',
          key: 'dictLabel',
          value: '',
          type: 'text',
          maxlength: 20,
        },
        {
          label: '说明',
          key: 'remark',
          value: '',
          type: 'textarea',
          maxlength: 200,
          showWordLimit: true
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
      if (this.status === 'add') {
        return
      }
      console.log(this.editInfo)
      this.$nextTick(() => {
        this.$refs.myForm.setForm(this.editInfo)
      })
    },
    handleClose() {
      this.$refs.myForm.reset()
      this.showDialog = false
    },
    handleConfirm() { 
      if(this.isLoading) return 
      this.isLoading = true
      this.$refs.myForm.getForm(async formInfo => {
        try {
          const res = this.status === 'add' ?
            await service.gridTypeAdd(formInfo) :
            (formInfo = Object.assign(this.editInfo, formInfo), await service.gridTypeEdit(formInfo))
          this.$message.success(`${TITLE_TYPE[this.status]}成功!`);
          this.handleClose()
          this.$parent.$parent.searchHandle()
          this.isLoading = false
        } catch {
          this.isLoading = false
        }
      })
    }
  },
})
</script>

<style lang="scss" scoped>
</style>