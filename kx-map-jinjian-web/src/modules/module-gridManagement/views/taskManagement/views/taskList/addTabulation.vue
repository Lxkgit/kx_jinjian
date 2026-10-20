<template>
  <i-dialog
    :title="`${statusLabel[status]}下发任务`"
    v-model="showDialog"
    width="80%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="status == 'detail' ? false : true"
  >
    <div class="small_title">
      <h5>任务基本信息</h5>
    </div>
    <el-form
      :model="addForm"
      ref="addForm"
      :rules="rules"
      :label-width="formLabelWidth"
    >
      <el-row>
        <el-col :span="12">
          <el-form-item label="任务名称" prop="taskName">
            <el-input
              :disabled="status === 'detail'"
              :size="size"
              v-model="addForm.taskName"
              placeholder="请输入任务名称"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="任务类型" prop="taskType">
            <el-select
              :disabled="status === 'detail'"
              :size="size"
              class="form-select"
              v-model="addForm.taskType"
              placeholder="请选择任务类型"
              v-if="status !== 'detail'"
            >
              <el-option
                v-for="item in taskType"
                :key="item.id"
                :label="item.taskTypeName"
                :value="item.id"
              ></el-option>
            </el-select>

            <el-input
              :disabled="status === 'detail'"
              :size="size"
              :value="defaultValue.taskTypeName"
              v-else
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="分配规则" prop="distribution">
            <el-select
              :disabled="status === 'detail'"
              :size="size"
              class="form-select"
              v-model="addForm.distribution"
              placeholder="请选择分配规则"
              v-if="status !== 'detail'"
            >
              <el-option
                v-for="item in distributionOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            label="客群选择"
            prop="customGroupId"
            v-if="addForm.distribution == '0'"
          >
            <el-cascader
              :disabled="status === 'detail'"
              v-model="addForm.customGroupId"
              :size="size"
              :options="customerListOptions"
              :props="customerGroupProps"
              v-if="status !== 'detail'"
              collapse-tags
              clearable
              filterable
              style="width: 100%"
              placeholder="请选择客群"
            ></el-cascader>

            <el-input
              :disabled="status === 'detail'"
              :size="size"
              :value="defaultValue.customGroupName"
              v-else
            />
          </el-form-item>
          <el-form-item label="客户导入" v-if="addForm.distribution == '1'">
            <el-button type="primary" @click="fileUploadClick"
              >文件导入
            </el-button>
            <el-button type="primary" @click="templateDownload"
              >模板下载</el-button
            >
            <input
              v-show="false"
              id="fileUpload"
              type="file"
              placeholder="点击选择文件"
              @change="uploadFile"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="任务起止时间" prop="time">
            <el-date-picker
              :disabled="status === 'detail'"
              class="form-select"
              v-model="addForm.time"
              :size="size"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              format="yyyy-MM-dd HH:mm"
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="任务优先级" prop="taskLevel">
            <el-select
              :disabled="status === 'detail'"
              :size="size"
              class="form-select"
              v-model="addForm.taskLevel"
              placeholder="请选择任务优先级"
            >
              <el-option
                v-for="item in dict.type.task_level"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="任务描述" prop="taskDesc">
            <el-input
              :disabled="status === 'detail'"
              :size="size"
              v-model="addForm.taskDesc"
              type="textarea"
              placeholder="请输入任务描述"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </i-dialog>
</template>

<script>
import services from "../../api";
import { mapGetters } from "vuex";
export default {
  name: "addTabulation",
  dicts: ["task_level"],
  props: {
    // 是否展示弹窗
    value: { type: Boolean, default: false, required: true },
    // 弹窗的状态：新增、编辑、查看
    status: { type: String, default: "add" },
    // 打开弹出式携带的数据
    defaultValue: { type: Object, default: () => {} },
  },
  data() {
    return {
      // 弹窗状态匹配对应的label
      statusLabel: {
        add: "新建",
        edit: "编辑",
        detail: "查看",
      },
      size: "mini",
      taskType: [], // 任务类型选项数据
      distributionOptions: [
        {
          label: "客群选择",
          value: "0",
        },
        {
          label: "客户导入",
          value: "1",
        },
      ],
      // 任务客群选项数据
      customerListOptions: [],
      customerGroupProps: {
        label: "customGroupName",
        value: "id",
        emitPath: false,
        multiple: true,
      },
      // 任务基本信息form属性
      addForm: {
        taskName: "",
        taskType: "",
        distribution: "",
        customGroupId: [],
        time: "",
        taskLevel: "",
        taskDesc: "",
        createBy: "",
      },
      // 任务基本信息rules规则
      rules: {
        taskName: [
          {
            required: true,
            message: "请输入任务名称",
            trigger: ["change", "blur"],
          },
        ],
        taskType: [
          {
            required: true,
            message: "请选择任务类型",
            trigger: ["change", "blur"],
          },
        ],
        distribution: [
          {
            required: true,
            message: "请选择分配规则",
            trigger: ["change", "blur"],
          },
        ],
        customGroupId: [
          {
            required: true,
            message: "请选择客群",
            trigger: ["change", "blur"],
          },
        ],
        time: [
          {
            required: true,
            message: "请选择任务起止时间",
            trigger: ["change", "blur"],
          },
        ],
        taskLevel: [
          {
            required: true,
            message: "请选择任务优先级",
            trigger: ["change", "blur"],
          },
        ],
      },
      formLabelWidth: "130px",
      showDialog: null,
    };
  },
  computed: {
    ...mapGetters(["name"]),
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
        if (val) {
          this.getTaskType();
          this.$nextTick(() => {
            this.$refs.addForm.clearValidate();
          });
        }
        this.$emit("input", val);
      },
      immediate: true,
    },
    defaultValue: {
      handler(val) {
        if (val) {
          if (this.status === "add") {
            this.addForm = {
              ...val,
              createBy: this.name,
            };
          } else {
            this.addForm = {
              ...val,
              taskLevel: val.taskLevel + "",
              customGroupId: val.customGroupIds,
              createBy: this.name,
              time: [val.startTime, val.endTime],
            };
          }
        }
      },
    },
    "addForm.taskType": {
      handler(val, old) {
        if (val) {
          setTimeout(() => {
            this.getCustomerGroup(val);
          }, 300);
          if (val != old && old) {
            this.addForm.customGroupId = [];
          }
        }
      },
    },
  },
  mounted() {
    this.getTaskType();
  },
  methods: {
    // 获取任务类型
    getTaskType() {
      services.taskListType().then((res) => {
        this.taskType = res.data;
      });
    },
    // 获取客群列表
    getCustomerGroup(val) {
      console.log("taskType", this.taskType);
      console.log("addForm.taskType", this.addForm.taskType);
      const value = this.taskType.find((item) => item.id === val);
      services.customerGroup({ customType: value.taskObjectId }).then((res) => {
        this.customerListOptions = res.data;
      });
    },
    /* 提交表单 */
    handleConfirm() {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          const taskTypeData = this.taskType.find(
            (item) => item.id == this.addForm.taskType
          );
          let data = {
            ...this.addForm,
            customGroupId: this.addForm.customGroupId.join(","),
            taskTypeName: taskTypeData.taskTypeName,
            taskObjectId: taskTypeData.taskObjectId,
          };
          if (this.addForm.time) {
            console.log(this.addForm.time);
            data.endTime = this.addForm.time[1];
            data.startTime = this.addForm.time[0];
            delete data.time;
          }
          services.addTaskList(data).then((res) => {
            if (res.code == 200) {
              this.$message.success("操作成功");
              this.showDialog = false;
              this.$emit("fetchList");
            }
          });
          // }
          // if (this.status === "edit") {
          //   services.editTaskList(data).then((res) => {
          //     if (res.code == 200) {
          //       this.$message.success("操作成功");
          //       this.showDialog = false;
          //       this.$emit("fetchList");
          //     }
          //   });
          // }
        }
      });
    },
    // 取消表单
    handleClose() {
      console.log(12321);
      (this.addForm = {
        taskName: "",
        taskType: "",
        customGroupId: [],
        time: "",
        taskLevel: "",
        taskDesc: "",
        createBy: "",
      }),
        this.$refs.addForm.resetFields();
      this.$refs.addForm.clearValidate();
    },
    fileUploadClick() {
      document.getElementById("fileUpload").click();
    },
    uploadFile(e) {
      console.log("file", e.target.files);
    },
    templateDownload() {
      console.log("下载模板");
    },
  },
};
</script>

<style scoped lang="scss">
.form-select {
  width: 100% !important;
}
</style>
