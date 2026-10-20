<template>
  <div>
    <el-form-item
      label="网格名称"
      :prop="`${preName}${index}name`"
      :rules="rules.name"
      class="dislpay-item"
    >
      <el-input
        v-model="formItem.name"
        @focus="focusHandle(formItem)"
        @blur="blurHandle()"
        @change="changeHandle(formItem)"
      >
      </el-input>
    </el-form-item>
    <el-form-item
      label="网格类型"
      :prop="`${preName}${index}ruralGridType`"
      :rules="mapStatus === '1' ? {} : rules.ruralGridType"
      class="dislpay-item"
    >
      <el-select v-model="formItem.ruralGridType" placeholder="请选择网格类型">
        <el-option
          v-for="item in dict.type.new_grid_type"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="网格等级" prop="addrLevel" class="dislpay-item">
      <el-input v-model="formItem.addrLevel" disabled></el-input>
    </el-form-item>
    <el-form-item label="上级网格" prop="parentName" class="dislpay-item">
      <el-input v-model="formItem.parentName" disabled></el-input>
    </el-form-item>
    <el-form-item label="下级网格数" prop="parentName1" class="dislpay-item">
      <el-input v-model="formItem.parentName1" disabled></el-input>
    </el-form-item>
    <!-- <el-form-item label="下级网格数" prop="childNum">
      <el-input v-model="formItem.childNum" disabled></el-input>
    </el-form-item> -->
    <el-form-item label="责任网点" prop="deptId" class="dislpay-item">
      <el-cascader
        v-model="formItem.deptId"
        :options="orgOptions"
        :props="props"
        :show-all-levels="false"
        disabled
        placeholder=""
      >
      </el-cascader>
    </el-form-item>
    <el-form-item label="网格长" prop="userName" class="dislpay-item">
      <el-input v-model="formItem.userName" disabled></el-input>
    </el-form-item>
    <!-- <el-form-item label="网格副管员" prop="secondUserName">
      <el-input v-model="formItem.secondUserName" disabled></el-input>
    </el-form-item> -->
  </div>
</template>

<script>
export default {
  dicts: ["new_grid_type"],
  props: {
    value: {
      type: Object,
    },
    orgOptions: {
      type: Array,
    },
    preName: {
      type: String,
      default: "",
    },
    index: {
      type: String,
      default: "",
    },
    mapStatus: {
      type: String,
    },
  },
  data() {
    return {
      rules: {
        name: [{ required: true, message: "请输入网格名称" }],
        ruralGridType: [
          { required: true, message: "请选择网格类型", trigger: "change" },
        ],
      },
      formItem: this.value,
      props: {
        value: "id",
        emitPath: false,
        checkStrictly: true,
      },
    };
  },
  watch: {
    value: {
      handler(val) {
        this.formItem = val;
      },
      deep: true,
    },
    formItem: {
      handler(val) {
        this.$emit("input", val);
      },
      deep: true,
    },
  },
  methods: {
    focusHandle(formItem) {
      this.$emit("focusHandle", formItem);
    },
    blurHandle() {
      this.$emit("blurHandle");
    },
    changeHandle(formItem) {
      this.$emit("changeHandle", formItem);
    },
  },
};
</script>

<style lang="scss" scoped>
.dislpay-item {
  display: flex;
  flex-direction: column;
  margin-bottom: 5px !important;
  ::v-deep .el-form-item__content {
    margin:  0 !important;;
  }
  ::v-deep .el-select,.el-cascader {
    width: 100%;
  }
}
</style>