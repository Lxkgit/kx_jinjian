<template>
  <i-dialog
    title="任务模板预览"
    v-model="showDialog"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="false"
    width="774px"
  >
    <i-form
      ref="msgForm1"
      :columns="columns"
      labelWidth="120px"
      labelPosition="right"
    >
    </i-form>
  </i-dialog>
</template>
<script>
import dialogMixin from "@/mixins/dialogMixin.vue";
export default {
  mixins: [dialogMixin],
  data() {
    return {
      columns: [
        {
          label: "任务要求",
          key: "dispositionSituation",
          type: "textarea",
          disabled: () => this.status == "view",
          span: 2,
        },
      ],
    };
  },
};
</script>
