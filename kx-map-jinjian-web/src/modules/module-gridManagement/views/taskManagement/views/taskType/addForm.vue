<template>
  <i-dialog
    :title="`任务类型${status === 'add' ? '新增' : '编辑'}`"
    v-model="showDialog"
    width="60%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="true"
  >
    <div class="small_title">
      <h5>模板信息</h5>
    </div>
    <el-form
      :model="addForm"
      ref="addForm"
      :rules="rules"
      :label-width="formLabelWidth"
    >
      <el-row>
        <!-- 输入类型名称 -->
        <el-col :span="12">
          <el-form-item label="任务类型" prop="taskTypeName">
            <el-input
              :size="size"
              style="width: 100%"
              v-model="addForm.taskTypeName"
              placeholder="请输入任务类型"
            ></el-input>
          </el-form-item>
        </el-col>
        <!-- 选择客户类型 -->
        <el-col :span="12">
          <el-form-item label="客户类型" prop="taskObjectId">
            <el-select
              :size="size"
              style="width: 100%"
              v-model="addForm.taskObjectId"
              placeholder="请选择客户类型"
            >
              <el-option
                v-for="item in dict.type.custom_kind"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="small_title">
      <h5>模板内容</h5>
    </div>
    <!-- <i-button-bar :btn-lists="btnLists" @click="clickHandle" ref="btnBar">
    </i-button-bar> -->
    <el-button type="primary" :disabled="isDisabled" @click="addField"
      >添加字段</el-button
    >
    <i-table
      v-if="columns.length > 0"
      :showPagination="false"
      :loading="loading"
      :data="tableData"
      stripe
      align="center"
      :max-height="maxHeight - 80"
      :allColumns="columns"
    >
      <template #fieldName="{ row }">
        <el-input
          :size="size"
          style="width: 100%"
          v-model="row.fieldName"
          placeholder="请输入字段名称"
        ></el-input>
      </template>
      <template #content="{ row }">
        <el-input
          v-if="['3', '4', '5'].includes(row.fillingForm)"
          :size="size"
          style="width: 100%"
          v-model="row.content"
          placeholder="请输入域值（例男,女）"
        ></el-input>
        <el-cascader
          v-if="row.fillingForm == '9' && row.isRequired == '1'"
          v-model="row.content"
          :size="size"
          style="width: 100%"
          :options="tagOptions"
          :props="tagProps"
          collapse-tags
          claerable
          :show-all-levels="false"
        ></el-cascader>
      </template>
      <template #isRequired="{ row }">
        <el-switch
          :size="size"
          v-model="row.isRequired"
          style="width: 100%"
          active-value="1"
          inactive-value="0"
        ></el-switch>
      </template>
      <template #fillingForm="{ row }">
        <div class="form-type">
          <el-select
            :size="size"
            v-model="row.fillingForm"
            @change="handleChange(row)"
            placeholder="请选择填写形式"
            style="width: 100%"
          >
            <el-option
              v-for="item in dict.type.filling_form"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </div>
      </template>
      <template #action="scope">
        <el-button
          :size="size"
          type="danger"
          @click="
            makeSure('是否继续删除该字段?', () => {
              handleDel(scope.row);
            })
          "
        >
          删除
        </el-button>
      </template>
    </i-table>
  </i-dialog>
</template>

<script>
import pageMixin from "@/mixins/pageMixin.vue";
import services from "../../api/index";
export default {
  name: "addForm",
  mixins: [pageMixin],
  dicts: ["filling_form", "custom_kind"],
  props: {
    value: { type: Boolean, default: false, required: true },
    status: {
      type: String,
      default: "add",
    },
    defaultValue: {
      type: Object,
      default: () => ({}),
    },
  },
  data() {
    return {
      size: "mini",
      addForm: {
        taskTypeName: "",
        taskObjectId: "",
      },
      rules: {
        taskTypeName: [
          {
            required: true,
            message: "请输入任务类型",
            trigger: ["change", "blur"],
          },
        ],
        taskObjectId: [
          {
            required: true,
            message: "请选择客户类型",
            trigger: ["change", "blur"],
          },
        ],
      },
      btnLists: [
        {
          cname: "添加字段",
          ename: "addField",
          type: "primary",
        },
      ],
      formLabelWidth: "120px",
      showDialog: false,
      maxHeight: window.innerHeight - 350,
      columns: [
        { label: "字段名称", prop: "fieldName", slot: true },
        { label: "填写形式", prop: "fillingForm", slot: true, width: 200 },
        { label: "域值", prop: "content", slot: true },
        { label: "是否必填", prop: "isRequired", slot: true, width: 90 },
        { label: "操作", slot: true, width: 90 },
      ],
      tableData: [],
      loading: false,
      pageParams: {
        pageSize: 10,
        pageNum: 1,
      },
      total: 0,
      tagProps: {
        emitPath: false,
        multiple: true,
        label: "labelGroupName",
        value: "id",
      },
      tagOptions: [], //标签选择的下拉数据
      ableConfirm: true,
    };
  },
  computed: {
    taskObject() {
      return this.dict.label.custom_kind[this.addForm.taskObjectId];
    },
    isDisabled() {
      if (
        this.addForm.taskTypeName == null ||
        this.addForm.taskTypeName == "" ||
        this.addForm.taskObjectId == null ||
        this.addForm.taskObjectId == ""
      ) {
        return true;
      } else {
        return false;
      }
    },
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
          this.addForm = val;
          if (this.status == "edit") {
            this.addForm.taskObjectId = val.taskObjectId + "";
          }
          this.tableData = val.list || [];
        }
      },
    },
    "addForm.taskObjectId": {
      handler(val, old) {
        if (val) {
          this.taskTypeLabel(val);
          if (val != old && old) {
            this.tableData.forEach((item) => {
              if (item.fillingForm == "9") {
                item.content = "";
              }
            });
          }
        }
      },
    },
  },
  methods: {
    // 添加表格item
    addField() {
      this.tableData.push({ frontKey: +new Date(), content: "" });
    },
    // 新增编辑 提交表单
    handleConfirm() {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          console.log(this.tableData);
          for (const item of this.tableData) {
            if (
              item.fieldName == null ||
              item.fieldName == "" ||
              item.fillingForm == null
            ) {
              this.ableConfirm = false;
              break;
            }
            if (
              ["3", "4", "5"].includes(item.fillingForm) &&
              item.content == ""
            ) {
              this.ableConfirm = false;
              break;
            }
            if (item.fillingForm == "9" && item.isRequired == "1") {
              if (item.content == "") {
                this.ableConfirm = false;
                break;
              }
              item.content = item.content.join(",");
            }
          }
          if (!this.ableConfirm) {
            this.ableConfirm = true;
            this.$message.error("模板内容中存在字段值为空");
            return;
          }
          let data = {
            ...this.addForm,
            list: this.tableData,
            taskObject: this.taskObject,
          };
          console.log("data111", data);
          if (this.status == "add") {
            services.addTaskType(data).then((res) => {
              if (res.code == 200) {
                this.$message.success("新增成功！");
                this.showDialog = false;
                this.$emit("fetchList");
              }
            });
          }
          if (this.status == "edit") {
            services.editTaskType(data).then((res) => {
              if (res.code == 200) {
                this.$message.success("编辑成功！");
                this.showDialog = false;
                this.$emit("fetchList");
              }
            });
          }
        }
      });
    },
    //关闭弹窗
    handleClose() {
      this.tableData = [{ frontKey: +new Date(), content: "" }];
      this.$refs.addForm.resetFields();
    },
    // 填写形式选择
    handleChange(val) {
      val.content = "";
    },
    // 标签
    taskTypeLabel(id) {
      const param = { id };
      services.taskTypeLabel(param).then((res) => {
        const { data } = res;
        this.tagOptions = data;
      });
    },
    // 删除字段
    handleDel(row) {
      this.tableData = this.tableData.filter((item) => {
        return item.frontKey !== row.frontKey;
      });
    },
  },
};
</script>

<style scoped lang="scss">
.small_title {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 10px;
}
</style>
