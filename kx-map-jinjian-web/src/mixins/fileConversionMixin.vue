<script>
const TYPES = {
  1: 'text',
  2: 'textarea',
  3: 'radio',
  4: 'checkbox',
  5: 'select',
  6: 'date',
  7: 'daterange',
  8: 'switch',
  9: 'imgupload',
  10: 'labelselect',
  11: 'checkin'
}
const trigger = {
  'text': 'blur',
  'textarea': 'blur',
  'select': 'change',
  'radio': 'change',
  'checkbox': 'change',
  'daterange': 'change',
  'date': 'change',
  'labelselect': 'change',
}
const rule_type = {
  'text': 'string',
  'textarea': 'string',
  'select': 'string',
  'radio': 'string',
  'checkbox': 'array',
  'daterange': 'array',
  'date': 'string',
  'labelselect': 'array',
}
const require_type = ['imgupload', 'checkin']
const require_key = ['customerPhone', 'customerIdentity']
const imageRule = (rule, value, callback) => {
  if (!value || value.length === 0) {
    return callback(new Error('图片不能为空'))
  } else {
    return callback()
  }
}
const checkRule = (rule, value, callback) => {
  if (!value || value == '' || JSON.stringify(value) === '{}') {
    return callback(new Error('打卡不能为空'))
  } else {
    return callback()
  }
}
const RULE = {
  imgupload: imageRule,
  checkin: checkRule
}
export default ({
  props: {
    customerType: {
      type: String,
    },
  },
  data() {
    return {
      modelType: null,
    }
  },
  computed: {
    modelStatus() {
      return this.$store.getters['gridManagement/modelStatus']
    },
  },
  methods: {
    // 客户档案表单
    dataConversion(formList) {
      const res = []
      if (formList.length > 0) {
        formList.forEach(item => {
          if (item.englishName === 'customerSource') {
            return
          }
          (!Array.isArray(item.content) && item.content) && (item.content = item.content.replace(/[\,\，]/g, ','))
          // const placeholder = require_key.includes(item.englishName) ? '证件号和手机号至少填写一项' : null
          let obj = {
            label: item.chineseName,
            key: item.englishName,
            type: TYPES[item.fieldType],
            value: item.fieldContent || '',
            options: Array.isArray(item.content) ? item.content : item.content && item.content.split(',').map((item, index) => ({ label: item, value: index.toString() })) || [],
            disabled: this.modelStatus === 'view',
            readonly: this.modelStatus === 'view',
            tagType: this.customerType || this.modelType,
            rules: [],
            isMust: item.isMust,
            // placeholder,
          }

          switch (item.fieldType) {
            case '7':
            case '4':
            case '9':
            case '10':
              if (item.fieldContent) {
                obj.value = JSON.parse(item.fieldContent)
              } else {
                obj.value = []
              }
              break
            case '11':
              if (item.fieldContent) {
                obj.value = JSON.parse(item.fieldContent)
              } else {
                obj.value = {}
              }
            default:
              break
          }
          if (trigger[obj.type] && item.isMust == '1' && !require_key.includes(obj.key)) {
            const message = Array.isArray(obj.value) ? `请至少选择一个${obj.label}` : `请填写${obj.label}`
            const type = rule_type[obj.type]
            obj.rules.push({ required: true, type, message, trigger: trigger[obj.type] })
          }
          if ((item.isMust == '1' && require_type.includes(obj.type))) {
            obj.rules.push({ required: true, validator: RULE[obj.type], trigger: ['change', 'blur'] })
          }
          if ((item.isMust == '1' && require_key.includes(obj.key))) {
            obj.required = true
          }
          res.push(obj)
        })
      }
      return res
    }
  },
})
</script>

<style lang="scss" scoped>
</style>