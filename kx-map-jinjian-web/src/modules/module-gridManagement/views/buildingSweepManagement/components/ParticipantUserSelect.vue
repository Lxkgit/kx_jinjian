<template>
  <el-select
    v-model="selectedIds"
    multiple
    filterable
    remote
    clearable
    collapse-tags
    :disabled="!rootDeptIds.length"
    :remote-method="searchUsers"
    :loading="loading"
    placeholder="请选择参与排查人员"
    style="width: 100%"
    @visible-change="handleVisibleChange"
    @change="handleChange"
  >
    <el-option
      v-for="item in options"
      :key="item.userName"
      :label="userLabel(item)"
      :value="item.userName"
    />
  </el-select>
</template>

<script>
import { listDepartmentUsers } from "../api";

export default {
  name: "ParticipantUserSelect",
  props: {
    value: { type: String, default: "" },
    names: { type: String, default: "" },
    departmentCodes: { type: String, default: "" },
  },
  data() {
    return {
      selectedIds: this.split(this.value),
      options: [],
      loading: false,
    };
  },
  computed: {
    rootDeptIds() { return this.split(this.departmentCodes); },
  },
  watch: {
    value(value) {
      this.selectedIds = this.split(value);
      this.ensureSelectedOptions();
    },
  },
  created() { this.ensureSelectedOptions(); },
  methods: {
    split(value) { return String(value || "").split(",").filter(Boolean); },
    ensureSelectedOptions() {
      const names = this.split(this.names);
      this.selectedIds.forEach((id, index) => {
        if (!this.options.some((item) => item.userName === id)) {
          this.options.push({ userName: id, nickName: names[index] || id, selected: true });
        }
      });
    },
    handleVisibleChange(visible) {
      if (visible && this.rootDeptIds.length) this.searchUsers("");
    },
    async searchUsers(keyword = "") {
      if (!this.rootDeptIds.length) return;
      this.loading = true;
      try {
        const res = await listDepartmentUsers(this.rootDeptIds, keyword.trim());
        const selected = this.options.filter((item) => this.selectedIds.includes(item.userName));
        const loaded = res.data || [];
        const map = {};
        [...selected, ...loaded].forEach((item) => { map[item.userName] = item; });
        this.options = Object.keys(map).map((key) => map[key]);
      } finally {
        this.loading = false;
      }
    },
    userLabel(item) {
      const name = item.nickName || item.userName;
      return item.userName && item.userName !== name ? `${name}（${item.userName}）` : name;
    },
    handleChange(ids) {
      const names = ids.map((id) => {
        const item = this.options.find((option) => option.userName === id);
        return item ? (item.nickName || item.userName || String(id)) : String(id);
      });
      this.$emit("input", ids.join(","));
      this.$emit("update:names", names.join(","));
    },
  },
};
</script>
