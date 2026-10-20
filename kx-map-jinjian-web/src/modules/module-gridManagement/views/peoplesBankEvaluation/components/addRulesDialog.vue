<template>
  <el-dialog
    title="新增规则"
    :visible.sync="show"
    width="50%"
    :before-close="handleClose"
    append
  >
    <!--:rules="rules"  -->
    <el-form
      ref="formData"
      :model="formData"
      label-position="right"
      label-width="140px"
    >
      <el-form-item label="评价项目" prop="title">
        <el-input
          class="form-item-line"
          v-model="formData.title"
          placeholder="请输入评价项目名称"
          type="text"
          style="width: 100%"
        >
        </el-input>
      </el-form-item>

      <el-form-item label="评价项目分数" prop="title">
        <el-input
          class="form-item-line"
          v-model="formData.title"
          placeholder="请输入评价项目分数"
          type="text"
          style="width: 100%"
        >
        </el-input>
      </el-form-item>

      <el-form-item label="评价内容" prop="title">
        <el-input
          class="form-item-line"
          v-model="formData.title"
          placeholder="请输入评价内容"
          type="text"
          style="width: 100%"
        >
        </el-input>
      </el-form-item>

      <el-form-item label="评价标准" prop="title">
        <el-input
          class="form-item-line"
          v-model="formData.title"
          placeholder="请输入评价标准"
          type="textarea"
          :rows="4"
          style="width: 100%"
        >
        </el-input>
      </el-form-item>

      <el-form-item label="得分标准" prop="title">
        <el-input
          class="form-item-line"
          v-model="formData.title"
          placeholder="请输入得分标准"
          type="textarea"
          :rows="4"
          style="width: 100%"
        >
        </el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary">确 定</el-button>
      <el-button @click="handleClose">取 消</el-button>
    </span>
  </el-dialog>
</template>
<script>
export default {
  name: "addRulesDialog",
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      show: false,
      formData: { customerSource: "" },
    };
  },
  watch: {
    dialogVisible(newval) {
      this.show = newval;
    },
  },
  methods: {
    handleClose() {
      this.$emit("update:dialogVisible", false);
    },
  },
};
</script>
<style lang="scss" scoped>
//弹窗相关
::v-deep .center-dialog,
::v-deep .el-dialog {
  margin-top: 0 !important;
  top: 50%;
  transform: translateY(-50%);
}
::v-deep .el-dialog__header {
  border-bottom: 1px solid #ebeef5;
  padding: 16px 24px;
  position: relative;
  .el-dialog__headerbtn {
    top: 50%;
    transform: translateY(-50%);
  }
}
::v-deep .el-dialog__footer {
  padding: 12px 20px;
  border-top: 1px solid #ebeef5;
}
</style>
