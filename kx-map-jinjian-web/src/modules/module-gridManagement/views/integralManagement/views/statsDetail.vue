<template>
  <i-dialog title="积分统计明细" v-model="showDialog" width="40%">
    <div class="customerInfo">
      <div class="infoItem" v-for="item in customField" :key="item.prop">
        <div class="label">{{ item.label }}</div>
        <div class="value">{{ item.value }}</div>
      </div>
    </div>
    <i-table
      border
      :loading="loading"
      :data="tableData"
      :max-height="maxHeight"
      align="center"
      stripe
      :allColumns="columns"
      :total="total"
      :pageNum="formInline.pageNum"
      :pageSize="formInline.pageSize"
      @currentChange="currentChange"
      @sizeChange="sizeChange"
    >
    </i-table>
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
      customField: [
        { label: 1, value: 1 },
        { label: 1, value: 1 },
        { label: 1, value: 1 },
      ],
      maxHeight: window.innerHeight - 500,
      columns: [
        { label: "积分动作", prop: "code" },
        { label: "积分情况", prop: "name" },
        { label: "积分时间", prop: "time" },
      ],
      tableData: [{}],
      loading: false,
      formInline: {
        pageSize: 10,
        pageNum: 1,
      },
      total: 0,
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
};
</script>

<style scoped lang="scss">
.customerInfo {
  padding: 10px 20px;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
  color: #999;
  .infoItem {
    display: grid;
    grid-template-columns: 70px 1fr;
    gap: 10px;
  }
}
</style>
