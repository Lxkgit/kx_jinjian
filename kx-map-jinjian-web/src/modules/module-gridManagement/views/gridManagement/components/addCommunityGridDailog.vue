<template>
  <IDialog
    title="添加重点对象"
    width="40%"
    v-model="showDialog"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
  >
    <i-form
      ref="myForm"
      labelPosition="right"
      :columns="columns"
      label-width="130px"
    />
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
  dicts: ["grid_rate_type"],
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
          label: "重点管理对象",
          key: "importName",
          type: "text",
          labelWidth: "150px",
          rules: [
            { required: true, message: "请输入重点管理对象", trigger: "blur" },
          ],
        },
        {
          label: "重点管理对象地址",
          key: "importAddress",
          type: "text",
          labelWidth: "150px",
          rules: [
            {
              required: true,
              message: "请输入重点管理对象地址",
              trigger: "blur",
            },
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
          service.addGridImportant(param).then((res) => {
            this.$modal.msgSuccess(`新增重点管理对象成功`);
            this.$parent.fetchImportantList();
            this.handleClose();
          });
        } else if ((this.status = "edit")) {
          param.id = this.defaultValue.id;
          service.editGridImportant(param).then((res) => {
            this.$modal.msgSuccess(`修改重点管理对象成功`);
            this.$parent.fetchImportantList();
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