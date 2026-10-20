<template>
  <!-- 改用原生el-dialog，避免自定义IDialog的兼容性问题 -->
  <el-dialog
    :title="title"
    :visible.sync="showDialog"
    width="600px"
    :before-close="handleClose"
  >
    <div class="batch-upload-box">
      <el-upload
        class="upload-area"
        drag
        action="#"
        :auto-upload="false"
        :on-change="handleFileChange"
        :file-list="fileList"
        accept=".xlsx,.csv"
        :limit="1"
        :on-exceed="handleExceed"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          点击或拖拽文件到此区域上传
          <div class="el-upload__tip" style="margin-top: 8px; color: #909399">
            支持 .xlsx, .csv 格式
          </div>
        </div>
      </el-upload>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleClose">取消</el-button>
      <el-button type="primary" @click="handleConfirm" :loading="loading">
        确定
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
// 移除dialogMixin，避免冲突
export default {
  name: "batchImportDialog",
  props: {
    title: { type: String, default: "批量导入" },
    // 父组件传入的显示状态（替代v-model）
    visible: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      showDialog: this.visible, // 内部控制弹窗显示
      fileList: [],
      mockData: [
        {
          checkNo: "PC202603190001",
          checkDate: "2026-03-19",
          leadDept: "国家金融监督管理总局上城监管支局",
          leadDeptPerson: "陈昊宇",
          checkArea: "浙江省杭州市上城区彭埠街道三花国际3幢2单元101室",
          subjectType: "商户",
          subjectCount: 2,
          checkTimes: 3,
          problemDesc: "存在超范围经营问题",
          reporter: "沈亦凡",
          reportTime: "2026-03-19 09:00:00",
          reviewStatus: "待复核",
        },
        {
          checkNo: "PC202603190002",
          checkDate: "2026-03-19",
          leadDept: "国家金融监督管理总局拱墅监管支局",
          leadDeptPerson: "林诗瑶",
          checkArea: "浙江省杭州市拱墅区祥符街道中大银泰城2号楼1502室",
          subjectType: "小微企业",
          subjectCount: 3,
          checkTimes: 2,
          problemDesc: "零钞备付不足",
          reporter: "冯可欣",
          reportTime: "2026-03-19 10:15:00",
          reviewStatus: "待复核",
        },
      ],
    };
  },
  watch: {
    // 监听父组件传入的visible，同步弹窗状态
    visible(val) {
      this.showDialog = val;
      if (val) {
        this.fileList = []; // 打开弹窗时清空文件
      }
    },
    // 弹窗关闭时通知父组件
    showDialog(val) {
      this.$emit("update:visible", val);
    },
  },
  methods: {
    handleFileChange(file) {
      this.fileList = [file];
    },
    handleExceed() {
      this.$message.warning("只能上传一个文件！");
    },
    handleClose() {
      this.showDialog = false;
      this.fileList = [];
    },
    handleConfirm() {
      if (this.fileList.length === 0) {
        this.$message.error("请先选择要导入的文件！");
        return;
      }

      this.loading = true;
      setTimeout(() => {
        this.loading = false;
        this.$message.success(
          `批量导入成功，共导入 ${this.mockData.length} 条数据`
        );
        this.$emit("submit", this.mockData); // 传递模拟数据
        this.showDialog = false; // 关闭弹窗
      }, 1500);
    },
  },
};
</script>

<style lang="scss" scoped>
.batch-upload-box {
  padding: 16px 0;
}
.upload-area {
  width: 100%;
  ::v-deep .el-upload {
    width: 100%;
  }
  ::v-deep .el-upload-dragger {
    width: 100%;
    height: 200px;
  }
}
.dialog-footer {
  text-align: right;
}
</style>
