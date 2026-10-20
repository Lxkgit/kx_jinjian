<template>
  <div>
    <PageContent>
      <!-- 过滤器 -->
      <i-form
        ref="baseForm"
        slot="filter"
        :columns="searchColumns"
        :gridColumns="4"
        @eventFun="formEventFun"
      >
      </i-form>
      <div class="content-title">模版内容</div>
      <el-steps :active="activeStep" :align-center="true">
        <el-step title="第一步: 基础建档要素"></el-step>
        <el-step title="第二步: 有效建档要素"></el-step>
      </el-steps>
      <!-- 按钮 -->
      <i-button-bar :btn-lists="btnLists" @click="clickHandle" ref="btnBar">
      </i-button-bar>
      <!-- 表单编辑 -->
      <el-form
        class="table-form"
        ref="tableForm"
        :rules="rules"
        :model="baseForm"
      >
        <el-table :height="height" :data="baseForm.fields" style="width: 100%">
          <el-table-column label="字段名称">
            <template slot-scope="scope">
              <el-form-item
                :prop="'fields.' + scope.$index + '.chineseName'"
                :rules="rules.chineseName"
              >
                <el-input
                  v-model="scope.row.chineseName"
                  placeholder="请输入字段名称"
                  :disabled="disableList.includes(scope.row.englishName)"
                ></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="关键字">
            <template slot-scope="scope">
              <el-form-item
                :prop="'fields.' + scope.$index + '.englishName'"
                :rules="rules.englishName"
              >
                <el-input
                  v-model.trim="scope.row.englishName"
                  placeholder="请输入关键字(英文)"
                  v-on:input="
                    (event) => eNameKeyup(event, baseForm.fields[scope.$index])
                  "
                  :disabled="disableList.includes(scope.row.englishName)"
                ></el-input>
              </el-form-item>
              <!-- oninput="this.value=this.value.replace(/[^a-zA-Z]/g, '') -->
            </template>
          </el-table-column>
          <el-table-column label="填写形式">
            <template slot-scope="scope">
              <el-form-item
                :prop="'fields.' + scope.$index + '.fieldType'"
                :rules="rules.fieldType"
              >
                <el-select
                  :disabled="disableList.includes(scope.row.englishName)"
                  v-model="scope.row.fieldType"
                  placeholder="请选择填写形式"
                >
                  <el-option
                    v-for="item in formTypeOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="域值">
            <template slot-scope="scope">
              <el-form-item
                v-if="['3', '4', '5'].includes(scope.row.fieldType)"
              >
                <el-input
                  v-model="scope.row.content"
                  :disabled="disableList.includes(scope.row.englishName)"
                  placeholder="请填写域值(例:男,女)"
                ></el-input>
              </el-form-item>
              <el-form-item
                v-if="
                  ['10'].includes(scope.row.fieldType) &&
                  scope.row.isMust == '1'
                "
              >
                <el-cascader
                  v-model="scope.row.content"
                  :options="tagOptions"
                  :props="tagProps"
                  collapse-tags
                  clearable
                  :show-all-levels="false"
                ></el-cascader>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="是否作为必填">
            <template slot-scope="scope">
              <el-form-item>
                <el-switch
                  v-model="scope.row.isMust"
                  :disabled="disableList.includes(scope.row.englishName)"
                  active-value="1"
                  inactive-value="0"
                >
                </el-switch>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="100">
            <template slot-scope="scope">
              <el-form-item>
                <el-button
                  :disabled="disableList.includes(scope.row.englishName)"
                  size="mini"
                  type="danger"
                  @click="deleteItem(scope.$index, scope.row)"
                  >删除
                </el-button>
              </el-form-item>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <!-- 操作按钮 -->
      <div v-if="activeStep === 0" class="handle-wrapper">
        <el-button @click="cancelHandle">取消</el-button>
        <el-button type="primary" @click="confirmHandle">下一步</el-button>
      </div>
      <div v-else class="handle-wrapper">
        <el-button @click="cancelHandle">取消</el-button>
        <el-button @click="backHandle">上一步</el-button>
        <el-button type="primary" @click="confirmHandle">确认</el-button>
      </div>
    </PageContent>
    <modelView v-model="showDialog" customerType="3"> </modelView>
  </div>
</template>

<script>
import pageMixin from "@/mixins/pageMixin";
import service from "../api";
import modelView from "../components/modelView";
const DefaultData = [
  {
    chineseName: "客户名称",
    englishName: "customerName",
    fieldType: "1",
    content: "",
    isMust: "1",
  },
  {
    chineseName: "地址",
    englishName: "customerAddress",
    fieldType: "1",
    content: "",
    isMust: "1",
  },
  {
    chineseName: "地址类型",
    englishName: "customerAddressType",
    fieldType: "5",
    content:
      "户籍地址,常居地址,邮寄地址,单位地址,证件地址,个人其他地址,注册地址,经营地址,工作地址,企业其他地址",
    isMust: "1",
  },
  {
    chineseName: "手机号码",
    englishName: "customerPhone",
    fieldType: "1",
    content: "",
    isMust: "1",
  },
  {
    chineseName: "证件号",
    englishName: "customerIdentity",
    fieldType: "1",
    content: "",
    isMust: "1",
  },
  {
    chineseName: "客户来源",
    englishName: "customerSource",
    fieldType: "5",
    content: "陌拜,客户介绍,第三方介绍,营销活动",
    isMust: "1",
  },
];

const DefaultOtherData = [
  {
    chineseName: "照片",
    englishName: "customerPhoto",
    fieldType: "9",
    content: "",
    isMust: "1",
  },
  {
    chineseName: "定位打卡",
    englishName: "clockIn",
    fieldType: "11",
    content: "",
    isMust: "1",
  },
];
export default {
  mixins: [pageMixin],
  dicts: ["table_model_type"],
  components: {
    modelView,
  },
  data() {
    return {
      searchColumns: [
        {
          label: "模版名称",
          key: "modelName",
          type: "text",
          rules: [{ required: true, message: "模版名称不能为空" }],
        },
        {
          label: "模版类型",
          key: "modelType",
          type: "select",
          options: () => this.dict.type.table_model_type,
          disabled: () => this.modelStatus !== "add",
          rules: [
            { required: true, message: "模版类型不能为空", trigger: "change" },
          ],
        },
      ],
      tagOptions: [],
      tagProps: {
        emitPath: false,
        multiple: true,
        value: "id",
        label: "labelGroupName",
      },
      btnLists: [
        {
          cname: "添加字段",
          ename: "addItem",
          type: "primary",
        },
      ],
      disableList: [
        "customerName",
        "customerAddress",
        "customerPhone",
        "customerIdentity",
        "customerSource",
        "customerPhoto",
        "clockIn",
        "customerAddressType",
      ],
      rules: {
        modelName: [
          { required: true, message: "请输入表单名称", trigger: "blur" },
        ],
        modelType: [
          { required: true, message: "请输入表单性质", trigger: "change" },
        ],
        chineseName: [
          { required: true, message: "请输入字段名称", trigger: "blur" },
        ],
        englishName: [
          { required: true, message: "请输入关键字", trigger: "blur" },
        ],
        fieldType: [
          { required: true, message: "请选择填写模式", trigger: "change" },
        ],
      },
      baseForm: {
        modelName: "",
        modelType: "",
        fields: [],
      },
      formTypeOptions: [
        { label: "文本", value: "1" },
        { label: "文本域", value: "2" },
        { label: "单选", value: "3" },
        { label: "多选", value: "4" },
        { label: "下拉列表", value: "5" },
        { label: "日期", value: "6" },
        { label: "开关", value: "8" },
        { label: "图片", value: "9" },
        { label: "标签选择", value: "10" },
        { label: "打卡", value: "11" },
      ],
      showDialog: false,
      activeStep: 0,
      height: window.innerHeight - 420,
    };
  },
  computed: {
    modelStatus() {
      return this.$store.getters["gridManagement/modelStatus"];
    },
    modelId() {
      return this.$store.getters["gridManagement/modelId"];
    },
  },
  mounted() {
    this.initData();
  },
  methods: {
    // 初始数据
    initData() {
      if (this.modelStatus === "add") {
        this.baseForm.fields = [];
        this.modelTypeChange();
      } else {
        this.getModalInfo();
      }
      this.selectChildLabel("3");
    },
    getModalInfo() {
      const param = {
        id: this.modelId,
        isFinishParam: this.activeStep,
      };
      service.getFilingModelInfoById(param).then((res) => {
        const { data } = res;
        this.$refs.baseForm.setForm(data);
        this.baseForm = Object.assign(this.baseForm, data);
        if (this.activeStep === 1) {
          if (this.baseForm.fields.length === 0) {
            this.baseForm.fields.splice(
              0,
              0,
              ...JSON.parse(JSON.stringify(DefaultOtherData))
            );
          }
        }
        this.baseForm.fields.forEach((item) => {
          if (["10"].includes(item.fieldType)) {
            if (item.isMust === "1") {
              item.content = item.content.split(",");
            } else {
              item.content = null;
            }
          }
        });
      });
    },
    modelTypeChange(modelType) {
      this.baseForm.fields = this.baseForm.fields.filter(
        (item) => !this.disableList.includes(item.englishName)
      );
      this.activeStep === 0 &&
        this.baseForm.fields.splice(
          0,
          0,
          ...JSON.parse(JSON.stringify(DefaultData))
        );
      this.activeStep === 1 &&
        this.baseForm.fields.splice(
          0,
          0,
          ...JSON.parse(JSON.stringify(DefaultOtherData))
        );
    },
    eNameKeyup(event, item) {
      item.englishName = this.disableList.includes(event)
        ? ""
        : event.replace(/[^a-zA-Z]/g, "");
    },
    formEventFun(evt) {
      const { key, val } = evt;
      switch (key) {
        case "modelType": {
          this.selectChildLabel(val);
          break;
        }
        default:
          break;
      }
    },
    addItem() {
      this.baseForm.fields.push({
        chineseName: "",
        englishName: "",
        fieldType: "",
        content: null,
        isMust: "0",
      });
    },
    selectChildLabel(id) {
      const param = {
        id,
      };
      service.selectChildLabel(param).then((res) => {
        const { data } = res;
        this.tagOptions = data;
      });
    },
    deleteItem(index, row) {
      this.baseForm.fields.splice(index, 1);
    },
    previewHandle() {
      this.$store.commit("gridManagement/SET_MODEL_STATUS", "edit");
      this.showDialog = true;
    },
    cancelHandle() {
      this.baseForm = {
        modelName: "",
        modelType: "",
        fields: [],
      };
      this.activeStep = 0;
      this.$refs.baseForm.reset();
      this.$refs.tableForm.resetFields();
      this.$router.go(-1);
    },
    backHandle() {
      this.activeStep = 0;
      this.$store.commit("gridManagement/SET_MODEL_STATUS", "edit");
      this.initData();
    },
    confirmHandle() {
      this.$refs.baseForm.getForm((formInfo) => {
        this.baseForm = Object.assign(this.baseForm, formInfo);
        const { modelName, modelType } = formInfo;
        this.$refs.tableForm.validate((tableValid) => {
          if (tableValid) {
            let fields = [];
            for (let item of this.baseForm.fields) {
              if (
                ["10"].includes(item.fieldType) &&
                item.isMust === "1" &&
                !item.content
              ) {
                this.$modal.msgError("必填标签域值不能为空");
                return;
              }
              const obj = {
                ...item,
                isFinish: this.activeStep,
                content: ["10"].includes(item.fieldType)
                  ? item.isMust === "1"
                    ? item.content.join(",")
                    : ""
                  : item.content,
              };
              fields.push(obj);
            }
            const param = {
              id:
                this.modelStatus === "add" && this.activeStep === 0
                  ? null
                  : this.modelId,
              modelName,
              modelType,
              isFinishParam: this.activeStep,
              fields,
            };
            service.editFilingModelInfo(param).then((res) => {
              this.$modal.msgSuccess("操作成功!");
              const { msg } = res;
              if (this.activeStep === 0) {
                this.$store.commit("gridManagement/SET_MODEL_ID", msg);
                this.activeStep = 1;
                this.initData();
              } else {
                this.cancelHandle();
              }
            });
          }
        });
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.content-title {
  font-size: 20px;
  margin: 10px 0;
}
.handle-wrapper {
  margin: 10px 0;
  width: 100%;
  display: flex;
  justify-content: flex-end;
}
</style>