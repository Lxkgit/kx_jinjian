<script>
const TYPES = {
  'el-input': 'text',
  'textarea': 'textarea',
  'el-radio': 'radio',
  'el-checkbox': 'checkbox',
  'el-select': 'select',
  'date': 'date',
  'daterange': 'daterange',
  'el-switch': 'switch',
  'el-rate': 'rate',
}
export default ({
  data() {
    return {

    }
  },
  methods: {
    dataConversion(formList) {
      const res = []
      if (formList.length > 0) {
        formList.forEach(item => {
          let type = item.type ? item.type : item.tag
          // item.content = item.content.replace(/[\,\，]/g, ',')
          let obj = {
            label: item.label,
            key: item.vModel,
            type: TYPES[type],
            value: item.defaultValue || '',
            placeholder: item.placeholder,
            disabled: item.disabled,
            readonly: item.readonly,
            clearable: item.clearable,
            span: item.span,
          }
          switch (type) {
            case 'daterange':
            case 'el-checkbox':
              if (item.defaultValue) {
                obj.value = JSON.parse(item.defaultValue)
              } else {
                obj.value = []
              }
              res.push(obj)
              break
            default:
              res.push(obj)
              break
          }
        })
      }

      return res
    },
  },
})
</script>

<style lang="scss" scoped>
</style>