<template>
  <IDialog
    title="导入风险线索"
    v-model="showDialog"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    :loading="loading"
  >
    <div class="import-box">
      <!-- 上传区域 -->
      <el-upload
        ref="uploadRef"
        :auto-upload="false"
        :on-change="handleFileChange"
        :file-list="fileList"
        accept=".xlsx,.csv"
        :limit="1"
        class="upload-dragger"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          点击或拖拽文件到此区域上传
          <div class="el-upload__tip">支持 .xlsx、.csv 格式</div>
        </div>
      </el-upload>

      <!-- 提示信息 -->
      <div class="tips" v-if="fileList.length > 0">
        <p>已选择文件：{{ fileList[0].name }}</p>
        <p class="tips-note">请确保文件格式为 .xlsx 或 .csv，且字段顺序正确</p>
      </div>
    </div>
  </IDialog>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin.vue";
import * as XLSX from "xlsx"; // 需安装: npm i xlsx --save

export default {
  mixins: [dialogMixin],
  props: {
    // 父组件控制弹窗显示
    visible: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      showDialog: false,
      fileList: [], // 上传文件列表
      uploadedFile: null, // 解析后的文件对象
    };
  },
  watch: {
    // 监听父组件传入的visible，同步到内部状态
    visible(val) {
      this.showDialog = val;
      if (val) {
        this.resetState(); // 打开时重置状态
      }
    },
  },
  methods: {
    // 文件选择变化
    handleFileChange(file, fileList) {
      this.fileList = [file]; // 限制单文件
      this.uploadedFile = file.raw; // 保存原始文件
    },

    // 重置所有状态
    resetState() {
      this.fileList = [];
      this.uploadedFile = null;
      this.$refs.uploadRef?.clearFiles();
    },

    // 关闭弹窗
    handleClose() {
      this.resetState();
      this.$emit("update:visible", false);
    },

    // 解析Excel/CSV文件为JSON
    async parseFile(file) {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = (e) => {
          try {
            const data = new Uint8Array(e.target.result);
            const workbook = XLSX.read(data, { type: "array" });
            const firstSheetName = workbook.SheetNames[0];
            const worksheet = workbook.Sheets[firstSheetName];
            // 转换为JSON
            const json = XLSX.utils.sheet_to_json(worksheet);
            resolve(json);
          } catch (error) {
            reject(error);
          }
        };
        reader.onerror = (err) => reject(err);
        reader.readAsArrayBuffer(file);
      });
    },

    // 确定导入
    async handleConfirm() {
      if (!this.uploadedFile) {
        this.$message.warning("请先选择要导入的文件！");
        return;
      }

      this.loading = true;
      try {
        // 1. 解析文件
        const jsonData = await this.parseFile(this.uploadedFile);
        if (!jsonData || jsonData.length === 0) {
          throw new Error("文件内容为空或格式不正确！");
        }

        // 2. 数据格式化（关键：将前端字段映射到后端字段）
        const formattedData = jsonData.map((item, index) => ({
          // 🔴 请根据你的Excel表头，修改这里的字段映射
          clueNo: item["线索编号"] || `FX-${Date.now()}-${index}`, // 自动生成或读取
          clueSourceOrg: item["线索来源机构"] || "",
          clueSourceChannel: item["线索来源渠道"] || "",
          clueSubmitTime: item["线索报送时间"] || this.formatDate(new Date()),
          riskSubjectInfo: `
            ${item["涉险主体名称"] || ""}
            统一社会信用代码：${item["统一社会信用代码"] || ""}
            地址：${item["涉险主体地址"] || ""}
          `
            .replace(/^\n+/, "")
            .replace(/\n+$/, ""), // 去除首尾换行
          clueType: item["线索类型"] || "非法集资",
          clueStatus: item["线索状态"] || "待复核",
          transferStatus: "未移交",
          receiveSigner: "-",
          signDate: "-",
          receiveFeedback: "-",
        }));

        // 3. 假实现：直接提示成功，不调用后端
        this.$message.success(`成功导入 ${formattedData.length} 条风险线索！`);
        this.$emit("success", formattedData); // 将数据传给父组件
        this.handleClose();
      } catch (error) {
        console.error("导入失败:", error);
        this.$message.error("导入失败：" + error.message);
      } finally {
        this.loading = false;
      }
    },

    // 简单日期格式化
    formatDate(date) {
      return date.toISOString().slice(0, 19).replace("T", " ");
    },
  },
};
</script>

<style lang="scss" scoped>
.import-box {
  padding: 20px 0;
}

.upload-dragger {
  ::v-deep .el-upload {
    width: 100%;
  }
  ::v-deep .el-upload-dragger {
    width: 100%;
    height: 180px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    border: 1px dashed #d9d9d9;
    border-radius: 4px;
    transition: all 0.3s;
    &:hover {
      border-color: #409eff;
      background-color: #f8f9fa;
    }
  }
  ::v-deep .el-icon-upload {
    font-size: 48px;
    color: #c0c4cc;
    margin-bottom: 16px;
  }
  .el-upload__text {
    font-size: 16px;
    color: #606266;
  }
  .el-upload__tip {
    font-size: 14px;
    color: #909399;
    margin-top: 8px;
  }
}

.tips {
  margin-top: 16px;
  padding: 12px;
  background: #f5f7fa;
  border-radius: 4px;
  p {
    margin: 4px 0;
    font-size: 14px;
  }
  .tips-note {
    color: #909399;
  }
}
</style>
