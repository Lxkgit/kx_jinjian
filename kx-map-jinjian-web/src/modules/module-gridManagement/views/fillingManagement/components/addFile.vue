<template>
  <i-dialog
    :title="title"
    v-model="showDialog"
    width="50%"
    @handleClose="handleClose"
    :showConfirm="false"
  >
    <i-form
      ref="filterBar"
      :columns="searchColumns"
      @eventFun="eventFun"
      label-position="right"
      label-width="140px"
    >
    </i-form>
    <el-steps :active="activeStep" :align-center="true" class="step-box">
      <el-step title="第一步: 基础建档要素"></el-step>
      <el-step title="第二步: 有效建档要素"></el-step>
    </el-steps>
    <customerSource
      ref="customerSource"
      v-model="sourceData"
      v-show="activeStep === 0 && columns.length > 0"
    ></customerSource>
    <i-form
      v-if="columns.length > 0"
      ref="myForm"
      :columns="columns"
      label-position="right"
      label-width="140px"
    >
    </i-form>
    <template #foot>
      <span v-if="activeStep === 0" class="handle-wrapper">
        <el-button
          v-if="!editInfo.filingStatus || editInfo.filingStatus == '0'"
          v-loading="loading"
          @click="confirmHandle(1)"
          >暂 存</el-button
        >
        <el-button type="primary" @click="confirmHandle(0)" v-loading="loading"
          >下一步</el-button
        >
      </span>
      <span v-else class="handle-wrapper">
        <el-button type="primary" @click="backHandle">上一步</el-button>
        <el-button @click="confirmHandle(1)" v-loading="loading"
          >暂 存</el-button
        >
        <el-button @click="confirmHandle(0)" v-loading="loading"
          >保 存</el-button
        >
      </span>
    </template>
  </i-dialog>
</template> 

<script>
import service from "../api";
import dialogMixin from "@/mixins/dialogMixin";
import fileConversionMixin from "@/mixins/fileConversionMixin";
const STATUS_TYPE = {
  add: "新增",
  edit: "编辑",
};
export default {
  mixins: [dialogMixin, fileConversionMixin],
  dicts: ["table_model_type"],
  props: {
    editInfo: {
      type: Object,
    },
  },
  data() {
    return {
      searchColumns: [
        {
          label: "模版类型",
          key: "modelType",
          type: "select",
          options: () => this.dict.type.table_model_type,
          events: {
            change: true,
          },
          disabled: () => this.status !== "add",
          rules: [
            { required: true, trigger: "change", message: "模版类型不能为空" },
          ],
        },
      ],
      columns: [],
      sourceData: {
        customerSource: "",
        customerIntroducerIdentity: "",
        marketAct: "",
      },
      activeStep: 0,
      modelId: null,
      fillingId: null,
      customerModelType: null,
      loading: false,
    };
  },
  computed: {
    title() {
      return STATUS_TYPE[this.status];
    },
    status() {
      return this.$store.getters["gridManagement/modelStatus"];
    },
  },

  methods: {
    initData() {
      this.modelType = "3";
      this.activeStep = 0;
      if (this.status === "add") {
        this.fillingId = null;
        this.columns = [];
        return;
      }
      const { id, customerType, modelId } = this.editInfo;
      this.fillingId = id;
      this.customerModelType = customerType;
      this.modelId = modelId;
      this.$nextTick(() => {
        this.$refs.filterBar.setForm({ modelType: customerType });
      });
      this.iterator = this.setFormData();
      this.iterator.next();
    },
    *setFormData() {
      yield this.getFilingModelInfoById();
      yield this.getByModelIdAndFilingId();
    },
    getByModelIdAndFilingId() {
      const param = {
        modelId: this.modelId,
        filingId: this.fillingId,
      };
      service.getByModelIdAndFilingId(param).then((res) => {
        const {
          data: {
            customerSource,
            customerIntroducerIdentity,
            marketAct,
            filingMessageDetail: { jsonMessage, jsonFinish },
          },
        } = res;
        if (this.activeStep === 0) {
          this.sourceData.customerSource = customerSource;
          this.sourceData.customerIntroducerIdentity =
            customerIntroducerIdentity;
          this.sourceData.marketAct = marketAct;
          this.$refs.myForm.setForm(JSON.parse(jsonMessage));
        } else {
          this.$refs.myForm.setForm(JSON.parse(jsonFinish));
        }
      });
    },
    getFilingModelInfoById() {
      const param = {
        id: this.modelId,
        isFinishParam: this.activeStep,
      };
      service.getFilingModelInfoById(param).then((res) => {
        const {
          data: { fields, modelType },
        } = res;
        this.customerModelType = modelType;
        this.columns = [];
        this.$nextTick(() => {
          this.columns = this.dataConversion(fields);
          this.iterator && this.iterator.next();
        });
      });
    },
    handleClose() {
      this.$refs.filterBar.reset();
      this.sourceData = {
        customerSource: "",
        customerIntroducerIdentity: "",
        marketAct: "",
      };
      this.$nextTick(() => {
        this.$refs.customerSource.$refs.formData.clearValidate();
      });
      this.$parent.fetchList();
      this.showDialog = false;
    },
    backHandle() {
      this.activeStep = 0;
      this.iterator = this.setFormData();
      this.iterator.next();
    },
    getFirstParam(formInfo, type) {
      let param = null;
      this.$refs.customerSource.$refs.formData.validate((valid) => {
        if (valid) {
          const { customerSource, customerIntroducerIdentity, marketAct } =
            this.sourceData;
          const {
            customerName,
            customerAddress,
            customerPhone,
            customerIdentity,
            customerAddressType,
          } = formInfo;
          param = {
            customerName,
            customerAddress,
            customerPhone,
            customerIdentity,
            customerAddressType,
            customerSource,
            customerIntroducerIdentity:
              customerSource === "3" ? "" : customerIntroducerIdentity,
            marketAct,
            filingStatus: this.activeStep + 1,
            isTmp: type,
            id: this.fillingId,
            customerType: this.customerModelType,
            modelId: this.modelId,
            filingMessageDetail: {
              jsonMessage: formInfo,
            },
          };
        } else {
          param = null;
        }
      });
      return param;
    },
    getSecondParam(formInfo, type) {
      const { clockIn, customerAddressType, customerPhoto } = formInfo;
      return {
        id: this.fillingId,
        filingStatus: this.activeStep + 1,
        isTmp: type,
        clockIn: JSON.stringify(clockIn),
        customerPhoto: JSON.stringify(customerPhoto),
        modelId: this.modelId,
        filingMessageDetail: {
          customerAddressType,
          clockIn,
          jsonFinish: formInfo,
        },
      };
    },
    confirmHandle(type) {
      if (this.columns.length === 0) {
        this.$modal.msgWarning("请确认模版是否存在");
        return;
      }
      this.$refs.filterBar.getForm((data) => {
        if (type === 1) {
          this.$refs.myForm.getFormWithoutRule((formInfo) => {
            const param =
              this.activeStep == 0
                ? this.getFirstParam(formInfo, type)
                : this.getSecondParam(formInfo, type);
            if (!param) return;
            service.filingMessageSave(param).then((res) => {
              this.$modal.msgSuccess(`${this.title}暂存成功!`);
              this.handleClose();
            });
          });
        } else {
          this.$refs.myForm.getForm((formInfo) => {
            const param =
              this.activeStep == 0
                ? this.getFirstParam(formInfo, type)
                : this.getSecondParam(formInfo, type);
            if (!param) return;
            service.filingMessageSave(param).then((res) => {
              const {
                data: { id },
              } = res;
              this.$modal.msgSuccess(`${this.title}成功!`);
              if (this.activeStep === 0) {
                this.activeStep = 1;
                if (this.status === "add") {
                  this.fillingId = id;
                  this.getFilingModelInfoById();
                } else {
                  this.iterator = this.setFormData();
                  this.iterator.next();
                }
              } else {
                this.handleClose();
                this.$parent.fetchList();
              }
            });
          });
        }
      });
    },
    eventFun(evt) {
      const { key, val } = evt;
      switch (key) {
        case "modelType":
          this.customerModelType = val;
          this.getModelIdByType(val);
          break;
        default:
          break;
      }
    },
    getModelIdByType(type) {
      service.getModelIdByType({ type }).then((res) => {
        const { data } = res;
        this.modelId = data;
        this.getFilingModelInfoById();
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.handle-wrapper {
  margin-left: 10px;
}
.step-box {
  margin: 14px 0;
}
</style>