<template>
  <i-dialog
    v-model="showDiolag"
    width="50%"
    :title="`${status == 'add' ? '新增' : '编辑'}事件`"
    :appendToBody="true"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showConfirm="status != 'edit'"
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
      <el-form-item label="投诉来源" prop="complaintSource">
        <el-select
          v-model="form.complaintSource"
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
      <el-form-item label="投诉时间" prop="complaintTime">
        <el-date-picker
          v-model="form.complaintTime"
          type="datetime"
          placeholder="选择投诉时间"
          format="yyyy-MM-dd HH:mm:ss"
          value-format="yyyy-MM-dd HH:mm:ss"
          style="width: 100%"
        ></el-date-picker>
      </el-form-item>

      <!-- 线索来源 -->
      <el-form-item label="线索来源" prop="leadSource">
        <el-select
          v-model="form.leadSource"
          placeholder="请选择线索来源"
          style="width: 100%"
        >
          <el-option
            v-for="item in leadSourceOpt"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>

      <!-- 行业 -->
      <el-form-item label="行业" prop="industry">
        <el-input v-model="form.industry" placeholder="请输入行业"></el-input>
      </el-form-item>

      <!-- 发生场景 -->
      <el-form-item label="发生场景" prop="scene">
        <el-input v-model="form.scene" placeholder="请输入发生场景"></el-input>
      </el-form-item>

      <!-- 是否为拒收人民币现金行为 -->
      <el-form-item label="是否为拒收人民币现金行为" prop="isCashRejection">
        <el-select
          v-model="form.isCashRejection"
          placeholder="请选择是否为拒收人民币现金行为"
          style="width: 100%"
        >
          <el-option label="是" value="1"> </el-option>
          <el-option label="否" value="0"> </el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="具体事由" prop="specificReason">
        <el-input
          type="textarea"
          v-model="form.specificReason"
          placeholder="请输入具体事由"
          rows="4"
        ></el-input>
      </el-form-item>

      <el-form-item label="处置情况" prop="disposalSituation">
        <el-input
          type="textarea"
          v-model="form.disposalSituation"
          placeholder="请输入处置情况"
          rows="4"
        ></el-input>
      </el-form-item>

      <!-- 投诉事件描述 -->
      <!-- <el-form-item label="投诉事件描述" prop="description">
        <el-input
          type="textarea"
          v-model="form.description"
          placeholder="请输入投诉事件描述"
          rows="4"
        ></el-input>
      </el-form-item> -->

      <!-- 处理人 -->
      <el-form-item label="网点联络员" prop="branchLiaison">
        <el-input
          v-model="form.branchLiaison"
          placeholder="请输入网点联络员"
        ></el-input>
      </el-form-item>

      <!-- 处理人联系电话 -->
      <el-form-item label="联络员联系电话" prop="branchLiaisonPhone">
        <el-input
          v-model="form.branchLiaisonPhone"
          placeholder="请输入联络员联系电话"
        ></el-input>
      </el-form-item>

      <el-form-item label="上级负责人名称" prop="superiorsName">
        <el-input
          v-model="form.superiorsName"
          placeholder="请输入上级负责人名称"
        ></el-input>
      </el-form-item>

      <el-form-item label="上级负责人电话" prop="superiorPhone">
        <el-input
          v-model="form.superiorPhone"
          placeholder="请输入上级负责人电话"
        ></el-input>
      </el-form-item>
    </el-form>
  </i-dialog>
</template>
  
  <script>
import { addComplaints, updateEven } from "../api";
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
        complaintSource: "",
        complaintTime: "",
        description: "",
        branchLiaison: "",
        branchLiaisonPhone: "",
        leadSource: "",
        industry: "",
        scene: "",
        isCashRejection: "",
        specificReason: "",
        disposalSituation: "",
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
        complaintSource: [
          { required: true, message: "投诉类型不能为空", trigger: "blur" },
        ],
        complaintTime: [
          { required: true, message: "投诉时间不能为空", trigger: "change" },
        ],
        description: [
          { required: true, message: "投诉事件描述不能为空", trigger: "blur" },
        ],
        branchLiaison: [
          { required: true, message: "处理人不能为空", trigger: "blur" },
        ],
        branchLiaisonPhone: [
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
        superiorPhone: [
          {
            required: true,
            message: "处理人联系电话不能为空",
            trigger: "blur",
          },
        ],
        leadSource: [
          { required: true, message: "投诉来源不能为空", trigger: "change" },
        ],
        industry: [
          { required: true, message: "行业不能为空", trigger: "change" },
        ],
        scene: [{ required: true, message: "场景不能为空", trigger: "change" }],
        isCashRejection: [
          {
            required: true,
            message: "是否拒绝现金不能为空",
            trigger: "change",
          },
        ],
        specificReason: [
          { required: true, message: "具体原因不能为空", trigger: "change" },
        ],
        disposalSituation: [
          { required: true, message: "处理情况不能为空", trigger: "change" },
        ],
      },
      leadSourceOpt: [
        { label: "投诉", value: "0" },
        { label: "舆情", value: "1" },
      ],
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
              anchorName: this.customerParam.customerName,
            };
            addComplaints(param).then((res) => {
              this.$modal.msgSuccess("添加成功");
              this.handleClose();
              this.$parent.getComplaint();
              this.$parent.getComplaintlist();
            });
          } else {
            const param = {
              ...this.form,
            };
            updateEven(param).then((res) => {
              this.$modal.msgSuccess("编辑成功");
              this.handleClose();
              this.$parent.getComplaint();
              this.$parent.getComplaintlist();
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