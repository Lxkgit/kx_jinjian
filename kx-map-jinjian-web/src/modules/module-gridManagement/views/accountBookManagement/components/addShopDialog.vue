<template>
  <IDialog
    :title="title"
    v-model="showDialog"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :loading="loading"
  >
    <i-form
      ref="myForm"
      :columns="columns"
      labelWidth="160px"
      labelPosition="right"
    />
  </IDialog>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin.vue";
import dayjs from "dayjs";

export default {
  mixins: [dialogMixin],
  props: {
    defaultValue: {
      type: Object,
      default: () => ({}),
    },
    title: { type: String, default: "新增排查记录" },
  },
  data() {
    return {
      loading: false,
      // 新增：控制弹窗显示的核心状态（兼容 dialogMixin）
      showDialog: false,
      columns: [
        {
          label: "排查编号",
          key: "checkNo",
          value: this.generateCheckNo(),
          type: "text",
          disabled: true,
          placeholder: "系统自动生成",
        },
        {
          label: "排查日期",
          key: "checkDate",
          value: dayjs().format("YYYY-MM-DD"),
          type: "date",
          required: true,
          placeholder: "请选择排查日期",
        },
        {
          label: "排查牵头部门",
          key: "leadDept",
          value: "",
          type: "text",
          required: true,
          placeholder: "请输入排查牵头部门",
        },
        {
          label: "排查牵头部门人员",
          key: "leadDeptPerson",
          value: "",
          type: "text",
          required: true,
          placeholder: "请输入排查牵头部门人员",
        },
        {
          label: "排查区域（详细地址）",
          key: "checkArea",
          value: "",
          type: "text",
          required: true,
          placeholder: "请输入详细地址",
        },
        {
          label: "覆盖市场主体类型",
          key: "subjectType",
          value: "",
          type: "select",
          required: true,
          placeholder: "请选择",
          options: [
            { label: "商户", value: "商户" },
            { label: "小微企业", value: "小微企业" },
            { label: "办公室", value: "办公室" },
          ],
        },
        {
          label: "覆盖市场主体数量",
          key: "subjectCount",
          value: "",
          type: "number",
          required: true,
          placeholder: "请输入数量",
          min: 1,
        },
        {
          label: "排查人次",
          key: "checkTimes",
          value: "",
          type: "number",
          required: true,
          placeholder: "请输入排查人次",
          min: 1,
        },
        {
          label: "排查问题简述",
          key: "problemDesc",
          value: "",
          type: "textarea",
          required: true,
          placeholder: "请简述排查问题",
          rows: 3,
        },
        {
          label: "佐证材料",
          key: "evidenceFiles",
          value: [],
          type: "upload",
          placeholder: "选择文件",
        },
        {
          label: "填报人",
          key: "reporter",
          value: "",
          type: "text",
          required: true,
          placeholder: "请输入填报人",
        },
        {
          label: "填报时间",
          key: "reportTime",
          value: dayjs().format("YYYY-MM-DD HH:mm:ss"),
          type: "datetime",
          required: true,
          placeholder: "请选择填报时间",
        },
      ],
    };
  },
  watch: {
    // 监听父组件传入的 v-model 状态
    value: {
      handler(val) {
        this.showDialog = val;
        if (val) {
          this.initData(); // 打开弹窗时初始化数据
        }
      },
      immediate: true,
    },
    // 弹窗关闭时通知父组件
    showDialog(val) {
      this.$emit("input", val);
    },
  },
  methods: {
    // 生成排查编号（示例：PC+年月日+4位序号）
    generateCheckNo() {
      const now = dayjs();
      const dateStr = now.format("YYYYMMDD");
      const seq = String(Math.floor(Math.random() * 10000)).padStart(4, "0");
      return `PC${dateStr}${seq}`;
    },
    initData() {
      if (Object.keys(this.defaultValue).length > 0) {
        // 编辑模式：回显数据
        this.$refs.myForm.setForm(this.defaultValue);
      } else {
        // 新增模式：重置表单
        this.$refs.myForm.reset();
      }
    },
    handleClose() {
      // 关闭弹窗
      this.showDialog = false;
      this.$refs.myForm.reset();
    },
    async handleConfirm() {
      try {
        // 1. 表单校验
        const [valid, formData] = await this.$refs.myForm.getForm();
        if (!valid) {
          this.$message.error("请完善所有必填项！");
          return;
        }

        // 2. 模拟提交加载状态
        this.loading = true;

        // 3. 模拟接口延迟（假实现）
        setTimeout(() => {
          this.loading = false;
          // 4. 提交成功提示
          this.$message.success(
            this.title.includes("新增") ? "新增成功！" : "编辑成功！"
          );
          // 5. 提交数据给父组件
          this.$emit("submit", formData);
          // 6. 关闭弹窗（核心修复点）
          this.showDialog = false;
        }, 800);
      } catch (error) {
        this.loading = false;
        this.$message.error("操作失败，请重试！");
        console.error("提交失败：", error);
      }
    },
  },
  mounted() {
    this.initData();
  },
};
</script>

<style lang="scss" scoped>
::v-deep .i-form-item {
  margin-bottom: 16px;
}
::v-deep .el-textarea__inner {
  resize: vertical;
}
/* 适配长label，避免换行 */
::v-deep .el-form-item__label {
  white-space: nowrap;
}
/* 修复按钮加载状态样式 */
::v-deep .el-dialog__footer .el-button--loading {
  pointer-events: none;
}
</style>
