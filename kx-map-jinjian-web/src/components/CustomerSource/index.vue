<template>
  <div class="customer-source-form">
    <el-form
      ref="formData"
      :style="{ '--gridColumns': `repeat(${gridColumns}, 1fr)` }"
      :disabled="modelStatus === 'view'"
      :model="formData"
      :rules="rules"
      label-position="right"
      label-width="140px"
    >
      <el-form-item label="客户来源" prop="customerSource">
        <el-select
          class="form-item"
          v-model="formData.customerSource"
          placeholder="请选择客户来源"
        >
          <el-option
            v-for="item in dict.type.customer_source_type"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item
        v-if="['1', '2'].includes(formData.customerSource)"
        label="介绍人"
        prop="customerIntroducerIdentity"
      >
        <el-button class="search-btn" type="primary" @click="showDialog = true"
          >搜索</el-button
        >
        <i-table
          ref="table"
          :data="tableData"
          align="center"
          stripe
          :allColumns="columns"
          :showPagination="false"
        >
        </i-table>
      </el-form-item>
      <el-form-item
        label="营销活动"
        prop="marketAct"
        v-if="formData.customerSource == 3"
      >
        <el-select
          class="form-item"
          v-model="formData.marketAct"
          placeholder="请选择营销活动"
        >
          <el-option
            v-for="item in marketActOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          ></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <introducerDialog
      v-model="showDialog"
      @selectedHandle="selectedHandle"
    ></introducerDialog>
  </div>
</template>

<script>
import service from "./api";
import introducerDialog from "./components/introducerDialog";
export default {
  name: "CustomerSource",
  dicts: ["customer_source_type"],
  components: {
    introducerDialog,
  },
  props: {
    value: {
      type: Object,
    },
    gridColumns: {
      type: Number,
      default: 1,
    },
  },
  data() {
    return {
      showDialog: false,
      formData: {
        customerSource: "",
        customerIntroducerIdentity: "",
        marketAct: "",
      },
      marketActOptions: [
        { label: "普及金融知识万里行", value: "0" },
        { label: "做金融明白人-7月", value: "1" },
        { label: "信用卡营销-7月", value: "2" },
        { label: "渝快贷推广-7月", value: "3" },
        { label: "“毕业季 正青春”主题活动", value: "4" },
        { label: "收单营销活动", value: "5" },
        { label: "渝快贷推广", value: "6" },
      ],
      rules: {
        customerSource: [
          { required: true, message: "请选择客户来源", trigger: "change" },
        ],
        customerIntroducerIdentity: [
          { required: true, message: "请选择介绍人", trigger: "change" },
        ],
        marketAct: [
          { required: true, message: "请选择营销活动", trigger: "change" },
        ],
      },
      tableData: [],
      columns: [
        { label: "介绍人名称", prop: "name" },
        {
          label: "证件号",
          prop: "identity",
          formatter: ({ identity }) => {
            return this.$identityMasking(identity);
          },
        },
        {
          label: "手机号",
          prop: "phone",
          formatter: ({ phone }) => {
            return this.$phoneMasking(phone);
          },
        },
      ],
    };
  },
  computed: {
    modelStatus() {
      return this.$store.getters["gridManagement/modelStatus"];
    },
  },
  watch: {
    value: {
      handler(val) {
        this.formData = val;
      },
      deep: true,
    },
    formData: {
      handler(val) {
        this.$emit("input", val);
        this.fetchList();
      },
      deep: true,
    },
  },
  methods: {
    selectedHandle(val) {
      this.formData.customerIntroducerIdentity = `${val.identity},${val.phone}`;
    },
    fetchList() {
      if (this.formData.customerIntroducerIdentity) {
        const [identity, phone] =
          this.formData.customerIntroducerIdentity.split(",");
        const param = {
          identity,
          phone,
        };
        service.queryCustomer(param).then((res) => {
          const { data } = res;
          this.tableData = data;
        });
      } else {
        this.tableData = [];
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.customer-source-form {
  padding-right: 16px;
}
.form-item {
  width: 100%;
}
.search-btn {
  margin-bottom: 10px;
}
::v-deep .el-form {
  display: grid;
  grid-template-columns: var(--gridColumns);
}
::v-deep .el-checkbox__inner {
  border-color: #5572ec;
}
</style>
