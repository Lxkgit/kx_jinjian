<template>
  <IDialog
    :title="isEdit ? '编辑风险线索' : '新增风险线索'"
    v-model="showDialog"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :loading="loading"
  >
    <i-form
      ref="myForm"
      :columns="columns"
      labelWidth="150px"
      labelPosition="right"
    />
  </IDialog>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin.vue";
import dayjs from "dayjs";
import service from "../api";

// 新字段校验规则
const rules = {
  clueSourceOrg: [
    {
      required: true,
      message: "请输入线索来源机构",
      trigger: ["change", "blur"],
    },
  ],
  clueSourceChannel: [
    {
      required: true,
      message: "请输入线索来源渠道",
      trigger: ["change", "blur"],
    },
  ],
  clueSubmitTime: [
    {
      required: true,
      message: "请选择线索报送时间",
      trigger: ["change", "blur"],
    },
  ],
  riskSubjectName: [
    {
      required: true,
      message: "请输入涉险主体名称",
      trigger: ["change", "blur"],
    },
  ],
  creditCode: [
    {
      required: true,
      message: "请输入统一社会信用代码",
      trigger: ["change", "blur"],
    },
    {
      pattern: /^[0-9A-HJ-NPQRTUWXY]{2}\d{6}[0-9A-HJ-NPQRTUWXY]{10}$/,
      message: "请输入正确的统一社会信用代码",
      trigger: "blur",
    },
  ],
  riskSubjectAddress: [
    {
      required: true,
      message: "请输入涉险主体地址",
      trigger: ["change", "blur"],
    },
  ],
  clueType: [
    { required: true, message: "请选择线索类型", trigger: ["change", "blur"] },
  ],
  clueStatus: [
    { required: true, message: "请选择线索状态", trigger: ["change", "blur"] },
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
  computed: {
    // 判断是否为编辑模式
    isEdit() {
      return this.type === 1 && Object.keys(this.defaultValue).length > 0;
    },
  },
  data() {
    return {
      loading: false,
      columns: [
        {
          label: "线索编号",
          key: "clueNo",
          value: "",
          type: "text",
          disabled: true,
          placeholder: "系统自动生成",
        },
        {
          label: "线索来源机构",
          key: "clueSourceOrg",
          value: "",
          type: "text",
          rules: rules.clueSourceOrg,
          placeholder: "请输入线索来源机构",
        },
        {
          label: "线索来源渠道",
          key: "clueSourceChannel",
          value: "",
          type: "text",
          rules: rules.clueSourceChannel,
          placeholder: "请输入线索来源渠道",
        },
        {
          label: "线索报送时间",
          key: "clueSubmitTime",
          value: "",
          type: "datetime",
          format: "yyyy-MM-dd HH:mm:ss",
          valueFormat: "yyyy-MM-DD HH:mm:ss",
          rules: rules.clueSubmitTime,
        },
        {
          label: "涉险主体名称",
          key: "riskSubjectName",
          value: "",
          type: "text",
          rules: rules.riskSubjectName,
          placeholder: "请输入涉险主体名称",
        },
        {
          label: "统一社会信用代码",
          key: "creditCode",
          value: "",
          type: "text",
          rules: rules.creditCode,
          placeholder: "请输入统一社会信用代码",
        },
        {
          label: "涉险主体地址",
          key: "riskSubjectAddress",
          value: "",
          type: "text",
          rules: rules.riskSubjectAddress,
          placeholder: "请输入涉险主体地址",
        },
        {
          label: "线索类型",
          key: "clueType",
          value: "",
          type: "select",
          rules: rules.clueType,
          options: [
            { label: "非法集资", value: "非法集资" },
            { label: "非法放贷", value: "非法放贷" },
            { label: "不法中介", value: "不法中介" },
          ],
        },
        {
          label: "线索状态",
          key: "clueStatus",
          value: "",
          type: "select",
          rules: rules.clueStatus,
          options: [
            { label: "待复核", value: "待复核" },
            { label: "复核通过", value: "复核通过" },
            { label: "退回补正", value: "退回补正" },
          ],
        },
      ],
    };
  },
  methods: {
    // 生成线索编号（示例：FX-年月日-序号）
    generateClueNo() {
      const now = dayjs();
      const dateStr = now.format("YYYYMMDD");
      const seq = String(Math.floor(Math.random() * 100)).padStart(3, "0");
      return `FX-${dateStr}-${seq}`;
    },

    // 拆分涉险主体信息（编辑时用）
    parseRiskSubjectInfo(info) {
      if (!info) return { name: "", code: "", address: "" };

      const lines = info.split("\n");
      const name = lines[0] || "";
      const codeMatch = lines[1]?.match(/统一社会信用代码：(.+)/);
      const code = codeMatch ? codeMatch[1] : "";
      const addressMatch = lines[2]?.match(/地址：(.+)/);
      const address = addressMatch ? addressMatch[1] : "";

      return { name, code, address };
    },

    // 初始化表单数据
    initData() {
      // 深拷贝columns避免修改原数组
      const newColumns = JSON.parse(JSON.stringify(this.columns));

      if (this.isEdit) {
        // 编辑模式：回显数据
        const { defaultValue } = this;
        // 拆分涉险主体信息
        const { name, code, address } = this.parseRiskSubjectInfo(
          defaultValue.riskSubjectInfo
        );

        newColumns.forEach((item) => {
          switch (item.key) {
            case "clueNo":
              item.value = defaultValue.clueNo || "";
              break;
            case "clueSourceOrg":
              item.value = defaultValue.clueSourceOrg || "";
              break;
            case "clueSourceChannel":
              item.value = defaultValue.clueSourceChannel || "";
              break;
            case "clueSubmitTime":
              item.value =
                defaultValue.clueSubmitTime ||
                dayjs().format("YYYY-MM-DD HH:mm:ss");
              break;
            case "riskSubjectName":
              item.value = name;
              break;
            case "creditCode":
              item.value = code;
              break;
            case "riskSubjectAddress":
              item.value = address;
              break;
            case "clueType":
              item.value = defaultValue.clueType || "非法集资";
              break;
            case "clueStatus":
              item.value = defaultValue.clueStatus || "待复核";
              break;
          }
        });
      } else {
        // 新增模式：设置默认值
        newColumns.forEach((item) => {
          switch (item.key) {
            case "clueNo":
              item.value = this.generateClueNo();
              break;
            case "clueSubmitTime":
              item.value = dayjs().format("YYYY-MM-DD HH:mm:ss");
              break;
            case "clueType":
              item.value = "非法集资";
              break;
            case "clueStatus":
              item.value = "待复核";
              break;
          }
        });
      }

      this.columns = newColumns;

      // 同步到表单组件
      this.$nextTick(() => {
        if (this.isEdit) {
          const formData = {
            clueNo: this.defaultValue.clueNo || "",
            clueSourceOrg: this.defaultValue.clueSourceOrg || "",
            clueSourceChannel: this.defaultValue.clueSourceChannel || "",
            clueSubmitTime:
              this.defaultValue.clueSubmitTime ||
              dayjs().format("YYYY-MM-DD HH:mm:ss"),
            riskSubjectName: this.parseRiskSubjectInfo(
              this.defaultValue.riskSubjectInfo
            ).name,
            creditCode: this.parseRiskSubjectInfo(
              this.defaultValue.riskSubjectInfo
            ).code,
            riskSubjectAddress: this.parseRiskSubjectInfo(
              this.defaultValue.riskSubjectInfo
            ).address,
            clueType: this.defaultValue.clueType || "非法集资",
            clueStatus: this.defaultValue.clueStatus || "待复核",
          };
          this.$refs.myForm.setForm(formData);
        }
      });
    },

    // 关闭弹窗重置表单
    handleClose() {
      this.$refs.myForm?.reset();
      // 重置columns到初始状态
      this.initData();
    },

    // 提交表单
    async handleConfirm() {
      try {
        this.$refs.myForm.getForm(async (formInfo) => {
          this.loading = true;
          try {
            // 构建提交数据（与列表字段对齐）
            const submitData = {
              // 编辑模式保留原编号，新增模式用生成的编号
              clueNo: this.isEdit ? this.defaultValue.clueNo : formInfo.clueNo,
              clueSourceOrg: formInfo.clueSourceOrg,
              clueSourceChannel: formInfo.clueSourceChannel,
              clueSubmitTime: formInfo.clueSubmitTime,
              // 合并涉险主体信息
              riskSubjectInfo: `${formInfo.riskSubjectName}\n统一社会信用代码：${formInfo.creditCode}\n地址：${formInfo.riskSubjectAddress}`,
              clueType: formInfo.clueType,
              clueStatus: formInfo.clueStatus,
              // 编辑模式保留原有移交状态，新增模式默认未移交
              transferStatus: this.isEdit
                ? this.defaultValue.transferStatus
                : "未移交",
              receiveSigner: this.isEdit
                ? this.defaultValue.receiveSigner
                : "-",
              signDate: this.isEdit ? this.defaultValue.signDate : "-",
              receiveFeedback: this.isEdit
                ? this.defaultValue.receiveFeedback
                : "-",
            };

            console.log(
              `${this.isEdit ? "编辑" : "新增"}提交数据:`,
              submitData
            );

            // 模拟接口调用（假实现）
            // if (this.isEdit) {
            //   // 编辑接口
            //   const res = await service.editClue(submitData);
            // } else {
            //   // 新增接口
            //   const res = await service.addClue(submitData);
            // }

            // 假实现：提示成功
            this.$message.success(
              `${this.isEdit ? "编辑" : "新增"}风险线索成功`
            );
            this.$emit("success", submitData); // 传递提交数据给父组件
            this.handleClose();
            this.showDialog = false;
          } catch (error) {
            console.error(
              `${this.isEdit ? "编辑" : "新增"}风险线索失败:`,
              error
            );
            this.$message.error(
              `${this.isEdit ? "编辑" : "新增"}风险线索失败：` +
                (error.message || "未知错误")
            );
          } finally {
            this.loading = false;
          }
        });
      } catch (error) {
        console.error("表单验证失败:", error);
        this.loading = false;
      }
    },
  },
  watch: {
    // 监听defaultValue变化，重新初始化数据
    defaultValue: {
      deep: true,
      handler() {
        if (this.isEdit) {
          this.initData();
        }
      },
    },
    // 监听type变化，重新初始化数据
    type() {
      this.initData();
    },
  },
  mounted() {
    this.initData();
  },
};
</script>

<style lang="scss" scoped></style>
