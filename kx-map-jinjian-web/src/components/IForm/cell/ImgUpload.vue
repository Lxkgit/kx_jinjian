<template>
  <div>
    <el-upload ref="uploadDom" :disabled="disabled" list-type="picture-card" :data="upload.data"
               accept=".jpg,.png,.jpge" :headers="upload.headers" :action="upload.url"
               :on-success="handSuccess" :limit="limit" :on-exceed="handleExceed"
               :before-upload="beforeUploadHandle" :file-list="fileList"
               :on-error="handleUploadError">
      <i slot="default" class="el-icon-plus"></i>
      <div slot="file" slot-scope="{file}" class="upload-list_content">
        <img class="el-upload-list__item-thumbnail" :src="file.url" alt="">
        <span class="el-upload-list__item-actions">
          <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
            <i class="el-icon-zoom-in"></i>
          </span>
          <span v-if="!disabled" class="el-upload-list__item-delete" @click="beforeRemove(file)">
            <i class="el-icon-delete"></i>
          </span>
        </span>
      </div>
    </el-upload>
    <el-dialog :modal="false" :visible.sync="dialogVisible" append-to-body>
      <img width="100%" :src="dialogImageUrl" alt="" />
    </el-dialog>
  </div>
</template>

<script>
import service from '../api'
import { getToken } from "@/utils/auth";
export default ({
  props: {
    value: {
      type: [Array, String],
    },
    disabled: {
      type: Boolean,
    },
    fileType: {
      type: Array,
      default: () => ['png', 'jpg', 'jpeg']
    },
    fileSize: {
      type: Number,
      default: 1,
    }
  },
  data() {
    return {
      limit: 10,
      fileList: [],
      upload: {
        headers: { Authorization: "Bearer " + getToken() }, // 设置上传的请求头部
        url: process.env.VUE_APP_BASE_API + "/system//minio//upload", // 上传的地址
        data: { fileType: 99 }
      },
      dialogImageUrl: null,
      dialogVisible: false
    }
  },
  watch: {
    value: {
      handler(val) {
        if (Array.isArray(val)) {
          this.fileList = val
        } else {
          this.fileList = []
        }
      },
      immediate:true
    },
    fileList: {
      handler(val) {
        this.$emit('input', val)
      },
      deep: true
    },
    disabled: {
      handler(val) {
        this.$nextTick(() => {
          if (val) {
            this.$refs.uploadDom.$children[1].$el.style.display = 'none'
          } else {
            this.$refs.uploadDom.$children[1].$el.style.display = ''
          }
        })
      },
      immediate: true,
    }
  },
  methods: {
    base64toBlob(urlData) {
      let arr = urlData.split(',')
      let type = arr[0].match(/:(.*?);/)[1]
      let bstr = atob(arr[1])
      let n = bstr.length
      let u8arr = new Uint8Array(n)
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n)
      }
      return new Blob([u8arr], {
        type
      })
    },
    compressFile(image, imageType, quality, callback) {
      const canvas = document.createElement('canvas')
      const ctx = canvas.getContext('2d')
      const img = new Image()
      img.onload = () => {
        canvas.width = img.width
        canvas.height = img.height
        ctx.drawImage(img, 0, 0)
        const dataUrl = canvas.toDataURL(imageType, quality / 100)
        callback(dataUrl)
      }
      img.src = image
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // 文件列表移除文件时
    handleRemove(file) {
      const index = this.fileList.indexOf(file)
      this.fileList.splice(index, 1)
    },
    // 文件上传成功时
    handSuccess(res) {
      const { url, fileName }  = res
      this.fileList.push({ name: fileName, url })
    },
    // 删除文件之前
    beforeRemove(file) {
      return this.$modal.confirm(`确定移除 ${file.name}？`).then(() => {
        this.handleRemove(file)
      });
    },
    // 文件超出限制时
    handleExceed(files) {
      this.$modal.msgWarning(
        `当前限制选择 ${limit} 个文件，本次选择了 ${files.length} 个文件`
      );
    },
    imageName(imageURL) {
      return imageURL.substring(imageURL.lastIndexOf('/') + 1)
    },
    beforeUploadHandle(file) {
      if (file.type.startsWith('image/')) {
        let reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onload = (e) => {
          this.compressFile(e.target.result, file.type, 80, (cImage) => {
            const imageBlob = this.base64toBlob(cImage)
            const newFile = new File([imageBlob], file.name, {
              type: file.type,
              lastModified: Date.now()
            })
            const param = new FormData()
            param.append("file", newFile)
            param.append('fileType', 99)
            service.upload(param).then(res => {
              this.handSuccess(res)
            })
            return newFile
          })
        }
        return false
      } else {
        this.$modal.msgError('请上传图片文件')
        return false
      }
    },
    handleUploadError() {
      this.$modal.msgError('上传图片失败,请重试')
    },
  },
})
</script>

<style lang="scss" scoped>
::v-deep .el-upload-list__item,
::v-deep .el-upload-list__item-name {
  transition: none;
  -webkit-transition: none;
}
.upload-list_content {
  width: 100%;
  height: 100%;
}
</style>