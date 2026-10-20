<template>
  <div class="filter-item-box">
    <!-- 文本 -->
    <el-input
      v-if="inputTypes.includes(info.type)"
      v-model="itemValue"
      :type="info.type"
      :placeholder="info.placeholder || `请输入${info.label}`"
      :rows="info.rows || 4"
      :disabled="info.disabled"
      :readonly="info.readonly"
      :clearable="info.clearable"
      class="filter-item"
    />
    <!-- 数字框 -->
    <el-input-number
      v-if="info.type === 'number'"
      v-model="itemValue"
      :placeholder="info.placeholder || `请输入${info.label}`"
      :precision="info.precision || 0"
      :controls="false"
      :disabled="info.disabled"
      :readonly="info.readonly"
      class="filter-item"
    />
    <!-- 带输入建议 -->
    <el-autocomplete
      v-if="info.type === 'autocomplete'"
      v-model="itemValue"
      :placeholder="info.placeholder || `请输入${info.label}`"
      :disabled="info.disabled"
      :readonly="info.readonly"
      class="filter-item"
      :value-key="info.valueKey"
      :trigger-on-focus="info.triggerOnFocus"
      :fetch-suggestions="info.querySearch"
      @select="
        info.events && info.events.select
          ? eventFun({ type: 'select', key: info.key, val: $event })
          : null
      "
    />
    <!-- 下拉框 -->
    <el-select
      v-if="info.type === 'select'"
      v-model="itemValue"
      :multiple="info.multiple || false"
      :collapse-tags="info.collapseTags || false"
      :multiple-limit="info.multipleLimit || 0"
      :placeholder="info.placeholder || `请选择${info.label}`"
      :disabled="info.disabled"
      :clearable="info.clearable"
      :filterable="info.filterable"
      :remote="info.remote"
      :loading="info.loading"
      :remote-method="info.remoteMethod"
      class="filter-item"
      @change="
        info.events && info.events.change
          ? eventFun({ type: 'change', key: info.key, val: $event })
          : null
      "
    >
      <el-option
        v-for="item in info.options || []"
        :key="item.value"
        :label="item.label"
        :value="item.value"
      />
    </el-select>

    <!-- 日期选择器 -->
    <el-date-picker
      v-if="datePickerTypes.includes(info.type)"
      v-model="itemValue"
      :type="info.type"
      :format="info.format || 'yyyy-MM-dd'"
      :disabled="info.disabled"
      :value-format="info.valueFormat || 'yyyy-MM-dd HH:mm:ss'"
      :readonly="info.readonly"
      :clearable="info.clearable"
      :unlink-panels="info.unlinkPanels || false"
      :start-placeholder="info.startPlaceholder"
      :end-placeholder="info.endPlaceholder"
      :range-separator="info.rangeSeparator"
      :picker-options="info.pickerOptions || {}"
      class="filter-item"
      :placeholder="info.placeholder || `请选择${info.label}`"
    />
    <!-- 级联选择器 -->
    <el-cascader
      v-if="info.type === 'cascader'"
      v-model="itemValue"
      :clearable="info.clearable"
      :options="typeof info.options === 'function' ? [] : info.options"
      :collapse-tags="info.collapseTags"
      :props="info.props"
      class="filter-item"
      :filterable="info.filterable"
      :placeholder="info.placeholder || `请选择${info.label}`"
      :show-all-levels="info.showAllLevels"
      @change="
        info.events && info.events.change
          ? eventFun({ type: 'change', key: info.key, val: $event })
          : null
      "
    />
    <!-- 树形框 -->
    <tree-select
      v-if="info.type === 'treeselect'"
      v-model="itemValue"
      :options="typeof info.options === 'function' ? [] : info.options"
      :placeholder="info.placeholder || `请选择${info.label}`"
      :normalizer="info.normalizer"
      :multiple="info.multiple"
      @select="
        info.events && info.events.select
          ? eventFun({ type: 'select', key: info.key, val: $event })
          : null
      "
    />
    <!-- 组合input -->
    <el-input
      v-if="info.type === 'composeInput'"
      placeholder="请输入内容"
      v-model="itemValue.input"
      class="input-with-select"
    >
      <el-select
        v-model="itemValue.select"
        slot="prepend"
        placeholder="请选择"
        style="width: 120px"
      >
        <el-option
          v-for="item in info.options || []"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </el-input>
    <!-- 双选择器 -->
    <div v-if="info.type === 'doubleSelect'" class="filter-item">
      <el-select
        v-model="itemValue.select1"
        :multiple="info.multiple || false"
        :collapse-tags="info.collapseTags || false"
        :multiple-limit="info.multipleLimit || 0"
        :placeholder="info.placeholder1 || '请选择'"
        :disabled="info.disabled"
        :clearable="info.clearable || false"
        :filterable="info.filterable"
        :remote="info.remote"
        :loading="info.loading"
        :remote-method="info.remoteMethod"
        style="width: 50%"
        @change="
          info.events && info.events.change
            ? eventFun({ type: 'change', key: info.key, val: $event })
            : null
        "
      >
        <el-option
          v-for="item in info.options1 || []"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <el-select
        v-model="itemValue.select2"
        :multiple="info.multiple || false"
        :collapse-tags="info.collapseTags || false"
        :multiple-limit="info.multipleLimit || 0"
        :placeholder="info.placeholder2 || '请选择'"
        :disabled="info.disabled"
        :clearable="info.clearable || false"
        :filterable="info.filterable"
        :remote="info.remote"
        :loading="info.loading"
        :remote-method="info.remoteMethod"
        style="width: 50%"
        @change="
          info.events && info.events.change
            ? eventFun({ type: 'change', key: info.key, val: $event })
            : null
        "
      >
        <el-option
          v-for="item in info.options1[Number(itemValue.select1)].options || []"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
    </div>

    <!-- 数据区间 -->
    <MinMaxInput
      v-if="info.type === 'minmax'"
      v-model="itemValue"
    ></MinMaxInput>
  </div>
</template>

<script>
import TreeSelect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import MinMaxInput from "../cell/MinMaxInput";
export default {
  components: {
    TreeSelect,
    MinMaxInput,
  },
  props: {
    value: {
      type: [String, Number, Object, Array, Boolean, Date],
      default: null,
    },
    info: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {
      itemValue: null,
      inputTypes: ["text", "password", "textarea", "url", "email"],
      datePickerTypes: [
        "year",
        "month",
        "date",
        "dates",
        "months",
        "years",
        "week",
        "datetime",
        "datetimerange",
        "daterange",
        "monthrange",
      ],
    };
  },
  watch: {
    value: {
      handler(val) {
        this.itemValue = val;
      },
      deep: true,
    },
    itemValue: {
      handler(val) {
        this.$emit("input", val);
      },
      deep: true,
    },
  },
  async created() {
    this.itemValue =
      this.info.type === "number" ? Number(this.value) : this.value;
    const _toString = Object.prototype.toString;
    if (
      ["object Array", "object Object"].includes(_toString.call(this.value))
    ) {
      this.itemValue = JSON.parse(JSON.stringify(this.value));
    }
    // 数据字典直接提取值
    if (typeof this.info.options === "function") {
      const res = await this.info.options.call(this);
      this.info.options = res;
    }
    if (this.info.type == "doubleSelect") {
      this.info.options1.forEach(async (item) => {
        if (typeof item.options === "function") {
          const res = await item.options.call(this);
          item.options = res;
        }
      });
    }
  },
  methods: {
    /*
     * 事件回传
     * { type: 事件类型 key:属性名称 val:属性值 }
     */
    eventFun(obj) {
      this.$emit("eventFun", obj);
    },
  },
};
</script>

<style lang="scss" scoped>
.filter-item-box {
  flex: 1;
  .filter-item {
    width: 100%;
  }
}
</style>
