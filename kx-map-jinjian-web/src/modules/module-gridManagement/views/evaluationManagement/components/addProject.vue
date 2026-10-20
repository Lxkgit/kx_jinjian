<template>
  <i-dialog
    :title="state"
    v-model="showDialog"
    width="50%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    appendToBody
  >
    <i-form
      ref="filterBar"
      :columns="baseColumns"
      label-position="right"
      label-width="150px"
    >
    </i-form>
  </i-dialog>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin";
export default {
  mixins: [dialogMixin],
  props: {
    editInfo: {
      type: Object,
      default: () => {},
    },
    status: {
      type: String,
    },
  },
  data() {
    return {
      showDialog: this.value,
      baseColumns: [
        {
          label: "评价材料类型",
          key: "classification",
          type: "textarea",
          rules: [
            {
              required: true,
              trigger: "blur",
              message: "评价材料类型不能为空",
            },
          ],
        },
        {
          label: "上报材料说明",
          key: "remark",
          type: "textarea",
          rules: [
            {
              required: true,
              trigger: "blur",
              message: "上报材料说明不能为空",
            },
          ],
        },
      ],
    };
  },
  computed: {
    state() {
      switch (this.status) {
        case "add":
          return "添加评价材料内容";
        case "edit":
          return "编辑评价材料内容";
        default:
          return "添加评价项目";
      }
    },
  },
  methods: {
    initData() {
      this.$nextTick(() => {
        this.$refs.filterBar.setForm(this.editInfo);
      });
    },
    handleConfirm() {
      this.$refs.filterBar.getForm((formInline) => {
        if (this.status == "add") {
          const param = {
            ...formInline,
            id: new Date().getTime(),
          };
          this.$parent.$parent.tableData.push(param);
          this.handleClose();
        } else {
          const param = {
            ...formInline,
            id: this.editInfo.id,
          };
          this.$emit('updateData', param)
          this.handleClose();
        }
      });
    },
    handleClose() {
      this.showDialog = false;
      this.$refs.filterBar.reset();
    },
  },
};
</script>

<style scoped>
</style>