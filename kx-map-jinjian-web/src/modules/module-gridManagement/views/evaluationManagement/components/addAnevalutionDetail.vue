<template>
  <i-dialog
    title="新增评价内容"
    v-model="showDialog"
    width="70%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    appendToBody
  >
    <i-form
      :gridColumns="1"
      ref="firstForm"
      :columns="firstColumns"
      label-width="140px"
      label-position="right"
    />
  </i-dialog>
</template>
  <script>
import dialogMixin from "@/mixins/dialogMixin.vue";
import pageMixin from "@/mixins/pageMixin";
export default {
  mixins: [dialogMixin, pageMixin],
  data() {
    return {
      firstColumns: [
        {
          label: "评价内容",
          key: "filingManagerName",
          type: "textarea",
          rules: [
            {
              required: true,
              message: "请输入评价内容",
              trigger: "blur",
            },
          ],
        },
        {
          label: "评价项目",
          key: "filingOrgName",
          type: "textarea",
          rules: [
            {
              required: true,
              message: "请输入评价项目",
              trigger: "blur",
            },
          ],
        },
        {
          label: "评价标准",
          key: "filingDate",
          type: "textarea",
          rules: [
            {
              required: true,
              message: "请输入评价标准",
              trigger: "blur",
            },
          ],
        },
        {
          label: "得分标准",
          key: "filingDate1",
          type: "textarea",
          rules: [
            {
              required: true,
              message: "请输入得分标准",
              trigger: "blur",
            },
          ],
        },
      ],
    };
  },
  mounted() {},
  methods: {
    handleConfirm() {
      this.$refs.firstForm.getForm((formInline) => {
        this.$modal.msgSuccess("添加成功");
        this.showDialog = false;
      });
    },
    handleClose() {},
  },
};
</script>
<style scoped lang="scss">
</style>