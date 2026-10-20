<template>
  <IDialog
    title="督办反馈录入"
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
  superviseUnit: [
    { required: true, message: "请输入督办单位", trigger: ["change", "blur"] },
  ],
  superviseTime: [
    { required: true, message: "请选择时间", trigger: ["change", "blur"] },
  ],
  requirements: [
    { required: true, message: "请输入要求", trigger: ["change", "blur"] },
  ],
  feedbackResult: [
    { required: true, message: "请输入反馈结果", trigger: ["change", "blur"] },
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
          label: "督办单位",
          key: "superviseUnit",
          value: "",
          type: "text",
          rules: rules.superviseUnit,
          placeholder: "请输入督办单位",
        },
        {
          label: "时间",
          key: "superviseTime",
          value: new Date().toISOString().slice(0, 19).replace("T", " "),
          type: "datetime",
          format: "yyyy-MM-dd HH:mm:ss",
          valueFormat: "yyyy-MM-dd HH:mm:ss",
          rules: rules.superviseTime,
        },
        {
          label: "要求",
          key: "requirements",
          value: "",
          type: "textarea",
          rules: rules.requirements,
          placeholder: "请输入要求",
        },
        {
          label: "反馈结果",
          key: "feedbackResult",
          value: "",
          type: "textarea",
          rules: rules.feedbackResult,
          placeholder: "请输入反馈结果",
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

        console.log("督办反馈录入提交数据:", submitData);

        // 模拟接口调用（假实现）
        // 这里可以替换为真实接口 service.superviseClue(submitData)
        this.$message.success("督办反馈录入成功");
        this.$emit("success", submitData); // 通知父组件刷新列表
        this.handleClose(); // 关闭弹窗
      } catch (error) {
        console.error("督办反馈录入失败:", error);
        this.$message.error("督办反馈录入失败：" + (error.message || "未知错误"));
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