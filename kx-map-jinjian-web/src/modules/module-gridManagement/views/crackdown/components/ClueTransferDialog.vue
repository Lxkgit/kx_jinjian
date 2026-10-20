<template>
  <el-dialog title="线索移送" :visible.sync="dialogVisible" width="680px" append-to-body :close-on-click-modal="false" class="operation-dialog" @closed="reset">
    <el-form ref="transferForm" :model="form" :rules="rules" label-position="top" size="small">
      <el-row :gutter="14">
        <el-col :span="12"><el-form-item label="移送依据" prop="transferBasis"><el-input v-model.trim="form.transferBasis" maxlength="1000" placeholder="请输入移送依据" /></el-form-item></el-col>
        <el-col :span="12"><el-form-item label="移送接收单位" prop="receiveOrgName"><el-input v-model.trim="form.receiveOrgName" maxlength="200" placeholder="请输入移送接收单位" /></el-form-item></el-col>
      </el-row>
      <el-row :gutter="14">
        <el-col :span="12">
          <el-form-item label="移送方式" prop="transferMethod">
            <el-select v-model="form.transferMethod" style="width: 100%">
              <el-option label="电子送达" :value="1" /><el-option label="纸质移交" :value="2" /><el-option label="其他" :value="3" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12"><el-form-item label="移送公文信息" prop="documentInfo"><el-input v-model.trim="form.documentInfo" maxlength="1000" placeholder="请输入移送公文信息" /></el-form-item></el-col>
      </el-row>
      <el-form-item label="移送时间" prop="transferTime">
        <el-date-picker v-model="form.transferTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="请选择移送时间" style="width: 100%" />
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
import { nowText } from "../config/clue";

export default {
  name: "ClueTransferDialog",
  props: {
    value: { type: Boolean, default: false },
    clue: { type: Object, default: () => ({}) },
  },
  data() {
    return {
      submitting: false,
      form: this.emptyForm(),
      rules: {
        transferBasis: [{ required: true, message: "请输入移送依据", trigger: "blur" }],
        receiveOrgName: [{ required: true, message: "请输入移送接收单位", trigger: "blur" }],
        transferMethod: [{ required: true, message: "请选择移送方式", trigger: "change" }],
        documentInfo: [{ required: true, message: "请输入移送公文信息", trigger: "blur" }],
        transferTime: [{ required: true, message: "请选择移送时间", trigger: "change" }],
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
      this.form = { ...this.emptyForm(), clueId: this.clue.id };
      this.$nextTick(() => this.$refs.transferForm && this.$refs.transferForm.clearValidate());
    },
  },
  methods: {
    emptyForm() {
      return { clueId: null, transferBasis: "", receiveOrgId: null, receiveOrgCode: "", receiveOrgName: "", transferMethod: 1, documentInfo: "", transferTime: nowText() };
    },
    submit() {
      this.$refs.transferForm.validate(async valid => {
        if (!valid) return;
        this.submitting = true;
        try {
          await service.riskClueTransfer(this.form);
          this.$message.success("移送成功");
          this.dialogVisible = false;
          this.$emit("success");
        } finally {
          this.submitting = false;
        }
      });
    },
    reset() { this.form = this.emptyForm(); },
  },
};
</script>
<style scoped lang="scss">
.operation-dialog ::v-deep .el-dialog { border-radius:8px; overflow:hidden; }.operation-dialog ::v-deep .el-dialog__header { padding:16px 24px; border-bottom:1px solid #ebeef5; background:#f5f7fa; }.operation-dialog ::v-deep .el-dialog__body { padding:24px; }.operation-dialog ::v-deep .el-dialog__footer { padding:16px 24px; border-top:1px solid #ebeef5; }.operation-dialog ::v-deep .el-form-item__label { color:#303133; font-weight:500; }
</style>
