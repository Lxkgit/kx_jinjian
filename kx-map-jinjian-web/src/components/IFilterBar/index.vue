<template>
  <div
    class="filter-bar-wrapper"
    :style="{ '--gridColumns': `repeat(${gridColumns},1fr)` }"
  >
    <div
      v-for="item in columns.filter((i) => !getFunOrValue(i.hidden))"
      :key="item.key"
      :style="{ gridColumnStart: gridStyle(item.span) }"
      class="filter-item-wrapper"
    >
      <span
        v-if="item.label"
        :style="{ '--labelWidth': labelWidth, '--labelAlign': labelAlign }"
        class="filter-item-label"
        >{{ item.label }}</span
      >
      <filter-item
        v-model="formInfo[item.key]"
        :info="item"
        @eventFun="eventFun($event)"
      />
    </div>
    <div
      class="filter-button-wrapper"
      :style="{ gridColumnStart: gridStyle(buttonSpan) }"
    >
      <slot />
    </div>
  </div>
</template>

<script>
import filterItem from "./components/filterItem";
export default {
  name: "IFilterBar",
  components: {
    filterItem,
  },
  props: {
    columns: {
      type: Array,
      default: () => [],
    },
    buttonSpan: {
      type: Number,
      default: 1,
    },
    labelWidth: {
      type: String,
      default: "none",
    },
    labelAlign: {
      type: String,
      default: "right",
    },
    gridColumns: {
      type: Number,
      default: 4,
    },
    defaultValue: {
      type: Object,
      default() {
        return {};
      },
    },
  },
  data() {
    return {
      formInfo: {}, //表单数据
    };
  },
  created() {
    this.reset();
  },
  watch: {
    defaultValue(v) {
      this.formInfo = Object.assign(this.formInfo, v);
    },
  },
  methods: {
    gridStyle(span) {
      return `span ${span || 1}`;
    },
    getForm(cb) {
      cb && cb(this.formInfo);
    },
    reset() {
      for (let item of this.columns) {
        if (item.type === "doubleSelect") {
          this.$set(this.formInfo, item.key, { select1: "0", select2: "" });
        } else if (item.type == "composeInput") {
          this.$set(this.formInfo, item.key, { select: "0", input: "" });
        } else {
          this.$set(
            this.formInfo,
            item.key,
            (item.value && JSON.parse(JSON.stringify(item.value))) || undefined
          );
        }
      }
    },
    // 组件事件处理
    eventFun(evt) {
      this.$emit("eventFun", evt);
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
.filter-bar-wrapper {
  width: 100%;
  display: grid;
  gap: 16px;
  grid-template-columns: var(--gridColumns);
  // margin-bottom: 16px;
  .filter-item-wrapper {
    width: 100%;
    display: flex;
    align-items: center;
    margin-right: 24px;
    .filter-item-label {
      display: inline-block;
      width: var(--labelWidth);
      text-align: var(--labelAlign);
      padding-right: 10px;
      font-size: 16px;
      font-weight: 400;
      color: #333333;
    }
  }
}
</style>
