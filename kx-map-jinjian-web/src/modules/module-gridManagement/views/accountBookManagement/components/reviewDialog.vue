<!-- reviewDialog.vue -->
<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogVisible"
    width="500px"
    :before-close="handleClose"
    :loading="loading"
  >
    <el-form
      ref="myForm"
      :model="form"
      :rules="rules"
      label-width="120px"
      label-position="right"
    >
      <el-form-item label="复核结果" prop="reviewResult">
        <el-select
          v-model="form.reviewResult"
          placeholder="请选择复核结果"
          style="width: 100%"
        >
          <el-option label="通过" value="通过"></el-option>
          <el-option label="不通过" value="不通过"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="复核意见" prop="reviewOpinion">
        <el-input
          v-model="form.reviewOpinion"
          type="textarea"
          :rows="4"
          placeholder="请输入复核意见"
        ></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="handleConfirm" :loading="loading">
        确定
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: "reviewDialog",
  props: {
    // 父组件传入的显示状态
    visible: {
      type: Boolean,
      default: false,
    },
    // 复核行数据
    rowData: {
      type: Object,
      default: () => ({}),
    },
    title: {
      type: String,
      default: "复核",
    },
  },
  data() {
    return {
      dialogVisible: this.visible,
      loading: false,
      form: {
        reviewResult: "", // 复核结果
        reviewOpinion: "", // 复核意见
        checkNo: "", // 排查编号（关联行数据）
      },
      rules: {
        reviewResult: [
          { required: true, message: "请选择复核结果", trigger: "change" },
        ],
      },
    };
  },
  watch: {
    // 监听父组件传入的visible，同步弹窗显示状态
    visible(val) {
      this.dialogVisible = val;
      // 打开弹窗时初始化表单
      if (val) {
        this.initForm();
      }
    },
    // 弹窗关闭时通知父组件
    dialogVisible(val) {
      this.$emit("update:visible", val);
    },
  },
  methods: {
    // 初始化表单（回显行数据）
    initForm() {
      this.form = {
        reviewResult: "",
        reviewOpinion: "",
        checkNo: this.rowData.checkNo || "", // 关联当前行的排查编号
      };
    },
    // 关闭弹窗
    handleClose() {
      this.dialogVisible = false;
      this.$refs.myForm?.resetFields(); // 重置表单
    },
    // 确认复核
    handleConfirm() {
      this.$refs.myForm.validate(async (valid) => {
        if (!valid) return;

        this.loading = true;
        // 模拟接口请求延迟（假实现）
        setTimeout(() => {
          this.loading = false;
          this.$message.success("复核成功！");
          // 向父组件传递复核结果
          this.$emit("submit", {
            checkNo: this.form.checkNo,
            reviewResult: this.form.reviewResult,
            reviewOpinion: this.form.reviewOpinion,
          });
          this.handleClose();
        }, 800);
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.dialog-footer {
  text-align: right;
}
.el-form-item {
  margin-bottom: 20px;
}
.el-textarea__inner {
  resize: vertical;
}
</style>
