<template>
  <IDialog title="保存客群" v-model="showDialog" @handleClose="handleClose"
           @handleConfirm="handleConfirm">
    <i-form ref="myForm" :columns="columns" labelWidth="100px" labelPosition="right" />
  </IDialog>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin.vue";
import service from "../api";
export default {
  mixins: [dialogMixin],
  props: {
    defaultValue: {
      type: Object,
      default: () => ({}),
    },
    type: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      columns: [
        {
          label: "客群名称",
          key: "customGroupName",
          value: "",
          type: "text",
          rules: [
            {
              required: true,
              message: "请输入客群名称",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "说明",
          key: "groupDesc",
          value: "",
          type: "textarea",
        },
      ],
    };
  },
  methods: {
    initData() { },
    handleClose() {
      this.$refs.myForm.reset();
    },
    handleConfirm() {
      this.$refs.myForm.getForm((formInfo) => {
        const data = {
          ...formInfo,
          ...this.defaultValue,
          type: this.type,
        };
        service.saveCustomGroup(data).then((res) => {
          this.$message.success("操作成功");
          this.showDialog = false;
          this.$emit("fetchList");
          this.handleClose()
        });
      });
    },
  },
};
</script>

<style lang="scss" scoped></style>
