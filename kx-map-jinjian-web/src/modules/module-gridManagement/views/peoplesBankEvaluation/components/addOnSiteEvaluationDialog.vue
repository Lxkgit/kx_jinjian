<template>
  <el-dialog
    title="新增年度现场评价"
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
      <el-form-item label="评价标题" prop="title">
        <el-input
          class="form-item-line"
          v-model="formData.title"
          placeholder="请输入评价标题"
          type="text"
          style="width: 100%"
        >
        </el-input>
      </el-form-item>

      <el-form-item label="评价年度" prop="year">
        <el-date-picker
          style="width: 100%"
          v-model="formData.year"
          type="year"
          placeholder="请选择评价年度"
          :default-value="2025"
        />
      </el-form-item>

      <el-form-item label="评价对象地市" prop="address">
        <el-select v-model="formData.address" style="width: 100%">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="评价参与机构"> </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary">确 定</el-button>
      <el-button @click="handleClose">取 消</el-button>
    </span>
  </el-dialog>
</template>
<script>
export default {
  name: "addOnSiteEvaluationDialog",
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      show: false,
      formData: { customerSource: "", year: "2025" },
      options: [],
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
