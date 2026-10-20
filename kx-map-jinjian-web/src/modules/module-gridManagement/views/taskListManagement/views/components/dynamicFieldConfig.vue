<template>
    <div class="field-config">
      <el-form
        :model="formData"
        ref="dynamicForm"
        label-width="70px"
        class="dynamic-form"
      >
        <div
          v-for="(field, index) in formData.fields"
          :key="index"
          class="form-row"
        >
          <el-form-item label="字段名称">
            <el-input
              v-model="field.name"
              placeholder="请输入字段名称"
              style="width: 240px"
            ></el-input>
          </el-form-item>

          <el-form-item label="填写方式">
            <el-select
              style="width: 100px"
              v-model="field.method"
              placeholder="请选择"
            >
            </el-select>
          </el-form-item>
          <el-form-item label="域值">
            <el-input
              v-model="field.name"
              placeholder="请输入字段名称"
              style="width: 240px"
            ></el-input>
          </el-form-item>

          <el-form-item label="填写方式">
            <el-select
              style="width: 100px"
              v-model="field.method"
              placeholder="请选择"
            >
            </el-select>
          </el-form-item>

          <i class="el-icon-remove removeIcon" @click="removeField(index)"></i>
        </div>

        <div class="addField" @click="addField">
          <i class="el-icon-circle-plus-outline addIcon"></i>
          添加字段
        </div>
      </el-form>
    </div>
</template>

<script>
export default {
  name: "ElementDynamicForm",
  props:{
    fields:{ type:Array,default:()=> [] } 
  },
  data() {
    return {
      formData: {
        fields: [],
      },
    };
  },
  watch:{
    fields:{
      handler(val) {
        this.formData.fields = val;
      },
      immediate: true,
      deep:true
    }
  },
  methods: {
    addField() {
      this.formData.fields.push({
        name: "",
        method: "",
        value: "",
        options: "",
      });
    },
    removeField(index) {
      this.formData.fields.splice(index, 1);
    },
    submitForm() {
      this.$refs.dynamicForm.validate((valid) => {});
    },
  },
};
</script>

<style scoped lang="scss">
.dynamic-form {
  margin: auto;
  width: 1068px;
  padding: 16px;
  border-radius: 8px;
  background: #f6f7fa;
  max-height: 300px;
  overflow-y: auto;
}
.form-row {
  display: flex;
  align-items: flex-start;
}
.el-form-item {
  margin-right: 15px;
  margin-bottom: 33px;
}
.addField {
  width: 70px;
  font-size: 12px;
  color: #5e81f4;
}
.removeIcon {
  width: 16px;
  height: 16px;
  line-height: 36px;
  color: #bec1d6;
}
</style>
