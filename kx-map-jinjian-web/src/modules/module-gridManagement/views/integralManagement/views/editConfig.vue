<template>
  <i-dialog
    title="编辑积分规则"
    v-model="showDialog"
    width="40%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="true"
  >
    <el-form
      :model="addForm"
      ref="addForm"
      :rules="rules"
      :label-width="formLabelWidth"
    >
      <el-form-item label="动作" prop="action">
        <el-select
          :size="size"
          style="width: 100%"
          v-model="addForm.action"
          placeholder="请选择"
        >
          <el-option
            v-for="item in fs"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="积分规则" prop="integralRule">
        <el-input
          :size="size"
          v-model="addForm.integralRule"
          placeholder="请输入"
        >
          <el-select
            v-model="addForm.integralRule"
            slot="prepend"
            placeholder="请选择"
            style="width: 120px"
          >
            <el-option
              v-for="item in []"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-input>
      </el-form-item>
    </el-form>
  </i-dialog>
</template>

<script>
import pageMixin from "@/mixins/pageMixin.vue";
import services from "../api";
export default {
  name: "",
  mixins: [pageMixin],
  props: {
    value: { type: Boolean, default: false, required: true },
    defaultValue: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {
      size: "mini",
      addForm: {
        action: "",
        integralRule: "",
      },
      rules: {
        action: [
          {
            required: true,
            message: "请输入任务类型",
            trigger: ["change", "blur"],
          },
        ],
        integralRule: [
          {
            required: true,
            message: "请选择客户类型",
            trigger: ["change", "blur"],
          },
        ],
      },
      formLabelWidth: "120px",
      showDialog: false,
    };
  },
  watch: {
    value: {
      handler(val) {
        this.showDialog = val;
      },
      immediate: true,
    },
    showDialog: {
      handler(val) {
        this.$emit("input", val);
      },
    },
    defaultValue: {
      handler(val) {
        if (val) {
        }
      },
    },
  },
  methods: {
    // 新增编辑 提交表单
    handleConfirm() {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          let data = {
            ...this.addForm,
            list: this.tableData,
            taskObject: this.taskObject,
          };
        }
      });
    },
    //关闭弹窗
    handleClose() {
      this.$refs.addForm.resetFields();
    },
  },
};
</script>

<style scoped lang="scss"></style>
