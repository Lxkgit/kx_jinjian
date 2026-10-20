<template>
  <IDialog
    :title="`${status == 'add' ? '新增' : '编辑'}基层组织`"
    width="40%"
    v-model="showDialog"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
  >
    <i-form ref="myForm" :columns="columns" label-width="130px" />
  </IDialog>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin.vue";
import service from "../api";
const TYPE = {
  add: "新增",
  edit: "编辑",
};
export default {
  mixins: [dialogMixin],
  dicts: ["basic_org_type"],
  props: {
    status: {
      type: String,
      default: "add",
    },
    defaultValue: {
      type: Object,
    },
  },
  data() {
    return {
      columns: [
        {
          label: "基层组织名称",
          key: "grassroots",
          type: "text",
          rules: [
            {
              required: true,
              message: "请输入基层组织名称",
              trigger: "blur",
            },
          ],
        },
        {
          label: "基层组织类型",
          key: "grassrootsType",
          type: "select",
          rules: [
            { required: true, message: "请选择基层组织类型", trigger: "blur" },
          ],
          options: () => this.dict.type.basic_org_type,
        },
        {
          label: "联系人",
          key: "contactName",
          type: "text",
          rules: [{ required: true, message: "请输入联系人", trigger: "blur" }],
        },
        {
          label: "联系电话",
          key: "contactPhone",
          type: "text",
          rules: [
            { required: true, message: "请输入联系电话", trigger: "blur" },
          ],
        },
      ],
    };
  },
  methods: {
    initData() {
      if (this.status === "add") {
        return;
      }
      this.$nextTick(() => {
        this.$refs.myForm.setForm(this.defaultValue);
      });
    },
    handleClose() {
      this.$refs.myForm.reset();
      this.showDialog = false;
    },
    handleConfirm() {
      this.$refs.myForm.getForm((formInfo) => {
        let param = {
          ...formInfo,
          gridCode: this.defaultValue.gridCode,
        };
        if (this.status == "add") {
          service.addGridBasicOrg(param).then((res) => {
            this.$modal.msgSuccess(`新增基层组织成功`);
            this.$parent.fetchGridBasicOrgInfo();
            this.handleClose();
          });
        } else if (this.status == "edit") {
          param.id = this.defaultValue.id;
          service.editGridBasicOrg(param).then((res) => {
            this.$modal.msgSuccess(`修改基层组织成功`);
            this.$parent.fetchGridBasicOrgInfo();
            this.handleClose();
          });
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
</style>