<template>
  <el-cascader
    ref="cascader"
    v-model="selectedValue"
    :props="cascaderProps"
    :placeholder="placeholder"
    style="width: 100%"
    clearable
    collapse-tags
    @change="handleChange"
  />
</template>

<script>
import { listDepartmentChildren } from "../api";

export default {
  name: "DepartmentTreeSelect",
  props: {
    value: { type: [String, Number], default: "" },
    names: { type: String, default: "" },
    multiple: { type: Boolean, default: false },
    placeholder: { type: String, default: "请选择部门" },
  },
  data() {
    return {
      selectedValue: this.toSelectedValue(this.value),
      cascaderProps: {
        lazy: true,
        multiple: this.multiple,
        checkStrictly: true,
        emitPath: false,
        value: "deptId",
        label: "deptName",
        leaf: "isLeaf",
        lazyLoad: this.loadDepartments,
      },
    };
  },
  watch: {
    value(value) {
      this.selectedValue = this.toSelectedValue(value);
    },
  },
  methods: {
    toSelectedValue(value) {
      const values = String(value || "").split(",").filter(Boolean);
      return this.multiple ? values : values[0] || "";
    },
    async loadDepartments(node, resolve) {
      try {
        const parentId = node.level === 0 ? null : node.value;
        const res = await listDepartmentChildren(parentId);
        resolve((res.data || []).map((item) => ({
          ...item,
          deptId: String(item.deptId),
          isLeaf: !item.hasChildren,
        })));
      } catch (error) {
        resolve([]);
        this.$message.error(error.message || "部门加载失败");
      }
    },
    handleChange(value) {
      const panel = this.$refs.cascader && this.$refs.cascader.getCheckedNodes;
      const nodes = panel ? this.$refs.cascader.getCheckedNodes(false) : [];
      const selectedCodes = this.multiple
        ? (value || []).map(String)
        : value ? [String(value)] : [];
      const nameMap = {};
      nodes.forEach((node) => { nameMap[String(node.value)] = node.label; });
      const oldCodes = String(this.value || "").split(",");
      const oldNames = String(this.names || "").split(",");
      oldCodes.forEach((code, index) => {
        if (code && !nameMap[code]) nameMap[code] = oldNames[index] || code;
      });
      const names = selectedCodes.map((code) => nameMap[code] || code);
      this.$emit("input", selectedCodes.join(","));
      this.$emit("update:names", names.join(","));
      this.$emit("change", { codes: selectedCodes.join(","), names: names.join(",") });
    },
  },
};
</script>
