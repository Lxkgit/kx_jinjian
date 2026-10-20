
<script>
const TYPES = {
  1: "text",
  2: "textarea",
  3: "radio",
  4: "checkbox",
  5: "select",
  6: "date",
  7: "daterange",
  8: "imgupload",
  9: "labelselect",
  10: "checkin",
};
const trigger = {
  text: "blur",
  textarea: "blur",
  select: "change",
  radio: "change",
  checkbox: "change",
  daterange: "change",
  date: "change",
  labelselect: "change",
};
const rule_type = {
  text: "string",
  textarea: "string",
  select: "string",
  radio: "string",
  checkbox: "array",
  daterange: "array",
  date: "string",
  labelselect: "array",
};
const require_type = ["imgupload", "checkin"];
const require_key = ["customerPhone", "customerIdentity"];
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
export default {
  props: {
    customerType: {
      type: String,
    },
  },
  data() {
    return {
      modelType: null,
    };
  },
  computed: {
    // modelStatus() {
    //   return this.$store.getters["gridManagement/modelStatus"];
    // },
  },
  methods: {
    // 客户档案表单
    dataConversion(formList) {
      const res = [];
      if (formList.length > 0) {
        formList.forEach((item, index) => {
          !Array.isArray(item.content) &&
            item.content &&
            (item.content = item.content.replace(/[\,\，]/g, ","));
          let obj = {
            label: item.fieldName,
            key: `formItem${index}`,
            type: TYPES[item.fillingForm],
            value: item.detailContent || "",
            options: Array.isArray(item.content)
              ? item.content
              : (item.content &&
                  item.content.split(",").map((item, index) => ({
                    label: item,
                    value: index.toString(),
                  }))) ||
                [],
            disabled: this.isDisabled,
            // readonly: this.modelStatus === "view",
            tagType: this.customerType || this.modelType,
            rules: [],
            isMust: item.isRequired,
          };

          switch (item.fillingForm) {
            case "7":
            case "4":
            case "8":
            case "9":
              if (item.detailContent) {
                obj.value = JSON.parse(item.detailContent);
              } else {
                obj.value = [];
              }
              break;
            case '11':
              if (item.fieldContent) {
                obj.value = JSON.parse(item.fieldContent)
              } else {
                obj.value = {}
              }
            default:
              break;
          }
          // if (trigger[obj.type] && item.isRequired == "1") {
          //   const message = Array.isArray(obj.value)
          //     ? `请至少选择一个${obj.label}`
          //     : `请填写${obj.label}`;
          //   const type = rule_type[obj.type];
          //   obj.rules.push({
          //     required: true,
          //     type,
          //     message,
          //     trigger: trigger[obj.type],
          //   });
          // }
          // if (
          //   (item.isRequired == "1" && require_type.includes(obj.type)) ||
          //   (item.isRequired == "1" && require_key.includes(obj.key))
          // ) {
          //   obj.required = true;
          // }

          if (trigger[obj.type] && item.isRequired == '1' && !require_key.includes(obj.key)) {
            const message = Array.isArray(obj.value) ? `请至少选择一个${obj.label}` : `请填写${obj.label}`
            const type = rule_type[obj.type]
            obj.rules.push({ required: true, type, message, trigger: trigger[obj.type] })
          }
          if ((item.isRequired == '1' && require_type.includes(obj.type))) {
            obj.rules.push({ required: true, validator: RULE[obj.type], trigger: ['change', 'blur'] })
          }
          if ((item.isRequired == '1' && require_key.includes(obj.key))) {
            obj.required = true
          }
          res.push(obj);
        });
      }

      return res;
    },
  },
};
</script>

<style lang="scss" scoped></style>
