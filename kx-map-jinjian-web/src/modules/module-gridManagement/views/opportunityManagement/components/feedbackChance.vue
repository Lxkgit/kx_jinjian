<template>
  <i-dialog
    :title="'商机反馈'"
    v-model="showFeedback"
    width="35%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="true"
  >
    <el-form
      :model="feedbackForm"
      ref="feedbackForm"
      :rules="rules"
      :label-width="formLabelWidth"
    >
      <el-row>
        <el-col :span="20">
          <!-- 选择商机阶段 -->
          <el-form-item label="商机阶段" prop="chanceStage">
            <el-select
              :size="size"
              class="form-select"
              v-model="feedbackForm.chanceStage"
              placeholder="请选择商机阶段"
            >
              <el-option
                v-for="item in stageOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="20">
          <!-- 备注 -->
          <el-form-item label="备注" prop="remark">
            <el-input
              type="textarea"
              v-model="feedbackForm.remark"
              placeholder="请输入备注内容"
              :rows="2"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </i-dialog>
</template>

<script>
export default {
  name: "feedbackChance",
  props: {
    value: {
      type: Boolean,
      default: false,
      required: true,
    },
    defaultValue: {
      type: Object,
      default: () => {},
    },
  },
  data() {
    return {
      size: "mini",
      feedbackForm: {
        chanceStage: "",
        remark: "",
      },
      rules: {
        chanceStage: [
          {
            required: true,
            message: "请输入类型名称",
            trigger: ["change", "blur"],
          },
        ],
      },
      stageOptions: [
        {
          value: "0",
          label: "初期沟通",
        },
        {
          value: "1",
          label: "确定意向需求",
        },
        {
          value: "2",
          label: "关键人认同",
        },
        {
          value: "3",
          label: "商务谈判",
        },
        {
          value: "4",
          label: "签约落地",
        },
      ],
      formLabelWidth: "120px",
      showFeedback: false,
    };
  },
  watch: {
    value: {
      handler(val) {
        this.showFeedback = val;
      },
      immediate: true,
    },
    showFeedback: {
      handler(val) {
        this.$emit("input", val);
      },
    },
    defaultValue: {
      handler(val) {
        if (val) {
          this.feedbackForm = val;
        }
      },
    },
  },
  methods: {
    // 弹窗确认按钮
    handleConfirm() {
      this.$refs.feedbackForm.validate((valid) => {
        if (valid) {
          console.log("确认", this.feedbackForm);
          this.showFeedback = false;
        }
      });
    },
    // 关闭弹窗
    handleClose() {
      this.$refs.feedbackForm.resetFields();
    },
  },
};
</script>

<style scoped lang="scss">
</style>