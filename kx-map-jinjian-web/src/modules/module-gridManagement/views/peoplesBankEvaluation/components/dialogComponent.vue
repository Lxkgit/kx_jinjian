<template>
  <el-dialog
    :title="title"
    :visible.sync="show"
    width="50%"
    :before-close="handleClose"
    append
  >
    <!--:rules="rules"  -->
    <el-form
      ref="formData"
      :disabled="isReadonlyForm"
      :model="formData"
      label-position="right"
      label-width="140px"
    >
      <el-form-item
        label="评价标题"
        prop="title"
        :style="{ 'grid-column': 'span 2' }"
      >
        <el-input
          class="form-item-line"
          v-model="formData.title"
          placeholder="请输入评价标题"
          type="text"
          style="width: 80%"
        >
        </el-input>
      </el-form-item>

      <el-form-item
        label="评价年度"
        prop="customerSource"
        :style="{ 'grid-column': 'span 2' }"
      >
        <el-select
          v-model="formData.customerSource"
          placeholder="请选择年度"
          style="width: 80%"
        >
          <el-option
            v-for="item in dict.type.customer_source_type"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label-width="40px" :style="{ 'grid-column': 'span 2' }">
        <p class="form-item-line">
          <span>评价银行机构类型占比设置</span>
          <span>引用上一年度占比</span>
        </p>
      </el-form-item>

      <el-form-item label="国有商业银行" prop="title">
        <el-input v-model="formData.title" placeholder="请输入" type="text">
          <template #suffix>%</template>
        </el-input>
      </el-form-item>

      <el-form-item label="股份制商业银行" prop="title">
        <el-input v-model="formData.title" placeholder="请输入" type="text">
          <template #suffix>%</template>
        </el-input>
      </el-form-item>

      <el-form-item label="中国邮政储蓄银行" prop="title">
        <el-input v-model="formData.title" placeholder="请输入" type="text">
          <template #suffix>%</template>
        </el-input>
      </el-form-item>

      <el-form-item label="市商业银行" prop="title">
        <el-input v-model="formData.title" placeholder="请输入" type="text">
          <template #suffix>%</template>
        </el-input>
      </el-form-item>

      <el-form-item label="农村商业银行" prop="title">
        <el-input v-model="formData.title" placeholder="请输入" type="text">
          <template #suffix>%</template>
        </el-input>
      </el-form-item>

      <el-form-item label="村镇银行" prop="title">
        <el-input v-model="formData.title" placeholder="请输入" type="text">
          <template #suffix>%</template>
        </el-input>
      </el-form-item>

      <el-form-item label="外资银行" prop="title">
        <el-input v-model="formData.title" placeholder="请输入" type="text">
          <template #suffix>%</template>
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
  name: "dialogComponent",
  dicts: ["customer_source_type"],
  props: {
    dialogVisible: {
      type: Boolean,
      default: false,
    },
    title: {
      type: String,
      default: "提示",
    },
    isReadonlyForm: {
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
//表单相关
::v-deep .el-form {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
}
.form-item-line {
  span:first-child {
    font-size: 14px;
    line-height: 20px;
    color: #494d68;
    margin-right: 12px;
  }
  span:last-child {
    font-size: 14px;
    line-height: 20px;
    text-decoration: underline;
    text-underline-offset: 5px;
    color: #5e81f4;
    cursor: pointer;
  }
}
</style>
