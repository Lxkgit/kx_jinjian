<template>
  <i-dialog title="批量移交" v-model="showDialog" width="30%" @handleClose="handleClose"
            @handleConfirm="handleConfirm">
    <i-form ref="myForm" :columns="searchColumns" label-position="right" label-width="140px">
    </i-form>
  </i-dialog>
</template>

<script>
import dialogMixin from '@/mixins/dialogMixin';
import service from '../api';
export default ({
  mixins: [dialogMixin],
  props: {
    selected: {
      type: Array,
    }
  },
  data() {
    return {
      searchColumns: [
        {
          label: '审批意见',
          key: 'moveStatus',
          type: 'radio',
          value: '1',
          options: [
            { label: '同意', value: '1' },
            { label: '拒绝', value: '2' },
          ]
        }
      ]
    }
  },
  methods: {
    handleClose() {
      this.$refs.myForm.reset()
      this.showDialog = false
    },
    handleConfirm() {
      this.$refs.myForm.getForm(formInfo => {
        const param = this.selected.map(item => {
          return {
            ...item,
            ...formInfo
          }
        })
        service.handoverJudgeBatch(param).then(res => {
          this.$modal.msgSuccess('审批成功')
          this.handleClose()
          this.$parent.fetchList()
        })
      })
    }
  },
})
</script>

<style lang="scss" scoped>
</style>