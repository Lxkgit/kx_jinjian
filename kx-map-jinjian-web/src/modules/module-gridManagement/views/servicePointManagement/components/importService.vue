<template>
    <i-dialog
      title="导入"
      v-model="showDiolag"
      width="40%"
      @handleClose="handleClose"
      @handleConfirm="handleConfirm"
      appendToBody
    >
      <div class="import-detail">
        <div class="steps">
          <div class="stepsList" v-for="(item, index) in steps" :key="item.name">
            <span>{{ index + 1 }}</span>
            <span>{{ item.name }}</span>
          </div>
        </div>
        <el-button
          style="margin-top: 10px; float: right"
          type="text"
          @click="dowlonadHandle"
          >模版下载</el-button
        >
        <el-upload
          class="upload-demo"
          :action="upload.action"
          :limit="1"
          :on-change="handleFileChange"
          :auto-upload="false"
          accept=".xlsx, .xls"
          :on-exceed="handleExceed"
          :file-list="fileList"
          drag
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
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
    },
    data() {
      return {
        showDiolag: this.value,
        steps: [{ name: "模版下载" }, { name: "上传文件" }, { name: "确认导入" }],
        upload: {
          action:
            process.env.VUE_APP_BASE_API +
            "/system/performance/customer/importBatch",
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
          .importBatch(formData)
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
    },
  };
  </script>
  
  <style scoped lang="scss">
  .import-detail {
    .steps {
      display: flex;
      align-items: center;
      justify-content: space-around;
      .stepsList {
        span {
          &:first-child {
            display: inline-block;
            width: 30px;
            height: 30px;
            border-radius: 50%;
            background: #ccc;
            line-height: 30px;
            text-align: center;
            margin-right: 10px;
            color: #fff;
          }
          &:last-child {
            font-size: 16px;
            color: #333;
          }
        }
      }
    }
    .upload-demo {
      width: 100%;
      ::v-deep .el-upload--text {
        width: 100% !important;
      }
      ::v-deep .el-upload-dragger {
        width: 100% !important;
      }
    }
  }
  </style>