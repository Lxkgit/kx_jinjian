<template>
  <i-dialog
    :title="dialogTitle"
    v-model="showDialog"
    width="60%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="this.status != 'view'"
  >
    <div class="form-title">投诉信息</div>
    <i-form
      ref="msgForm1"
      :gridColumns="2"
      :columns="columns1"
      labelWidth="160px"
      labelPosition="right"
    >
    </i-form>
    <el-divider></el-divider>
    <div class="form-title">所属网格信息</div>
    <i-form
      ref="msgForm2"
      :gridColumns="2"
      :columns="columns2"
      labelWidth="160px"
      labelPosition="right"
    >
    </i-form>
  </i-dialog>
</template>
    
<script>
import dialogMixin from "@/mixins/dialogMixin.vue";
export default {
  name: "addStoreDialog",
  mixins: [dialogMixin],
  dicts: ["complaint_source", "merchant_true_false"],
  props: {
    defaultValue: {
      type: Object,
    },
  },
  data() {
    return {
      status: "view",
      columns1: [
        {
          label: "投诉来源",
          key: "complaintPersonSource",
          type: "select",
          options: () => this.dict.type.complaint_source,
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择投诉来源",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "投诉时间",
          key: "complaintTime",
          type: "text",
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择投诉时间",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "投诉人",
          key: "complaintPerson",
          type: "text",
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择投诉人",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "投诉人联系电话",
          key: "complaintPersonPhone",
          type: "text",
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择投诉人联系电话",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "投诉人联系电话",
          key: "complaintPersonPhone",
          type: "text",
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择投诉人联系电话",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "线索来源",
          key: "leadSource",
          type: "text",
          disabled: () => this.status == "view",
          rules: [{ required: true, message: "请选择投诉人联系电话" }],
        },
        {
          label: "行业",
          key: "industry",
          type: "text",
          disabled: () => this.status == "view",
          rules: [{ required: true, message: "请选择投诉人联系电话" }],
        },
        {
          label: "发生场景",
          key: "scene",
          type: "text",
          disabled: () => this.status == "view",
          rules: [{ required: true, message: "请选择投诉人联系电话" }],
        },
        {
          label: "是否为拒收人民币现金服务",
          key: "isRefuseCash",
          type: "select",
          disabled: () => this.status == "view",
          options: () => this.dict.type.merchant_true_false,
          rules: [{ required: true, message: "请选择投诉人联系电话" }],
        },
        {
          label: "处理状态",
          key: "hadResolve",
          type: "select",
          options: [
            { value: "0", label: "已处理" },
            { value: "1", label: "未处理" },
          ],
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择处理状态",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "具体事由",
          key: "specificCause",
          type: "textarea",
          disabled: () => this.status == "view",
          span: 2,
          rules: [
            {
              required: true,
              message: "请选择投诉事件描述",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "处置情况",
          key: "dispositionSituation",
          type: "textarea",
          disabled: () => this.status == "view",
          span: 2,
          rules: [
            {
              required: true,
              message: "请选择投诉事件描述",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "网点联络员",
          key: "dutyPointDutyPerson",
          type: "text",
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择网点联络员",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "联络员联系电话",
          key: "dutyPersonPhone",
          type: "text",
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择联络员联系电话",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "上级负责人",
          key: "gridManager",
          type: "text",
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择上级负责人",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "上级负责人联系电话",
          key: "gridManagerPhone",
          type: "text",
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择上级负责人联系电话",
              trigger: ["change", "blur"],
            },
          ],
        },
      ],
      columns2: [
        {
          label: "网格名称",
          key: "complaintInGrid",
          type: "text",
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择投诉人",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "网格长",
          key: "gridManager",
          type: "text",
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择投诉人",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "网格长联系电话",
          key: "gridManagerPhone",
          type: "text",
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择投诉人",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "网格责任网点",
          key: "gridDutyPoint",
          type: "text",
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择投诉人",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "责任网点负责人",
          key: "dutyPointDutyPerson",
          type: "text",
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择投诉人",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "网点负责人联系电话",
          key: "dutyPersonPhone",
          type: "text",
          disabled: () => this.status == "view",
          rules: [
            {
              required: true,
              message: "请选择投诉人",
              trigger: ["change", "blur"],
            },
          ],
        },
      ],
      dialogVisible: false,
    };
  },
  computed: {
    dialogTitle() {
      return this.status == "add"
        ? "新增"
        : this.status == "edit"
        ? "编辑"
        : "详情";
    },
  },
  methods: {
    initData() {
      this.$nextTick(() => {
        this.$refs.msgForm1.setForm(this.defaultValue);
        this.$refs.msgForm2.setForm(this.defaultValue);
      });
    },
    handleConfirm() {},
    handleClose() {
      this.$refs.msgForm1.reset();
      this.$refs.msgForm2.reset();
      this.showDialog = false;
    },
  },
};
</script>
    
<style lang="scss" scoped>
.form-title {
  margin: 10px 0 20px 0;
  font-size: 18px;
}
</style>