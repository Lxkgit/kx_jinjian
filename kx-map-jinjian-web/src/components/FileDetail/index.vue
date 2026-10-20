<template>
  <div class="file-detail-wrapper">
    <!-- 基础信息 -->
    <div class="sub-title">客户基础信息</div>
    <customerSource v-show="firstColumns.length>0" v-model="sourceData" :gridColumns="2">
    </customerSource>
    <i-form v-if="firstColumns.length>0" :gridColumns="2" ref="firstForm" :columns="firstColumns"
            label-width="140px" label-position="right" />
    <!-- 有效信息 -->
    <div class="sub-title">客户有效信息</div>
    <i-form v-if="secondColumns.length>0" :gridColumns="2" ref="secondForm" :columns="secondColumns"
            label-width="140px" label-position="right" />
    <div class="sub-title">建档人信息</div>
    <i-form v-if="columns.length>0" :gridColumns="2" ref="baseForm" :columns="columns"
            label-width="140px" label-position="right" />
  </div>
</template>

<script>
import service from './api';
import fileConversionMixin from '@/mixins/fileConversionMixin';
export default ({
  name: 'FileDetail',
  mixins: [fileConversionMixin],
  dicts: ['custom_type_three', 'filling_status_type', 'filling_source_type'],
  props: {
    customerModelType: {
      type: String
    },
    customerParam: {
      type: Object
    },
    modelId: {
      type: [String, Number]
    },
  },
  data() {
    return {
      sourceData: {
        customerSource: '',
        customerIntroducerIdentity: ''
      },
      firstColumns: [],
      secondColumns: [],
      activeStep: 0,
      columns: [
        {
          label: '建档人',
          key: 'filingManagerName',
          type: 'text',
          placeholder: ' ',
          disabled: true
        },
        {
          label: '建档机构',
          key: 'filingOrgName',
          type: 'text',
          placeholder: ' ',
          disabled: true
        },
        {
          label: '建档日期',
          key: 'filingDate',
          type: 'text',
          placeholder: ' ',
          disabled: true
        },
        {
          label: '建档状态',
          key: 'filingStatus',
          type: 'select',
          placeholder: ' ',
          options: () => this.dict.type.filling_status_type,
          disabled: true
        },
        {
          label: '建档完成度',
          key: 'filingProgress',
          type: 'text',
          placeholder: ' ',
          disabled: true
        },
        {
          label: '建档来源',
          key: 'filingSource',
          placeholder: ' ',
          options: () => this.dict.type.filling_source_type,
          type: 'select',
          disabled: true
        },
      ],
    }
  },
  computed: {

  },
  mounted() {
    this.modelType = '3'
    this.iterator = this.setFormData()
    this.iterator.next()
  },
  methods: {
    * setFormData() {
      yield this.getFilingModelInfoById()
      yield this.getFilingModelInfoById()
      yield this.getByModelIdAndIdentity()
    },
    getByModelIdAndIdentity() {
      const { identity, phone } = this.customerParam
      const param = {
        modelId: this.modelId,
        identity,
        phone
      }
      service.getByModelIdAndIdentity(param).then(res => {
        if (!res.data) {
          this.$modal.msgWarning('该客户未在该模版建档')
          return
        }
        const { data: { customerSource, customerIntroducerIdentity, filingMessageDetail: { jsonMessage, jsonFinish } } } = res
        this.sourceData.customerSource = customerSource
        this.sourceData.customerIntroducerIdentity = customerIntroducerIdentity
        jsonMessage && this.$refs.firstForm.setForm(JSON.parse(jsonMessage))
        jsonFinish && this.$refs.secondForm.setForm(JSON.parse(jsonFinish))
        this.$refs.baseForm.setForm(res.data)
      })
    },
    getFilingModelInfoById() {
      const param = {
        id: this.modelId,
        isFinishParam: this.activeStep
      }
      service.getFilingModelInfoById(param).then(res => {
        const { data: { fields } } = res
        if (this.activeStep === 0) {
          this.firstColumns = []
          this.activeStep = 1
          this.$nextTick(() => {
            this.firstColumns = this.dataConversion(fields)
          })
        } else {
          this.secondColumns = []
          this.$nextTick(() => {
            this.secondColumns = this.dataConversion(fields)
          })
        }

        this.iterator && this.iterator.next()
      })
    },
  },
})
</script>

<style lang="scss" scoped>
.file-detail-wrapper {
  .sub-title {
    padding: 20px;
    position: relative;
    font-size: 20px;
    margin-bottom: 10px;
  }
  ::v-deep .el-input.is-disabled .el-input__inner {
    background-color: #f0f2f5; /* 背景色 */
    color: #606266; /* 文本颜色 */
    cursor: not-allowed; /* 鼠标样式 */
    border-color: #e4e7ed; /* 边框颜色 */
  }
  ::v-deep .el-radio__input.is-disabled.is-checked .el-radio__inner::after {
    background-color: #606266;
  }
}
</style>