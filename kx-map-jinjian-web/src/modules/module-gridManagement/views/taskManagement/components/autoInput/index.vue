<template>
  <div class="auto-input">
    <div class="title">{{ label }}</div>
    <el-autocomplete
      ref="autocomplete"
      class="inline-input"
      v-model="searchValue"
      :value-key="valueKey"
      :fetch-suggestions="querySearch"
      :placeholder="placeholder"
      :debounce="0"
      :clearable="true"
      :prefix-icon="'el-icon-search'"
      @select="handleSelect"
      @clear="handleClear"
    ></el-autocomplete>
  </div>
</template>

<script>
export default {
  name: "autoInput",
  props: {
    // 建议列表
    suggest: {
      type: Array,
      default: () => [
        {
          value: "张三",
          age: 16,
        },
        {
          value: "李四",
          age: 18,
        },
        {
          value: "张三李四",
          age: 25,
        },
        {
          value: "王五",
          age: 20,
        },
        {
          value: "赵六",
          age: 30,
        },
        {
          value: "王五赵六",
          age: 40,
        },
      ],
    },
    // 标题
    label: {
      type: String,
      default: "标题",
    },
    // 提示信息
    placeholder: {
      type: String,
      default: "请输入要搜索的内容",
    },
    // 输入建议对象中用于显示的键名
    valueKey: {
      type: String,
      default: "value",
    },
  },
  data() {
    return {
      searchValue: "",
      timer: null,
    };
  },
  watch: {
    searchValue(newVal) {
      if (this.timer) {
        clearTimeout(this.timer);
      }
      this.timer = setTimeout(() => {
        this.$emit("valueChange", newVal);
      }, 750);
    },
  },
  methods: {
    querySearch(queryString, cb) {
      let suggestList = this.suggest;
      let results = queryString
        ? suggestList.filter(this.createFilter(queryString))
        : suggestList;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },
    createFilter(queryString) {
      return (searchValue) => {
        return (
          searchValue[this.valueKey]
            .toLowerCase()
            .indexOf(queryString.toLowerCase()) === 0
        );
      };
    },
    handleSelect(item) {
      this.$emit("handleSelect", item);
    },
    // 点击清除按钮的时候
    handleClear() {
      this.$refs.autocomplete.activated = false;
    },
  },
};
</script>

<style lang="scss" scoped>
.auto-input {
  // border: 1px solid red;
  display: flex;
  align-items: center;
  .title {
    font-size: 16px;
    margin-right: 10px;
    color: #333333;
    font-weight: bold;
  }
}
</style>