<template>
  <i-dialog
    v-model="showDiolag"
    width="50%"
    :title="`${status == 'add' ? '新增' : '编辑'}事件`"
    :appendToBody="true"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
  >
    <el-form
      :model="form"
      :rules="rules"
      ref="complaintForm"
      label-width="150px"
      class="complaint-form"
    >
      <!-- 投诉人 -->
      <el-form-item label="投诉人" prop="complainant">
        <el-input
          v-model="form.complainant"
          placeholder="请输入投诉人"
        ></el-input>
      </el-form-item>

      <!-- 投诉人联系电话 -->
      <el-form-item label="投诉人联系电话" prop="complainantPhone">
        <el-input
          v-model="form.complainantPhone"
          placeholder="请输入联系电话"
        ></el-input>
      </el-form-item>

      <!-- 投诉来源 -->
      <el-form-item label="投诉来源" prop="complainantType">
        <el-select
          v-model="form.complainantType"
          placeholder="请选择投诉类型"
          style="width: 100%"
        >
          <el-option
            v-for="item in complaintType"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <!-- 投诉时间 -->
      <el-form-item label="投诉时间" prop="complainTime">
        <el-date-picker
          v-model="form.complainTime"
          type="datetime"
          placeholder="选择投诉时间"
          format="yyyy-MM-dd HH:mm:ss"
          value-format="yyyy-MM-dd HH:mm:ss"
          style="width: 100%"
        ></el-date-picker>
      </el-form-item>

      <!-- 投诉事件描述 -->
      <el-form-item label="投诉事件描述" prop="description">
        <el-input
          type="textarea"
          v-model="form.description"
          placeholder="请输入投诉事件描述"
          rows="4"
        ></el-input>
      </el-form-item>

      <!-- 处理人 -->
      <el-form-item label="网点联络员" prop="handler">
        <el-input
          v-model="form.handler"
          placeholder="请输入网点联络员"
        ></el-input>
      </el-form-item>

      <!-- 处理人联系电话 -->
      <el-form-item label="联络员联系电话" prop="handlerPhone">
        <el-input
          v-model="form.handlerPhone"
          placeholder="请输入联络员联系电话"
        ></el-input>
      </el-form-item>

      <el-form-item label="上级负责人名称" prop="superiorsName">
        <el-input
          v-model="form.superiorsName"
          placeholder="请输入上级负责人名称"
        ></el-input>
      </el-form-item>

      <el-form-item label="上级负责人电话" prop="superiorsPhone">
        <el-input
          v-model="form.superiorsPhone"
          placeholder="请输入上级负责人电话"
        ></el-input>
      </el-form-item>
    </el-form>
  </i-dialog>
</template>

<script>
import { addInsertEven, updateEven } from "../api";
import pageMixin from "@/mixins/pageMixin";
export default {
  mixins: [pageMixin],
  dicts: ["complaint_source"],
  props: {
    value: {
      type: Boolean,
    },
    editInfo: {
      type: Object,
      default: () => {},
    },
    status: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      showDiolag: this.value,
      form: {
        complainant: "",
        complainantPhone: "",
        complainantType: "",
        complainTime: "",
        description: "",
        handler: "",
        handlerPhone: "",
      },
      rules: {
        complainant: [
          { required: true, message: "投诉人不能为空", trigger: "blur" },
        ],
        complainantPhone: [
          {
            required: true,
            message: "投诉人联系电话不能为空",
            trigger: "blur",
          },
          //   {
          //     pattern: /^1[3-9]\d{9}$/,
          //     message: "请输入有效的联系电话",
          //     trigger: "blur",
          //   },
        ],
        complainantType: [
          { required: true, message: "投诉类型不能为空", trigger: "blur" },
        ],
        complainTime: [
          { required: true, message: "投诉时间不能为空", trigger: "change" },
        ],
        description: [
          { required: true, message: "投诉事件描述不能为空", trigger: "blur" },
        ],
        handler: [
          { required: true, message: "处理人不能为空", trigger: "blur" },
        ],
        handlerPhone: [
          {
            required: true,
            message: "处理人联系电话不能为空",
            trigger: "blur",
          },
        ],
        superiorsName: [
          {
            required: true,
            message: "处理人联系电话不能为空",
            trigger: "blur",
          },
        ],
        superiorsPhone: [
          {
            required: true,
            message: "处理人联系电话不能为空",
            trigger: "blur",
          },
        ],
      },
    };
  },
  computed: {
    customerParam() {
      return this.$store.getters["gridManagement/customerParam"];
    },
    complaintType() {
      return this.dict.type.complaint_source;
    },
  },
  watch: {
    value: {
      handler(val) {
        this.showDiolag = val;
        if (val) {
          this.initData();
        }
      },
    },
    showDiolag: {
      handler(val) {
        this.$emit("input", val);
      },
    },
  },
  methods: {
    initData() {
      this.form = JSON.parse(JSON.stringify(this.editInfo));
    },
    handleClose() {
      this.$refs.complaintForm.resetFields();
      this.showDiolag = false;
    },
    handleConfirm() {
      this.$refs.complaintForm.validate((valid) => {
        if (valid) {
          if (this.status === "add") {
            const param = {
              ...this.form,
              customerName: this.customerParam.customerName,
            };
            addInsertEven(param).then((res) => {
              this.$modal.msgSuccess("添加成功");
              this.handleClose();
              this.$parent.getEvensAll();
              this.$parent.getEvensRecently();
            });
          } else {
            const param = {
              ...this.form,
            };
            updateEven(param).then((res) => {
              this.$modal.msgSuccess("编辑成功");
              this.handleClose();
              this.$parent.getEvensAll();
              this.$parent.getEvensRecently();
            });
          }
        }
      });
    },
  },
};
</script>

<style scoped>
</style>