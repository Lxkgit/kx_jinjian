<template>
  <IDialog
    title="添加社区监督员"
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
          label: "网格员名称",
          key: "supervisorName",
          type: "text",
          rules: [
            { required: true, message: "请输入监督员名称", trigger: "blur" },
          ],
        },
        {
          label: "联系电话",
          key: "supervisorPhone",
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
          service.addCommunityWatcher(param).then((res) => {
            this.$modal.msgSuccess(`新增社区监督员成功`);
            this.$parent.fetchCommunityWatcher();
            this.handleClose();
          });
        } else if (this.status == "edit") {
          param.id = this.defaultValue.id;
          service.editCommunityWatcher(param).then((res) => {
            this.$modal.msgSuccess(`修改社区监督员成功`);
            this.$parent.fetchCommunityWatcher();
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