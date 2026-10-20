<template>
  <el-dialog :title="title" :visible.sync="dialogVisible" :width="width"
            :modal="modal"
           :before-close="handleClose"
             :close-on-click-modal="false" :append-to-body="appendToBody">
    <template #title>
      <slot name="title"></slot>
    </template>
    <slot />
    <span slot="footer" class="dialog-footer" v-if="showFooter">
      <el-button @click="handleClose">取 消</el-button>
      <slot name="foot_between"></slot>
      <el-button v-if="showConfirm" :disabled="confirmDisabled" type="primary" :loading="loading"
                 @click="handleConfirm">确 定
      </el-button>
      <slot name="foot"></slot>
    </span>
  </el-dialog>
</template>

<script>
export default {
  props: {
    value: {
      type: Boolean,
      default: false,
    },
    title: {
      type: String,
      default: "",
    },
    width: {
      type: String,
      default: "30%",
    },
    confirmDisabled: {
      type: Boolean,
      default: false,
    },
    showFooter: {
      type: Boolean,
      default: true,
    },
    appendToBody: {
      type: Boolean,
      default: false,
    },
    showConfirm: {
      type: Boolean,
      default: true,
    },
    loading: {
      type: Boolean,
      default: false
    },
    modal: {
      type: Boolean,
      default: true
    },
  },
  data() {
    return {
      dialogVisible: this.value,
    };
  },
  watch: {
    value: {
      handler(val) {
        this.dialogVisible = val;
      },
    },
    dialogVisible: {
      handler(val) {
        this.$emit("input", val);
      },
    },
    showFooter: {
      handler(val) {
        console.log(val, " foot");
      },
    },
  },
  methods: {
    handleClose() {
      this.dialogVisible = false;
      this.$emit("handleClose");
    },
    handleConfirm() {
      this.$emit("handleConfirm");
    },
  },
};
</script>

<style lang="scss" scoped>
::v-deep .el-dialog__header {
  border-bottom: 1px solid #e8e8e8;
  padding: 20px;
}
::v-deep .el-dialog__body {
  padding: 24px 22px;
}
::v-deep .el-dialog__footer {
  padding: 12px 24px;
  border-top: 1px solid #e8e8e8;
}
</style>