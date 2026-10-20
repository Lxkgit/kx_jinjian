<template>
    <IDialog title="新增事件" v-model="showDialog" @handleClose="handleClose"
             @handleConfirm="handleConfirm" :loading="loading">
      <i-form ref="myForm" :columns="columns" labelWidth="150px" labelPosition="right" />
    </IDialog>
  </template>
  
  <script>
  import dialogMixin from "@/mixins/dialogMixin.vue";
  const rules = {
    '投诉来源':[
        {required: true,message: "请输入投诉来源",trigger: ["change", "blur"],},
    ],
    '投诉人':[
        {required: true,message: "请输入投诉人",trigger: ["change", "blur"],},
    ],
    '投诉人联系电话':[
        {required: true,message: "请输入投诉人联系电话",trigger: ["change", "blur"],},
    ],
    '投诉时间':[
        {required: true,message: "请选择投诉时间",trigger: ["change", "blur"],},
    ],
    '线索来源':[
        {required: true,message: "请输入线索来源",trigger: ["change", "blur"],},
    ],
    '行业':[
        {required: true,message: "请输入行业",trigger: ["change", "blur"],},
    ],
    '发生场景':[
        {required: true,message: "请输入发生场景",trigger: ["change", "blur"],},
    ],
    '是否为拒收人民币现金行为':[
        {required: true,message: "请选择是否为拒收人民币现金行为",trigger: ["change", "blur"],},
    ],
    '具体事由':[
        {required: true,message: "请输入具体事由",trigger: ["change", "blur"],},
    ],
    '处置情况':[
        {required: true,message: "请输入处置情况",trigger: ["change", "blur"],},
    ],
    '网点联络员':[
        {required: true,message: "请输入网点联络员",trigger: ["change", "blur"],},
    ],
    '联络员联系电话':[
        {required: true,message: "请输入联络员联系电话",trigger: ["change", "blur"],},
    ],
    '上级负责人':[
        {required: true,message: "请输入上级负责人",trigger: ["change", "blur"],},
    ],
    '上级负责人联系电话':[
        {required: true,message: "请输入上级负责人联系电话",trigger: ["change", "blur"],},
    ],
}
  export default {
    mixins: [dialogMixin],
    props: {
      defaultValue: {
        type: Object,
        default: () => ({}),
      },
      type: {
        type: Number,
        default: 0,
      },
    },
    data() {
      return {
        columns: [
          {label: "投诉来源", key: "1", value: "",type: "text",rules:rules['投诉来源']},
          {label: "投诉人", key: "2", value: "",type: "text",rules:rules['投诉人']},
          {label: "投诉人联系电话", key: "3", value: "",type: "text",rules:rules['投诉人联系电话']},
          {label: "投诉时间", key: "4", value: "",type: "text",rules:rules['投诉时间']},
          {label: "线索来源", key: "15", value: "",type: "text",rules:rules['线索来源']},
          {label: "行业", key: "6", value: "",type: "text",rules:rules['行业']},
          {label: "发生场景", key: "7", value: "",type: "text",rules:rules['发生场景']},
          {label: "是否为拒收人民币现金行为", key: "8", value: "",type: "text",rules:rules['是否为拒收人民币现金行为']},
          {label: "具体事由", key: "9", value: "",type: "textarea",rules:rules['具体事由']},
          {label: "处置情况", key: "0", value: "",type: "textarea",rules:rules['处置情况']},
          {label: "网点联络员", key: "11", value: "",type: "text",rules:rules['网点联络员']},
          {label: "联络员联系电话", key: "12", value: "",type: "text",rules:rules['联络员联系电话']},
          {label: "上级负责人", key: "123", value: "",type: "text",rules:rules['上级负责人']},
          {label: "上级负责人联系电话", key: "234", value: "",type: "text",rules:rules['上级负责人联系电话']},
        ],
        loading: false
      };
    },
    methods: {
      initData() { },
      handleClose() {
        this.$refs.myForm.reset();
      },
      handleConfirm() {
        this.$refs.myForm.getForm((formInfo) => {
          
        });
      },
    },
  };
  </script>
  
  <style lang="scss" scoped></style>
  