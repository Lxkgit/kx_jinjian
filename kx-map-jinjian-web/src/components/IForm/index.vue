<template>
  <div class="form-wrapper">
    <el-form
      ref="iForm"
      :style="{ '--gridColumns': `repeat(${gridColumns}, 1fr)` }"
      :model="formInfo"
      :rules="formRules"
      :label-width="labelWidth"
      :label-position="labelPosition"
      :inline="inline"
      :label-suffix="labelSuffix"
    >
      <el-form-item
        v-for="item in columns.filter((i) => !getFunOrValue(i.hidden))"
        :key="item.key || item.label"
        :prop="item.key"
        :label="item.label || ''"
        :style="{ gridColumnStart: gridStyle(item.span) || '' }"
        :class="{ required: item.required }"
        :label-width="item.labelWidth || ''"
      >
        <form-item-tool
          v-if="item.type !== 'slot'"
          :value="formInfo"
          :info="item"
          @eventFun="eventFun($event)"
        >
          <template #customerAddress>
            <span class="tips">地址示例：**省**市**区/县**街道**号**</span>
          </template>
          <template #tip>
            <span class="tips">{{ item.tip }}</span>
          </template>
        </form-item-tool>
        <slot v-else :name="item.slotName" />
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import FormItemTool from "./components/FormItemTool";
export default {
  name: "IForm",
  components: {
    FormItemTool,
  },
  props: {
    gridColumns: {
      type: Number,
      default: 1,
    },
    columns: {
      //表单配置
      type: Array,
      default: () => [],
    },
    inline: {
      //行内表单模式
      type: Boolean,
      default: false,
    },
    labelWidth: {
      type: String,
      default: "100px",
    },
    labelPosition: {
      type: String,
      default: "left",
    },
    labelSuffix: {
      //表单标签后缀
      type: String,
      default: "",
    },
  },
  data() {
    return {
      formInfo: {}, //表单数据
      formRules: {}, //校验规则
    };
  },
  created() {
    this.loadForm();
  },
  methods: {
    // 加载表单
    loadForm() {
      this.columns.forEach((item) => {
        this.$set(this.formInfo, item.key, item.value);
        if (item.rules) {
          if (item.rules.length) {
            item.rules = item.rules.map((rule) => {
              if (
                rule.validator &&
                Object.prototype.toString.call(rule.validator) ===
                  "[object Function]"
              ) {
                rule.validator = rule.validator.bind(this);
              }
              return rule;
            });
          }
          this.formRules[item.key] = item.rules;
        }
        if (item.key == "customerAddress") {
          item.slot = true;
        }
      });
    },
    // 表单校验,获取数据
    getForm(cb) {
      let res = false;
      this.$refs.iForm.validate((val) => {
        if (val) {
          res = true;
          cb && cb(this.formInfo);
        }
      });
      return res;
    },
    getFormWithoutRule(cb) {
      cb && cb(this.formInfo);
    },
    reset() {
      for (let item of this.columns) {
        this.$set(this.formInfo, item.key, item.value || undefined);
      }
      this.clearValidate();
    },
    clearValidate() {
      this.$nextTick(() => {
        this.$refs.iForm.clearValidate();
      });
    },
    // 设置表单数据
    // 键值对
    setForm(data) {
      const keys = Object.keys(data);
      this.columns.forEach((item) => {
        if (keys.includes(item.key)) {
          this.$set(
            this.formInfo,
            item.key,
            item.type === "number" ? Number(data[item.key]) : data[item.key]
          );
        }
      });
      this.clearValidate();
    },
    // 组件事件处理
    eventFun(evt) {
      this.$emit("eventFun", evt);
    },
    // 栅格布局格式
    gridStyle(span) {
      return `span ${span}`;
    },
    getFunOrValue(fun) {
      if (typeof fun === "function") {
        return fun.call();
      } else {
        return fun;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
::v-deep .el-form {
  display: grid;
  grid-template-columns: var(--gridColumns);
  align-items: start;
  .el-form-item {
    margin-right: 16px;
  }
  .required {
    > .el-form-item__label:before {
      content: "*";
      color: #f56c6c;
      margin-right: 4px;
    }
  }
}
.tips {
  font-size: 12px;
  color: #999;
}
::v-deep .el-textarea__inner {
  height: 70px;
}
</style>
