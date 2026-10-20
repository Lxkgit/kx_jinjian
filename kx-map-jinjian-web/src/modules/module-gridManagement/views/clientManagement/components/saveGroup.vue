<template>
  <IDialog title="保存网点" v-model="showDialog" @handleClose="handleClose"
           @handleConfirm="handleConfirm" :loading="loading">
    <i-form ref="myForm" :columns="columns" labelWidth="120px" labelPosition="right" />
  </IDialog>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin.vue";
import services from "../api";
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
          label: "网点分类名称",
          key: "customGroupName",
          value: "",
          type: "text",
          rules: [
            {
              required: true,
              message: "请输入网点名称",
              trigger: ["change", "blur"],
            },
          ],
        },
        {
          label: "说明",
          key: "groupDesc",
          value: "",
          type: "textarea",
          maxlength: 200,
          showWordLimit: true
        },
      ],
      loading: false
    };
  },
  methods: {
    initData() { },
    handleClose() {
      this.$refs.myForm.reset();
    },
    handleConfirm() {
      this.$refs.myForm.getForm((formInfo) => {
        this.loading = true
        const data = {
          ...formInfo,
          ...this.defaultValue,
          type: this.type,
        };
        services.saveCustomGroup(data).then((res) => {
          this.loading = false
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
