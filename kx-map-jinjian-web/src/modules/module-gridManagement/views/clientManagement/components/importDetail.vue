<template>
  <i-dialog
    :title="title"
    v-model="showDiolag"
    width="40%"
    @handleClose="handleClose"
    @handleConfirm="handleConfirm"
    appendToBody
  >
    <div class="import-detail">
      <div class="download-template">
        <div class="title">1、下载导入模板</div>
        <div class="content">根据提示信息完成表格内容</div>
        <div class="download-box">
          <div class="download-content" @click="dowlonadHandle">
            <svg-icon icon-class="arrowDown" class="svgIcon"></svg-icon>
            <span>下载空的模板表格</span> 
          </div>
        </div>
      </div>
      <el-upload
        ref="upload"
        class="upload-demo"
        :action="upload.action"
        :limit="1"
        :on-change="handleFileChange"
        :auto-upload="false"
        accept=".xlsx, .xls"
        :on-exceed="handleExceed"
        :file-list="fileList"
        :show-file-list="false"
        drag
      > 
        <div class="upload-box">
         <div class="upload-template" v-if="!file">
           <div class="img-box"></div>
           <div class="template-text">选择文件</div>
           <div class="template-toolip">可直接将文件拖拽到此处进行上传，支持格式：XLS、XLSX</div>
         </div>
         <div class="upload-template" v-else>
          <div class="img-box-file"></div>
          <div class="template-text">{{ file.name }}</div>
          <el-button @click="clear">重新选择</el-button>
         </div>
        </div>
      </el-upload>
    </div>
  </i-dialog>
</template>

<script>
import { getToken } from "@/utils/auth";
import services from "../api";
export default {
  props: {
    value: {
      type: Boolean,
    },
    title:{
      type:String,
      default: "网点导入"
    }
  },
  data() {
    return {
      showDiolag: this.value,
      upload: {
        action:
          process.env.VUE_APP_BASE_API +
          "/branch/branch/import",
        headers: { Authorization: "Bearer " + getToken() },
      },
      fileList: [],
      file: null,
    };
  },
  watch: {
    value: {
      handler(val) {
        this.showDiolag = val;
        if (val) {
          this.fileList = [];
          this.file = ''
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
    dowlonadHandle() {
      services.modelDownload().then((res) => {
        const { msg } = res;
        this.download("/system/common/download", { fileName: msg }, msg);
      });
    },
    handleFileChange(file) {
      this.file = file.raw;
    },
    handleClose() {},
    handleConfirm() {
      if (!this.file) {
        this.$modal.msgError("请先选择文件");
        return;
      }
      const formData = new FormData();
      formData.append("file", this.file);
      services
        .branchImport(formData)
        .then((res) => {
          if (res.code == 200) {
            this.$modal.msgSuccess("文件上传成功");
            this.showDiolag = false;
            this.$parent.fetchList();
          }
        })
        .catch((err) => {
          this.$modal.msgError("文件上传失败");
        });
    },
    handleExceed(files, fileList) {
      this.$modal.msgWarning(`当前限制选择 1 个文件`);
    },
    //清空已选择的文件
    clear() {
      this.file = ''
      this.fileList = []
    },
  },
};
</script>

<style scoped lang="scss">
::v-deep .el-dialog__header {
  border-bottom: 1px solid #e8e8e8;
  padding: 20px;
}
::v-deep .el-dialog__body {
  padding: 24px 22px;
}
::v-deep .el-dialog__footer {
  padding: 12px 24px;
  border-top: 1px solid #e8e8e8;
}
.import-detail {
  .download-template {
    height: 160px;
    background: #F6F7FA;
    padding: 20px 24px;
    margin-bottom: 16px;
    border-radius: 4px;
    position: relative;
    .title {
      font-size: 14px;
      color: #323548;
    }
    .content {
      font-size: 14px;
      color: #898EAC;
      margin-top: 8px;
    }
    .download-box {
      position: absolute;
      left: 35%;
      bottom: 25px;
      width: 172px;
      height: 36px;
      padding: 7px 10px;
      background: rgba(94,129,244,0.1);
      border-radius: 4px;
      color: #5E81F4;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      .download-content {
        display: flex;
        align-items: center;
        cursor: pointer;
        .svgIcon {
          margin-right: 8px;
        }
      }
    }
  }
  .upload-demo {
    width: 100%;
    height: 264px;
    background: #F6F7FA;
    border: none;
    padding: 20px 24px;
    position: relative;
    .upload-title {
      position: absolute;
      left: 0;
    }
    ::v-deep .el-upload--text {
      width: calc(100% - 24px) !important;
      height: 178px;
      position: absolute;
      top: 65px;
    }
    ::v-deep .el-upload-dragger {
      width: calc(100% - 24px) !important;
      height: 178px;
    }
  }
  .upload-demo::before {
    content:'2、上传完善后的表格';
    position: absolute;
    left: 24px;
    font-size: 14px;
    color: #323548;
  }
  .upload-box {
    padding:24px 0;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    .upload-template {
      height: 100%;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: space-around;
      .img-box {
        width: 48px;
        height: 48px;
        background-image: url('~@/modules/module-gridManagement/assets/images/upload.png');
        background-size: 100% 100%;
      }
      .template-text {
        color: #494D68;
        font-size: 14px;
      }
      .template-toolip {
        color: #898EAC;
        font-size: 14px;
      }
      .img-box-file {
        width: 48px;
        height: 48px;
        background-image: url('~@/modules/module-gridManagement/assets/images/file-excel.png');
        background-size: 100% 100%;
      }
    }
  }
}
</style>