<template>
  <IDialog title="任务执行对象变更" v-model="showDialog" @handleClose="handleClose"
           @handleConfirm="handleConfirm" :loading="loading">
    <div class="task-object-change-form">
      <el-form label-width="140px" :model="form">
        <el-form-item label="当前任务执行人对象">
          <el-input v-model="form.currentObject" readonly disabled />
        </el-form-item>
        <el-form-item label="变更后任务执行对象">
          <el-select v-model="form.newObject" placeholder="请选择任务执行对象" style="width:100%">
            <el-option
              v-for="item in objectOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
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
  },
  data() {
    return {
      loading:false,
      form: {
        currentObject: '工商银行金华分行', 
        newObject: '',
      },
      objectOptions: [
        { label: '农业银行金华分行', value: 'abc' },
        { label: '建设银行金华分行', value: 'ccb' },
      ],
    };
  },
  methods: {
    initData() {},
    handleClose() {
      this.showDialog = false;
    },
    handleConfirm() {
      // 提交逻辑
    },
  },
};
</script>

<style lang="scss" scoped>
.task-object-change-form {
  padding: 10px 20px 0 20px;
  .el-form-item {
    margin-bottom: 22px;
  }
  .dialog-footer-btns {
    display: flex;
    justify-content: flex-end;
    margin-top: 20px;
    .el-button + .el-button {
      margin-left: 12px;
    }
  }
}
</style>
  