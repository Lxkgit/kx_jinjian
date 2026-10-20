<template>
  <div class="form-wrapper">
    <i-form
      ref="myForm"
      :columns="columns"
      label-width="150px"
      :grid-columns="2"
      @eventFun="formEventFun" />
  </div>
</template>

<script>
import { data } from './mock/generator'
import dataConversion from './mixins/dataConversion'
export default ({
  mixins: [dataConversion],
  data() {
    return {
      columns: [],
    }
  },
  mounted() {
    // this.columns = this.dataConversion(data)
    this.columns = [{"label":"多行234","key":"field102","type":"textarea","value":"","placeholder":"请输入多行234","disabled":false,"readonly":false,"span":24},{"label":"评分","key":"field103","type":"rate","value":"","disabled":false,"span":24},{"label":"单行文本","key":"field101","type":"text","value":"","placeholder":"请输入单行文本","disabled":false,"readonly":false,"clearable":true,"span":24},{"label":"手机号","key":"mobile","type":"text","value":"","placeholder":"请输入手机号","disabled":false,"readonly":false,"clearable":true,"span":24}]
  },
  methods: {
    formEventFun(evt) {
      const { key, val } = evt
      switch (key) {
        case 'autocomplete': {
          const { mapAnchorId } = val
          this.$refs.myForm.setForm({ mapAnchorId: mapAnchorId })
          break
        }
        default:
          break
      }
    },
  },
})
</script>

<style lang="scss" scoped>
.form-wrapper {
  padding: 0 20px;
}
</style>