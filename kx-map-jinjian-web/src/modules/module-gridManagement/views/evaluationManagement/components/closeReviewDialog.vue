<template>
  <IDialog
    title="销号审核"
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
  reviewOpinion: [
    { required: true, message: "请选择审核意见", trigger: ["change", "blur"] },
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
          label: "审核意见",
          key: "reviewOpinion",
          value: "同意",
          type: "radio",
          rules: rules.reviewOpinion,
          options: [
            { label: "同意", value: "同意" },
            { label: "退回补正", value: "退回补正" },
          ],
        },
        {
          label: "备注",
          key: "remarks",
          value: "",
          type: "textarea",
          placeholder: "请输入备注",
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

        console.log("销号审核提交数据:", submitData);

        // 模拟接口调用（假实现）
        // 这里可以替换为真实接口 service.closeReviewClue(submitData)
        this.$message.success("销号审核成功");
        this.$emit("success", submitData); // 通知父组件刷新列表
        this.handleClose(); // 关闭弹窗
      } catch (error) {
        console.error("销号审核失败:", error);
        this.$message.error("销号审核失败：" + (error.message || "未知错误"));
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