<template>
  <IDialog
    title="线索移送"
    v-model="showDialog"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :loading="loading"
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

// 新字段校验规则（与截图中带*的必填项一致）
const rules = {
  riskLevel: [
    { required: true, message: "请选择风险等级", trigger: ["change", "blur"] },
  ],
  transferBasis: [
    { required: true, message: "请输入移送依据", trigger: ["change", "blur"] },
  ],
  receiveUnit: [
    {
      required: true,
      message: "请输入移送接收单位",
      trigger: ["change", "blur"],
    },
  ],
  transferDocument: [
    {
      required: true,
      message: "请输入移送公文信息",
      trigger: ["change", "blur"],
    },
  ],
};

export default {
  mixins: [dialogMixin],
  props: {
    defaultValue: {
      type: Object,
      default: () => ({}),
    },
    // type=0:新增，type=1:编辑
    type: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      columns: [
        {
          label: "风险等级",
          key: "riskLevel",
          value: "中", // 默认值：中
          type: "select",
          rules: rules.riskLevel,
          options: [
            { label: "高", value: "高" },
            { label: "中", value: "中" },
            { label: "低", value: "低" },
          ],
        },
        {
          label: "移送依据",
          key: "transferBasis",
          value: "",
          type: "textarea",
          rules: rules.transferBasis,
          placeholder:
            "根据《拒收人民币处罚依据及裁量标准》该行为属于“因三年内首次违反本项规定且危害后果轻微，现场整改到位”，不予处罚。已对商家开展不拒收人民币现金币政策宣导，对商家进行了警示教育。",
        },
        {
          label: "移送接收单位",
          key: "receiveUnit",
          value: "",
          type: "text",
          rules: rules.receiveUnit,
          placeholder: "请输入移送接收单位",
        },
        {
          label: "移送公文信息",
          key: "transferDocument",
          value: "",
          type: "text",
          rules: rules.transferDocument,
          placeholder: "请输入移送公文信息",
        },
      ],
      loading: false,
    };
  },
  methods: {
    // 初始化数据（编辑模式回显）
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
          // 固定值：移交状态默认为已移交
          transferStatus: "已移交",
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
// 保持原有样式，调整文本域高度
::v-deep .el-textarea__inner {
  height: 216px !important;
  min-height: 216px !important; // 确保最小高度一致
}
</style>
