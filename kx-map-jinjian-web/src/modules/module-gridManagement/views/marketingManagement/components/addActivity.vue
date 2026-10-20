<template>
  <i-dialog
    :modal="false"
    :title="title"
    v-model="showDialog"
    width="60%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :showFooter="status != 'result'"
  >
    <el-form
      :model="formInfo"
      ref="formInfo"
      :rules="rules"
      :label-width="formLabelWidth"
      size="mini"
    >
      <el-row>
        <!-- 活动名称 -->
        <el-col :span="24">
          <div class="title">活动基本信息</div>
        </el-col>
        <!-- 活动名称 -->
        <el-col :span="12">
          <el-form-item label="活动名称" prop="actName">
            <el-input
              :size="size"
              :maxlength="50"
              v-model="formInfo.actName"
              autocomplete="off"
              placeholder="请输入活动名称"
              :disabled="formReadonly"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="活动网格" prop="actGrid">
            <el-button type="primary" @click="showArea=true">地图圈选</el-button>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <!-- 活动时间 -->
        <el-col :span="12">
          <el-form-item label="活动时间" prop="actTimeList">
            <el-date-picker
              class="form-select"
              :size="size"
              v-model="formInfo.actTimeList"
              type="daterange"
              align="right"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd"
              :disabled="formReadonly"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
        <!-- 活动时长 -->
        <el-col :span="12">
          <el-form-item label="活动时长" prop="actDuration">
            <el-input
              :size="size"
              :maxlength="50"
              v-model="formInfo.actDuration"
              autocomplete="off"
              placeholder="请输入活动时长"
              :disabled="formReadonly"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <!-- 创建人 -->
        <el-col :span="12">
          <el-form-item label="创建人" prop="createBy">
            <el-input
              :size="size"
              :maxlength="50"
              v-model="formInfo.createBy"
              autocomplete="off"
              placeholder="请输入创建人"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <!-- 创建机构 -->
        <el-col :span="12">
          <el-form-item label="创建机构" prop="createDeptName">
            <el-input
              :size="size"
              :maxlength="50"
              v-model="formInfo.createDeptName"
              autocomplete="off"
              placeholder="请输入创建机构"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <!-- 活动描述 -->
        <el-col :span="12">
          <el-form-item label="活动描述" prop="actDesc">
            <el-input
              :size="size"
              :maxlength="500"
              showWordLimit
              type="textarea"
              :rows="3"
              v-model="formInfo.actDesc"
              autocomplete="off"
              placeholder="请输入活动描述"
              :disabled="formReadonly"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="24">
          <div class="title">活动预期结果</div>
        </el-col>
        <!-- 活动参与人数 -->
        <el-col :span="12">
          <el-form-item label="活动参与人数" prop="actParticipateNum">
            <el-input
              :size="size"
              :maxlength="50"
              v-model="formInfo.actParticipateNum"
              autocomplete="off"
              placeholder="请输入活动参与人数"
              :disabled="formReadonly"
            ></el-input>
          </el-form-item>
        </el-col>
        <!-- 预期成果描述 -->
        <el-col :span="12">
          <el-form-item label="预期成果描述" prop="otherExpectDesc">
            <el-input
              :size="size"
              :maxlength="500"
              showWordLimit
              type="textarea"
              :rows="3"
              v-model="formInfo.otherExpectDesc"
              autocomplete="off"
              placeholder="请输入预期成果描述"
              :disabled="formReadonly"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row v-if="status==='result'">
        <el-col :span="24">
          <div class="title">活动结果</div>
        </el-col>
        <el-col :span="12">
          <el-form-item label="扫码人数" prop="actParticipateNum">
            <el-input
              :size="size"
              :maxlength="50"
              v-model="formInfo.xxx"
              autocomplete="off"
              placeholder="请输入扫码人数"
              :disabled="formReadonly"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="信息收集人数" prop="otherExpectDesc">
            <el-input
              :size="size"
              :maxlength="500"
              showWordLimit
              :rows="3"
              v-model="formInfo.xxx"
              autocomplete="off"
              placeholder="请输入信息收集人数"
              :disabled="formReadonly"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="12">
          <el-form-item label="有效信息人数" prop="otherExpectDesc">
            <el-input
              :size="size"
              :maxlength="500"
              showWordLimit
              :rows="3"
              v-model="formInfo.xxx"
              autocomplete="off"
              placeholder="请输入有效信息人数"
              :disabled="formReadonly"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="业务办理人数" prop="otherExpectDesc">
            <el-input
              :size="size"
              :maxlength="500"
              showWordLimit
              :rows="3"
              v-model="formInfo.xxx"
              autocomplete="off"
              placeholder="请输入业务办理人数"
              :disabled="formReadonly"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="活动反馈" prop="otherExpectDesc">
            <el-input
              :size="size"
              :maxlength="500"
              showWordLimit
              :rows="3"
              type="textarea"
              v-model="formInfo.xxx"
              autocomplete="off"
              placeholder="请输入活动反馈"
              :disabled="formReadonly"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="24">
          <el-form-item label="打卡地点" prop="otherExpectDesc">
            <el-input
              :size="size"
              :maxlength="500"
              showWordLimit
              :rows="3"
              v-model="formInfo.xxx"
              autocomplete="off"
              placeholder="请输入打卡地点"
              :disabled="formReadonly"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="24">
          <el-form-item label="照片" prop="otherExpectDesc">
            <el-upload action="#" >
            <el-button size="small">
              选择
              <i class="el-icon-upload el-icon--right"></i>
            </el-button>
          </el-upload>
          </el-form-item>
        </el-col>
        

      </el-row>
    </el-form>

    <addArea
      v-model="showArea"
      :status="status"
      :formInfo = "formInfo"
    >
    </addArea>
  </i-dialog>
</template>

<script>
import { mapState } from "vuex";
import dialogMixin from "@/mixins/dialogMixin.vue";
import service from "../api";
import addArea from "./addArea.vue"
export default {
  name: "addGroup",
  mixins: [dialogMixin],
  components: { addArea },
  props: {
    defaultValue: {
      type: Object,
    },
    status: {
      type: String,
    },
  },
  data() {
    return {
      size: "mini",
      formLabelWidth: "150px",
      formInfo: {
        actName: "", //活动名称
        actTimeList: [], //活动时间范围
        actDuration: "", //活动时长
        createBy: "", //创建人
        createManagerNum: "", //创建人客户号
        createDeptName: "", //创建机构
        createOrgNum: "", //创建机构号
        actDesc: "", //活动描述
        actParticipateNum: "", //活动参与人数
        otherExpectDesc: "", //预期结果描述
        actStatus: "1", //活动状态
        coordinates:'',//活动网格
        gridName:'',//网格名称
        gridCenter:[],//网格中心
      },
      rules: {
        actName: [
          {
            required: true,
            message: "请输入活动名称",
            trigger: ["change", "blur"],
          },
        ],
        actTimeList: [
          {
            required: true,
            type: "array",
            message: "请选择活动时间",
            trigger: ["change", "blur"],
          },
        ],
        actDuration: [
          {
            required: true,
            message: "请输入活动时长",
            trigger: ["change", "blur"],
          },
        ],
        createBy: [
          {
            required: true,
            message: "请输入创建人",
            trigger: ["blur"],
          },
        ],
        createDeptName: [
          {
            required: true,
            message: "请输入创建机构",
            trigger: ["blur"],
          },
        ],
        updateFlag: [
          { required: true, message: "请选择标签关联关系", trigger: "change" },
        ],
      },
      showArea:false
    };
  },
  computed: {
    ...mapState({ userInfo: (state) => state.user.userInfo }),
    title() {
      if (this.status === "add") {
        return "新建营销活动";
      } else if (this.status === "edit") {
        return "编辑营销活动";
      } else {
        return "营销活动成果";
      }
    },
    formReadonly() {
      if (this.status == "result") {
        return true;
      } else {
        return false;
      }
    },
  },
  mounted() {
    // console.log("userInfo", this.userInfo);
  },
  methods: {
    resetData(){
      this. formInfo = {
        actName: "", //活动名称
        actTimeList: [], //活动时间范围
        actDuration: "", //活动时长
        createBy: "", //创建人
        createManagerNum: "", //创建人客户号
        createDeptName: "", //创建机构
        createOrgNum: "", //创建机构号
        actDesc: "", //活动描述
        actParticipateNum: "", //活动参与人数
        otherExpectDesc: "", //预期结果描述
        actStatus: "1", //活动状态
        coordinates:'',//活动网格
        gridName:'',//网格名称
        gridCenter:[],//网格中心
      }
    },
    initData() {
      if (this.status === "add") {
        this.resetData()
        this.formInfo.createBy = this.userInfo.userName;
        this.formInfo.createManagerNum = this.userInfo.managerNum;
        this.formInfo.createDeptName = this.userInfo.dept.deptName;
        this.formInfo.createOrgNum = this.userInfo.dept.orgNum;
        return;
      } else {
        this.formInfo = this.defaultValue;
      }
    },
    // 关闭弹窗
    handleClose() {
      this.$refs.formInfo.clearValidate();
      this.$refs.formInfo.resetFields();
      this.showDialog = false;
    },
    // 弹窗确认按钮
    handleConfirm() {
      this.$refs.formInfo.validate((valid) => {
        if (valid) {
          if (this.status == "add") {
            this.addActivity();
          } else {
            this.updateActivity();
          }
        }
      });
    },
    addActivity() {
      let data = {
        ...this.formInfo,
      };
      console.log("AddFormInfo", this.formInfo);
      service.addAct(data).then((res) => {
        this.$message.success("新增活动成功！");
        this.$parent.fetchList();
        this.handleClose();
      });
    },
    updateActivity() {
      let data = {
        ...this.formInfo,
      };
      console.log("UpdateFormInfo", this.formInfo);
      service.updateAct(data).then((res) => {
        this.$message.success("活动更新成功");
        this.$parent.fetchList();
        this.handleClose();
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.form-select {
  width: 100% !important;
}
.title {
  font-size: 18px;
  font-weight: bold;
}
</style>
