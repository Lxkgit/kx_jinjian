<template>
  <i-dialog title="导出范围选择" v-model="showDialog" width="45%" @handleClose="handleClose"
            @handleConfirm="handleConfirm">
    <el-form :model="addForm" ref="addForm" :rules="rules" :inline="true">
      <div class="form-row">
        <el-form-item label="导出页码" prop="exportPageStart">
          <el-input :size="size" v-model="addForm.exportPageStart" placeholder="请输入起始页码" />
        </el-form-item>
        <span>-</span>
        <el-form-item prop="exportPageEnd">
          <el-input :size="size" v-model="addForm.exportPageEnd" placeholder="请输入结束页码" />
        </el-form-item>
      </div>
      <div class="tip" v-if="showTips">
        提示：页码范围内数据量超过系统导出限制（上限10000条）
      </div>
    </el-form>
  </i-dialog>
</template>

<script>
import dialogMixin from '@/mixins/dialogMixin.vue';
export default {
  mixins: [dialogMixin],
  props: {
    dataInfo: {
      type: Object,
    },
    edportUrl: {
      type: String,
    },
    fileName: {
      type: String
    }
  },
  data() {
    return {
      size: 'mini',
      addForm: {
        exportPageStart: '',
        exportPageEnd: ''
      },
      rules: {
        exportPageStart: [
          { required: true, message: '请输入起始页码', trigger: ['change', 'blur'] },
          { pattern: /^[0-9]*$/, message: '请输入正确的页码', trigger: 'change' }
        ],
        exportPageEnd: [
          { required: true, message: '请输入结束页码', trigger: ['change', 'blur'] },
          { pattern: /^[0-9]*$/, message: '请输入正确的页码', trigger: 'change' }
        ]
      },
      showTips: false,
    };
  },
  methods: {
    initData() {

    },
    handleClose() {
      this.$refs.addForm.resetFields()
    },
    handleConfirm() {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          const { exportPageStart, exportPageEnd } = this.addForm
          if ((exportPageEnd - exportPageStart) * this.dataInfo.pageSize > 10000) {
            this.showTips = true
            return
          }
          this.showTips = false
          const param = {
            exportPageStart,
            exportPageEnd,
            ...this.dataInfo
          }
          this.download(
            this.edportUrl,
            param,
            `${this.fileName}_${new Date().getTime()}.xlsx`
          );
        }
      })
    },
  },
};
</script>

<style scoped lang="scss">
.form-row {
  display: flex;
  span {
    display: inline-block;
    line-height: 36px;
    margin: 0 10px;
  }
}
.tip {
  width: 100%;
  padding: 0 28px;
  display: flex;
  align-items: center;
  justify-content: flex-end;
  color: red;
  font-size: 12px;
}
</style>
