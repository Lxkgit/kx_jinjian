<template>
  <IDialog
    title="线索移送录入"
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
  receiveUnit: [
    { required: true, message: "请输入接受单位", trigger: ["change", "blur"] },
  ],
  documentNo: [
    { required: true, message: "请输入公函号", trigger: ["change", "blur"] },
  ],
  deliveryTime: [
    { required: true, message: "请选择送达时间", trigger: ["change", "blur"] },
  ],
  notifyMethod: [
    { required: true, message: "请选择告知方式", trigger: ["change", "blur"] },
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
          label: "接受单位",
          key: "receiveUnit",
          value: "",
          type: "text",
          rules: rules.receiveUnit,
          placeholder: "请输入接受单位",
        },
        {
          label: "公函号",
          key: "documentNo",
          value: "",
          type: "text",
          rules: rules.documentNo,
          placeholder: "请输入公函号",
        },
        {
          label: "送达时间",
          key: "deliveryTime",
          value: new Date().toISOString().slice(0, 19).replace("T", " "),
          type: "datetime",
          format: "yyyy-MM-dd HH:mm:ss",
          valueFormat: "yyyy-MM-dd HH:mm:ss",
          rules: rules.deliveryTime,
        },
        {
          label: "告知方式",
          key: "notifyMethod",
          value: "",
          type: "select",
          rules: rules.notifyMethod,
          placeholder: "请选择告知方式",
          options: [
            { label: "书面告知", value: "书面告知" },
            { label: "电话告知", value: "电话告知" },
            { label: "邮件告知", value: "邮件告知" },
            { label: "其他", value: "其他" },
          ],
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

        console.log("线索移送提交数据:", submitData);

        // 模拟接口调用（假实现）
        // 这里可以替换为真实接口 service.transferClue(submitData)
        this.$message.success("线索移送成功");
        this.$emit("success", submitData); // 通知父组件刷新列表
        this.handleClose(); // 关闭弹窗
      } catch (error) {
        console.error("线索移送失败:", error);
        this.$message.error("线索移送失败：" + (error.message || "未知错误"));
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
// 保持原有样式
</style>