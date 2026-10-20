<template>
  <IDialog title="线索上报" v-model="showDialog" @handleClose="handleClose" @handleConfirm="handleConfirm"
    :loading="loading">
    <div>
      <!-- 基本信息表单 -->
      <i-form ref="myForm" :columns="basicColumns" labelWidth="120px" labelPosition="right" />

      <!-- 涉险主体信息表单 -->
      <div style="margin-top: 20px; padding-top: 20px;">
        <div style="display: flex; align-items: center; margin-bottom: 16px;">
          <div style="flex: 0.1; height: 1px; background-color: #e4e7ed; margin-right: 10px;"></div>
          <h2 style="font-size: 18px; font-weight: 600; margin: 0;">涉险主体信息</h2>
          <div style="flex: 1; height: 1px; background-color: #e4e7ed; margin-left: 10px;"></div>
        </div>
        <i-form ref="subjectForm" :columns="subjectColumns" labelWidth="120px" labelPosition="right" />
      </div>
    </div>
  </IDialog>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin.vue";

// 字段校验规则（与截图中带*的必填项一致）
const rules = {
  clueSourceOrg: [
    { required: true, message: "请输入来源机构", trigger: ["change", "blur"] },
  ],
  clueSourceChannel: [
    { required: true, message: "请输入来源渠道", trigger: ["change", "blur"] },
  ],
  riskSubjectName: [
    { required: true, message: "请输入主体名称", trigger: ["change", "blur"] },
  ],
  unifiedSocialCreditCode: [
    { required: true, message: "请输入统一社会信用代码", trigger: ["change", "blur"] },
  ],
  address: [
    { required: true, message: "请输入地址", trigger: ["change", "blur"] },
  ],
  clueType: [
    { required: true, message: "请选择线索类型", trigger: ["change", "blur"] },
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
      basicColumns: [
        {
          label: "线索编号",
          key: "clueNo",
          value: "XS-" + new Date().toISOString().slice(0, 10).replace(/-/g, "") + "-" + Math.floor(100 + Math.random() * 900),
          type: "text",
          disabled: true
        },
        {
          label: "线索来源机构",
          key: "clueSourceOrg",
          value: "",
          type: "select",
          rules: rules.clueSourceOrg,
          options: [
            { label: "北京市朝阳区金融服务办公室", value: "北京市朝阳区金融服务办公室" },
            { label: "北京市海淀区金融工作局", value: "北京市海淀区金融工作局" },
            { label: "北京市西城区金融服务办公室", value: "北京市西城区金融服务办公室" },
            { label: "北京市东城区金融服务办公室", value: "北京市东城区金融服务办公室" },
            { label: "北京市丰台区金融服务办公室", value: "北京市丰台区金融服务办公室" }
          ],
          placeholder: "请选择来源机构",
        },
        {
          label: "线索来源渠道",
          key: "clueSourceChannel",
          value: "网格走访",
          type: "select",
          rules: rules.clueSourceChannel,
          options: [
            { label: "网格走访", value: "网格走访" },
            { label: "群众举报", value: "群众举报" },
            { label: "监管发现", value: "监管发现" },
            { label: "其他渠道", value: "其他渠道" }
          ],
          placeholder: "请选择来源渠道",
        },
        {
          label: "线索报送时间",
          key: "clueSubmitTime",
          value: new Date().toISOString().slice(0, 19).replace("T", " "),
          type: "datetime",
          format: "yyyy-MM-dd HH:mm:ss",
          valueFormat: "yyyy-MM-dd HH:mm:ss",
          disabled: true
        },
      ],
      subjectColumns: [
        {
          label: "名称",
          key: "riskSubjectName",
          value: "",
          type: "text",
          rules: rules.riskSubjectName,
          placeholder: "请输入主体名称",
        },
        {
          label: "统一社会信用代码",
          key: "unifiedSocialCreditCode",
          value: "",
          type: "text",
          rules: rules.unifiedSocialCreditCode,
          placeholder: "请输入统一社会信用代码",
        },
        {
          label: "地址",
          key: "address",
          value: "",
          type: "text",
          rules: rules.address,
          placeholder: "请输入地址",
        },
        {
          label: "线索类型",
          key: "clueType",
          value: "非法集资",
          type: "select",
          rules: rules.clueType,
          options: [
            { label: "非法集资", value: "非法集资" },
            { label: "非法放贷", value: "非法放贷" },
            { label: "不法中介", value: "不法中介" },
            { label: "其他", value: "其他" }
          ],
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
          this.$refs.subjectForm.setForm(this.defaultValue);
        });
      } else {
        // 新增模式：重置表单
        this.$refs.myForm?.reset();
        this.$refs.subjectForm?.reset();
      }
    },

    // 关闭弹窗
    handleClose() {
      this.showDialog = false;
      this.$refs.myForm?.reset();
      this.$refs.subjectForm?.reset();
    },

    // 确定提交
    async handleConfirm() {
      try {
        // 获取表单数据并校验
        // const [valid1, formInfo1] = await this.$refs.myForm.getForm();
        // const [valid2, formInfo2] = await this.$refs.subjectForm.getForm();

        // if (!valid1 || !valid2) {
        //   return;
        // }

        this.loading = true;

        // 构建提交数据
        const submitData = {
          // 构建提交数据
        };

        console.log("线索上报提交数据:", submitData);

        // 模拟接口调用（假实现）
        // 这里可以替换为真实接口 service.addEvent(submitData)
        this.$message.success("线索上报成功");
        this.$emit("success", submitData); // 通知父组件刷新列表
        this.handleClose(); // 关闭弹窗
      } catch (error) {
        console.error("线索上报失败:", error);
        this.$message.error("线索上报失败：" + (error.message || "未知错误"));
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
// 保持原有样式</style>