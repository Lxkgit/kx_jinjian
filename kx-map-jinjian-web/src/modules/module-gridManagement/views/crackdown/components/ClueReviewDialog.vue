<template>
  <el-dialog title="线索复核" :visible.sync="dialogVisible" width="500px" append-to-body :close-on-click-modal="false" class="operation-dialog" @closed="reset">
    <el-form ref="reviewForm" :model="form" :rules="rules" label-position="top" size="small">
      <el-form-item label="复核结果" prop="reviewResult">
        <el-select v-model="form.reviewResult" style="width: 100%">
          <el-option label="复核通过" :value="2" />
          <el-option label="退回补正" :value="3" />
        </el-select>
      </el-form-item>
      <el-form-item label="复核意见" prop="reviewOpinion">
        <el-input v-model.trim="form.reviewOpinion" type="textarea" :rows="4" maxlength="1000" show-word-limit placeholder="请输入复核意见" />
      </el-form-item>
    </el-form>
    <span slot="footer">
      <el-button @click="dialogVisible=false">取消</el-button>
      <el-button type="primary" :loading="submitting" @click="submit">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import service from "../api";

export default {
  name: "ClueReviewDialog",
  props: {
    value: { type: Boolean, default: false },
    clue: { type: Object, default: () => ({}) },
  },
  data() {
    return {
      submitting: false,
      form: { id: null, reviewResult: 2, reviewOpinion: "" },
      rules: {
        reviewResult: [{ required: true, message: "请选择复核结果", trigger: "change" }],
        reviewOpinion: [{ required: true, message: "请输入复核意见", trigger: "blur" }],
      },
    };
  },
  computed: {
    dialogVisible: {
      get() { return this.value; },
      set(value) { this.$emit("input", value); },
    },
  },
  watch: {
    value(visible) {
      if (!visible) return;
      this.form = { id: this.clue.id, reviewResult: 2, reviewOpinion: "" };
      this.$nextTick(() => this.$refs.reviewForm && this.$refs.reviewForm.clearValidate());
    },
  },
  methods: {
    submit() {
      this.$refs.reviewForm.validate(async valid => {
        if (!valid) return;
        this.submitting = true;
        try {
          await service.riskClueReview(this.form);
          this.$message.success("复核成功");
          this.dialogVisible = false;
          this.$emit("success");
        } finally {
          this.submitting = false;
        }
      });
    },
    reset() {
      this.form = { id: null, reviewResult: 2, reviewOpinion: "" };
    },
  },
};
</script>
<style scoped lang="scss">
.operation-dialog ::v-deep .el-dialog { border-radius:8px; overflow:hidden; }.operation-dialog ::v-deep .el-dialog__header { padding:16px 24px; border-bottom:1px solid #ebeef5; background:#f5f7fa; }.operation-dialog ::v-deep .el-dialog__body { padding:24px; }.operation-dialog ::v-deep .el-dialog__footer { padding:16px 24px; border-top:1px solid #ebeef5; }.operation-dialog ::v-deep .el-form-item__label { color:#303133; font-weight:500; }
</style>
