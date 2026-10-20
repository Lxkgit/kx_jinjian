<template>
  <i-dialog
    :title="`任务类型${status === 'add' ? '新增' : '编辑'}`"
    v-model="showDialog"
    width="60%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="status === 'add' || status == 'turn'"
  >
    <!-- <div class="small_title">
      <h5>模板信息</h5>
    </div> -->
    <el-form
      :model="addForm"
      ref="addForm"
      :rules="rules"
      :label-width="formLabelWidth"
    >
      <el-row>
        <!-- 输入类型名称 -->
        <el-col :span="12">
          <el-form-item label="任务名称" prop="taskName">
            <el-input
              :size="size"
              style="width: 100%"
              v-model="addForm.taskName"
              placeholder="请输入任务名称"
            ></el-input>
          </el-form-item>
        </el-col>
        <!-- 选择客户类型 -->
        <el-col :span="12">
          <el-form-item label="任务类型" prop="taskType">
            <el-select
              :size="size"
              style="width: 100%"
              v-model="addForm.taskType"
              placeholder="请选择任务类型"
            >
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="addForm.taskType == '日常任务'">
        <el-col :span="16">
          <el-form-item label="执行起止时间" prop="startToEnd">
            <el-date-picker
              v-model="addForm.startToEnd"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="['00:00:00', '23:59:00']"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="addForm.taskType == '周期任务'">
        <el-col :span="12">
          <el-form-item label="执行周期" prop="cycle">
            <el-select
              :size="size"
              style="width: 100%"
              v-model="addForm.cycle"
              placeholder="请选择执行周期"
            >
              <el-option
                v-for="item in cycleOpt"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="周期范围" prop="startToEnd">
            <el-date-picker
              v-model="addForm.startToEnd"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :default-time="['00:00:00', '23:59:00']"
              format="yyyy-MM-dd HH:mm:ss"
              value-format="yyyy-MM-dd HH:mm:ss"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-if="addForm.taskType == '周期任务'">
        <el-col :span="12">
          <el-form-item label="共执行次数" prop="times">
            <el-input
              :size="size"
              style="width: 100%"
              v-model="addForm.times"
              placeholder="请输入"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <el-form-item label="任务执行人">
            <div>
              <el-button type="primary" @click="showEsecutorDialog = true"
                >选择</el-button
              >
              <el-button type="text">沿用上次任务执行人</el-button>
            </div>
            <div class="selected-box">
              <span v-for="dept in dutyDepts" :key="dept.orgNum">
                <el-tag
                  style="margin: 0 7.5px 5px 0"
                  v-for="item in dept.executors"
                  :key="item.orgNum"
                >
                  {{ item.executorName }}({{ dept.deptName }})
                </el-tag>
              </span>
            </div>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div class="small_title">
      <h5>模板内容</h5>
    </div>
    <!-- <i-button-bar :btn-lists="btnLists" @click="clickHandle" ref="btnBar">
    </i-button-bar> -->
    <el-button type="primary" @click="addField">添加字段</el-button>
    <i-table
      v-if="columns.length > 0"
      :showPagination="false"
      :loading="loading"
      :data="tableData"
      stripe
      align="center"
      :max-height="maxHeight"
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

    <executor-dialog
      v-model="showEsecutorDialog"
      @confirm="confirm"
      :selectedList="[...dutyDepts]"
    >
    </executor-dialog>
  </i-dialog>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin.vue";
import pageMixin from "@/mixins/pageMixin.vue";
import services from "../../../api/index";
import executorDialog from "./executorDialog.vue";
export default {
  name: "addForm",
  mixins: [dialogMixin, pageMixin],
  dicts: ["filling_form", "custom_kind"],
  components: { executorDialog },
  props: {
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
        taskName: "",
        taskType: "",
        cycle: "",
        times: "",
        startToEnd: null,
      },
      rules: {
        taskName: [
          {
            required: true,
            message: "请输入任务名称",
            trigger: ["blur"],
          },
        ],
        taskType: [
          {
            required: true,
            message: "请选择客户类型",
            trigger: ["change"],
          },
        ],
        startToEnd: [
          {
            required: true,
            message: "请选择执行时间",
            trigger: ["change"],
          },
        ],
        cycle: [
          {
            required: true,
            message: "请选择执行周期",
            trigger: ["change"],
          },
        ],
        times: [
          {
            required: true,
            message: "请输入共执行次数",
            trigger: ["blur"],
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
      // maxHeight: window.innerHeight - 350,
      maxHeight: 400,
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
      options: [
        { label: "日常任务", value: "日常任务" },
        { label: "周期任务", value: "周期任务" },
      ],
      cycleOpt: [
        { label: "月", value: "月" },
        { label: "季度", value: "季度" },
        { label: "年", value: "年" },
      ],
      dutyDepts: [],
      showEsecutorDialog: false,
    };
  },
  computed: {
    // taskObject() {
    //   return this.dict.label.custom_kind[this.addForm.taskObjectId];
    // },
    // isDisabled() {
    //   if (
    //     this.addForm.taskTypeName == null ||
    //     this.addForm.taskTypeName == "" ||
    //     this.addForm.taskObjectId == null ||
    //     this.addForm.taskObjectId == ""
    //   ) {
    //     return true;
    //   } else {
    //     return false;
    //   }
    // },
    taskHairList() {
      return this.$store.getters["gridManagement/taskHairList"];
    },
  },
  watch: {
    // defaultValue: {
    //   handler(val) {
    //     if (val) {
    //       this.addForm = JSON.parse(JSON.stringify(val));
    //       if (this.status == "edit") {
    //         this.addForm.taskObjectId = val.taskObjectId + "";
    //       }
    //       this.dutyDepts = val.taskObject || []
    //       this.tableData = val.tableData || [];
    //     }
    //   },
    // },
    // "addForm.taskObjectId": {
    //   handler(val, old) {
    //     if (val) {
    //       this.taskTypeLabel(val);
    //       if (val != old && old) {
    //         this.tableData.forEach((item) => {
    //           if (item.fillingForm == "9") {
    //             item.content = "";
    //           }
    //         });
    //       }
    //     }
    //   },
    // },
  },
  methods: {
    initData() {
      if (this.status != 'add') {
        this.addForm = JSON.parse(JSON.stringify(this.defaultValue));
      }
      console.log(this.addForm)
      this.dutyDepts = this.defaultValue.taskObject || []
      this.tableData = this.defaultValue.list || [];
    },
    // 添加表格item
    addField() {
      this.tableData.push({ frontKey: +new Date(), content: "" });
    },
    // 新增编辑 提交表单
    handleConfirm() {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          if (this.status == 'add') {
            const params = {
              ...this.addForm,
              id: new Date().getTime(),
              list: this.tableData,
              taskObject: this.dutyDepts,
              taskStatus: '进行中',
              startTime:this.addForm.startToEnd[0],
              endTime: this.addForm.startToEnd[1],
              hairTime: this.parseTime(new Date(), '{y}-{m}-{d} {h}:{i}:{s}'),
              progress:'0%'
            };
            console.log(params);
            this.$store.commit("gridManagement/SET_TASK_HAIR_LIST", [
              { ...params },
              ...this.taskHairList,
            ]);
            this.$modal.msgSuccess('操作成功')
          } else {
            this.$modal.msgSuccess('转派成功')
          }
          this.handleClose()
        }
      });
    },
    //关闭弹窗
    handleClose() {
      this.tableData = [{ frontKey: +new Date(), content: "" }];
      this.dutyDepts = [];
      this.$refs.addForm.resetFields();
      this.showDialog = false
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
    confirm(e) {
      this.dutyDepts = [...e];
      console.log(this.dutyDepts)
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
