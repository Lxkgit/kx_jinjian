<template>
  <IDialog
    title="线索研判录入"
    v-model="showDialog"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :loading="loading"
    :center="true"
  >
    <i-form
      ref="myForm"
      :columns="columns"
      labelWidth="120px"
      labelPosition="right"
    />
  </IDialog>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin.vue";

// 字段校验规则
const rules = {
  analysisTime: [
    { required: true, message: "请选择研判时间", trigger: ["change", "blur"] },
  ],
  analysisDept: [
    { required: true, message: "请输入研判部门", trigger: ["change", "blur"] },
  ],
  participants: [
    { required: true, message: "请输入参会人员", trigger: ["change", "blur"] },
  ],
  analysisResult: [
    { required: true, message: "请输入研判结果", trigger: ["change", "blur"] },
  ],
};

export default {
  mixins: [dialogMixin],
  props: {
    defaultValue: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {
      columns: [
        {
          label: "研判时间",
          key: "analysisTime",
          value: new Date().toISOString().slice(0, 19).replace("T", " "),
          type: "datetime",
          format: "yyyy-MM-dd HH:mm:ss",
          valueFormat: "yyyy-MM-dd HH:mm:ss",
          rules: rules.analysisTime,
        },
        {
          label: "研判部门",
          key: "analysisDept",
          value: "",
          type: "text",
          rules: rules.analysisDept,
          placeholder: "请输入研判部门",
        },
        {
          label: "参会人员",
          key: "participants",
          value: "",
          type: "text",
          rules: rules.participants,
          placeholder: "请输入参会人员",
        },
        {
          label: "研判结果",
          key: "analysisResult",
          value: "",
          type: "textarea",
          rules: rules.analysisResult,
          placeholder: "请输入研判结果",
        },
        {
          label: "上传附件",
          key: "attachment",
          value: "",
          type: "imgupload",
          action: "/api/upload",
          placeholder: "选择文件",
        },
      ],
      loading: false,
    };
  },
  methods: {
    // 初始化数据
    initData() {
      if (
        this.isEdit &&
        this.defaultValue &&
        Object.keys(this.defaultValue).length > 0
      ) {
        // 编辑模式：回显数据
        this.$nextTick(() => {
          this.$refs.myForm.setForm(this.defaultValue);
        });
      } else {
        // 新增模式：重置表单
        this.$refs.myForm?.reset();
      }
    },

    // 关闭弹窗
    handleClose() {
      this.showDialog = false;
      this.$refs.myForm?.reset();
    },

    // 确定提交
    async handleConfirm() {
      try {
        // 获取表单数据并校验
        // const [valid, formInfo] = await this.$refs.myForm.getForm();

        // if (!valid) {
        //   return;
        // }

        this.loading = true;

        // 构建提交数据
        const submitData = {
          // 构建提交数据
        };

        console.log("线索研判提交数据:", submitData);

        // 模拟接口调用（假实现）
        // 这里可以替换为真实接口 service.analysisClue(submitData)
        this.$message.success("线索研判成功");
        this.$emit("success", submitData); // 通知父组件刷新列表
        this.handleClose(); // 关闭弹窗
      } catch (error) {
        console.error("线索研判失败:", error);
        this.$message.error("线索研判失败：" + (error.message || "未知错误"));
      } finally {
        this.loading = false;
      }
    },
  },
  mounted() {
    this.initData();
  },
  watch: {
    // 监听默认值变化，重新初始化
    defaultValue: {
      deep: true,
      handler() {
        this.initData();
      },
    },
  },
};
</script>

<style lang="scss" scoped>
// 调整文本域高度
::v-deep .el-textarea__inner {
  height: 120px !important;
  min-height: 120px !important;
}
</style>