<template>
  <IDialog title="新增标准化资料" v-model="showDialog" @handleClose="handleClose"
           @handleConfirm="handleConfirm">
    <i-form ref="myForm" :columns="columns" label-width="80px">
      <template #upload>
        <el-upload class="upload-demo" :action="upload.action" :headers="upload.headers" :limit="1"
                   :file-list="fileList" :on-success="successHandle" :on-remove="removeHandle">
          <el-button type="primary">文件上传</el-button>
        </el-upload>
      </template>
    </i-form>
  </IDialog>
</template>

<script>
import dialogMixin from '@/mixins/dialogMixin.vue';
import { getToken } from "@/utils/auth";
import service from '../api'
export default ({
  mixins: [dialogMixin],
  data() {
    return {
      columns: [
        {
          label: '创建人',
          key: 'createBy',
          value: '',
          type: 'text',
          disabled: true,
        },
        {
          label: '创建时间',
          key: 'createTime',
          value: '',
          type: 'text',
          disabled: true,
        },
        {
          label: '模板名称',
          key: 'fileName',
          value: '',
          type: 'text',
          rules: [
            { required: true, message: '模板名称不能为空' }
          ]
        },
        {
          label: '模板文件',
          type: 'slot',
          slotName: 'upload'
        },
      ],
      upload: {
        action: process.env.VUE_APP_BASE_API + "/system/minio/upload",
        headers: { Authorization: "Bearer " + getToken() },
      },
      fileList: []
    }
  },
  computed: {
    userName() {
      return this.$store.getters['userInfo'].userName
    }
  },
  methods: {
    initData() {
      const obj = {
        createBy: this.userName
      }
      this.fileList = []
      this.$nextTick(() => {
        this.$refs.myForm.setForm(obj)
      })
    },
    successHandle(res) {
      const { url, fileName } = res
      this.fileList.push({ name: fileName, url })
    },
    removeHandle(file) {
      const index = this.fileList.indexOf(file)
      this.fileList.splice(index, 1)
    },
    handleClose() {
      this.$refs.myForm.reset()
      this.showDialog = false
    },
    handleConfirm() {
      if (this.fileList.length === 0) {
        this.$modal.msgWarning('模板文件不能为空')
        return
      }
      this.$refs.myForm.getForm(formInfo => {
        const { fileName } = formInfo
        let param = {
          fileName,
          fileUrl: this.fileList[0].url
        }
        service.standardSave(param).then(res => {
          this.$modal.msgSuccess('新增成功')
          this.$parent.fetchList()
          this.handleClose()
        })
      })
    }
  },
})
</script>

<style lang="scss" scoped>
</style>