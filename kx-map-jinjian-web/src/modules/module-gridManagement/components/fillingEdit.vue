<template>
  <IDialog title="客户建档" v-model="showDialog" @handleClose="handleClose"
           @handleConfirm="handleConfirm">
    <i-form ref="myForm" :columns="columns" labelWidth="150px" labelPosition="right" />
  </IDialog>
</template>

<script>
import dialogMixin from '@/mixins/dialogMixin.vue';
import { customerColumns, companyColumns, merchantColumns } from '../config/fillingConfig';
import service from '../api/customer';
export default ({
  mixins: [dialogMixin],
  dicts: ['business_premises_type', 'marriage_status', 'sys_user_sex'],
  props: {
    clientType: {
      type: String,
      default: '1'
    },
    fillingInfo: {
      type: Object
    },
  },
  data() {
    return {

    }
  },
  computed: {
    columns() {
      return this.clientType === '1' ? customerColumns(this) : this.clientType === '2' ? companyColumns(this) : merchantColumns(this)
    }
  },
  methods: {
    initData() {
      this.getCustomerDetail()
    },
    getCustomerDetail() {
      const param = {
        id: this.fillingInfo.id
      }
      service.customerDetail(param).then(res => {
        const { data: { customerInfo } } = res
        if (this.clientType === '1') {
          customerInfo.customerSex = (customerInfo.customerSex + '')
          customerInfo.marriageFlag = (customerInfo.marriageFlag + '')
        } else if (this.clientType === '3') {
          customerInfo.businessPlaceType = (customerInfo.businessPlaceType + '')
        }

        this.$refs.myForm.setForm(customerInfo)
      })
    },
    handleClose() {
      this.$refs.myForm.reset()
      this.showDialog = false
    },
    handleConfirm() {
      this.$refs.myForm.getForm(formInfo => {
        console.log(formInfo)
        const param = {
          id: this.fillingInfo.id,
          ...formInfo
        }
        service.customerArchival(param).then(res => {
          this.$modal.msgSuccess('建档成功')
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