<template>
  <IDialog :title="title" v-model="showDialog" width="50%" :showFooter="false">
    <div class="task-detail-form">
      <el-form label-width="160px" :model="form">
        <el-form-item label="任务要求">
          <el-input
            type="textarea"
            :rows="3"
            v-model="form.requirement"
            :disabled="taskType == 'view'"
          />
        </el-form-item>
        <el-form-item label="政策公示与宣传">
          <el-checkbox v-model="form.policyPublicity" :disabled="taskType == 'view'">是否在网点显著位置张贴“接受现金支付”标识？</el-checkbox>
        </el-form-item>
        <el-form-item label="内部培训与监督">
          <el-checkbox v-model="form.internalTraining" :disabled="taskType == 'view'">是否组织员工学习《人民币管理条例》《整治拒收现金公告》</el-checkbox>
        </el-form-item>
        <el-form-item label="现金收付服务支持">
          <el-checkbox v-model="form.cashSupport" :disabled="taskType == 'view'">是否组织员工学习《人民币管理条例》《整治拒收现金公告》</el-checkbox>
        </el-form-item>
        <el-form-item label="检查与整改要求">
          <el-checkbox v-model="form.checkRectify" :disabled="taskType == 'view'">每月是否开展拒收现金自查检查？</el-checkbox>
        </el-form-item>
        <el-form-item label="现金服务基础设施">
          <el-checkbox v-model="form.cashFacility" :disabled="taskType == 'view'">点钞机、清分机等设备是否定期维护，确保正常运行？</el-checkbox>
        </el-form-item>
        <el-form-item label="相关附件上传">
          <el-upload
            class="upload-demo"
            action="#"
            :file-list="fileList"
            :on-remove="handleRemove"
            :on-change="handleChange"
            :auto-upload="false"
            :show-file-list="true"
            list-type="text"
            multiple
            :disabled="taskType == 'view'"
          >
            <template #file="{file}">
              <div class="custom-file-item">
                <svg-icon :icon-class="getFileIcon(file)" class="icon"></svg-icon>
                <div class="file-name">{{ file.name }}</div>
                <svg-icon icon-class="close-icon" class="file-remove" @click.stop="handleRemove(file, fileList)"></svg-icon>
              </div>
            </template>

            <div class="upload">
              <div class="upload-btn">
                <div class="upload-img"></div>
                <div class="upload-text">上传附件</div>
              </div>
              <div class="max-text">最大(100M/个)</div>
            </div>
          </el-upload>
        </el-form-item>
      </el-form>
    </div>
  </IDialog>
</template>

<script>
import dialogMixin from "@/mixins/dialogMixin.vue";
export default {
  mixins: [dialogMixin],
  props: {
    defaultValue: {
      type: Object,
      default: () => ({}),
    },
    title:{
      type: String,
      default: '任务详情'
    },
    // 任务类型，view:查看详情；edit：编辑详情
    taskType:{
      type:String,
      default:'view',
    }
  },
  data() {
    return {
      loading: false,
      form: {
        requirement:
          '落实从网点显著位置张贴“接受现金支付”标识、设立“小面额现金兑换绿色通道”，是否提供残损币兑换、大额现金存取服务，保留人工现金窗口(尤其针对老年人、境外人士等群体)',
        policyPublicity: false,
        internalTraining: false,
        cashSupport: false,
        checkRectify: false,
        cashFacility: false,
      },
      fileList: [
        // 示例数据，实际可为空
        // { name: '网点现金服务质量任务.xlsx', url: '' },
        // { name: '网点现金服务质量任务.doc', url: '' },
      ],
    };
  },
  methods: {
    handleRemove(file, fileList) {
      this.fileList = fileList.filter(f => f.uid !== file.uid);
    },
    handleChange(file, fileList) {
      this.fileList = fileList;
    },
    getFileIcon(file) {
      const ext = file.name.split('.').pop().toLowerCase();
      if (ext === 'doc' || ext === 'docx') return 'word';
      if (ext === 'xls' || ext === 'xlsx') return 'excel-icon';
      return 'excel-icon';
    },
  },
};
</script>

<style lang="scss" scoped>
.task-detail-form {
  padding: 10px 20px;
  .el-form-item {
    margin-bottom: 18px;
  }
  .custom-file-item {
    display: flex;
    align-items: center;
    padding: 8px;
    border: 1px solid #E0E2EC;
    .icon {
      width: 21px;
      height: 24px;
    }
    .file-name {
      flex: 1;
      font-size: 14px;
      color: #323548;
      margin-right: 8px;
      word-break: break-all;
      margin-left: 8px;
    }
    .file-remove {
      cursor: pointer;
      margin-left: 4px;
      width: 10px;
      height: 10px;
    }
  }
}
.upload {
  display:flex;
  align-items: center;
  .max-text {
    font-size: 12px;
    color: #999;
    height: 36px;
    margin-left: 8px;
    font-size: 14px;
  }
  .upload-btn {
  display:flex;
  justify-content: center;
  align-items: center;
  width: 104px;
  height: 36px;
  border-radius: 4px;
  border: 1px solid #E0E2EC;
  .upload-img {
    width: 20px;
    height: 20px;
    background-image: url('~@/modules/module-gridManagement/assets/images/upload.png');
    background-size: 100% 100%;
  }
  .upload-text {
    color: #323548;
    margin-left: 8px;
  }
}
}

</style>
  