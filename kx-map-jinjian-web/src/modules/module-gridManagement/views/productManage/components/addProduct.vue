<template>
  <IDialog title="新增产品" v-model="showDialog" @handleClose="handleClose"
           @handleConfirm="handleConfirm">
    <i-form ref="myForm" :columns="columns" label-width="100px">
    </i-form>
  </IDialog>
</template>

<script>
import dialogMixin from '@/mixins/dialogMixin.vue';
import service from '../api'
export default ({
  mixins: [dialogMixin],
  dicts: ['product_type'],
  data() {
    return {
      columns: [
        {
          label: '产品名称',
          key: 'productName',
          value: '',
          type: 'text',
        },
        {
          label: '产品类型',
          key: 'productType',
          value: '',
          type: 'select',
          options: () => this.dict.type.product_type,
        },
        {
          label: '业务链接',
          key: 'businessLink',
          value: '',
          type: 'text',
        },
      ],
    }
  },
  methods: {
    handleClose() {
      this.$refs.myForm.reset()
      this.showDialog = false
    },
    handleConfirm() {
      this.$refs.myForm.getForm(formInfo => {
        service.productSave(formInfo).then(res => {
          this.$modal.msgSuccess('新增成功')
          this.$parent.fetchList()
          this.handleClose()
        })
      })
    }
  },
})
</script>

<style lang="scss" scoped>
</style>