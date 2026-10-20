<template>
  <IDialog :title="title" v-model="showDialog" @handleClose="handleClose"
           @handleConfirm="handleConfirm" :confirmDisabled="confirmDisabled">
    <el-form :rules="rules" ref="formData" :model="formData" label-width="100px">
      <el-form-item label="日程标题" prop="scheduleTitle">
        <el-input v-model="formData.scheduleTitle" placeholder="请输入日程标题"></el-input>
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker class="form-item" type="datetime" v-model="formData.startTime"
                        value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择开始时间"
                        :picker-options="startPickerOptions" default-time="08:00:00">

        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker class="form-item" type="datetime" v-model="formData.endTime"
                        value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择结束时间"
                        :picker-options="endPickerOptions" default-time="18:00:00">
        </el-date-picker>
      </el-form-item>
      <el-row>
        <el-col :span="8" v-if="status === 'add'">
          <el-form-item label="是否重复">
            <el-switch v-model="formData.isRepeat" :active-value="1" :inactive-value="0">
            </el-switch>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="是否全天">
            <el-switch v-model="formData.isAllDay" :active-value="1" :inactive-value="0">
            </el-switch>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="到期自动完成">
            <el-switch v-model="formData.autoFinish" :active-value="1" :inactive-value="0">
            </el-switch>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item label="是否提醒">
        <el-switch v-model="formData.needNotice" :active-value="1" :inactive-value="0">
        </el-switch>
      </el-form-item>
      <el-form-item label="提醒时间" v-if="formData.needNotice==1">
        <el-select class="form-item" v-model="formData.noticeTimeType">
          <el-option v-for="item in dict.type.notice_time_type" :key="item.value"
                     :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="参与人员" prop='partUserIds'>
        <el-select class="form-item" v-model="formData.partUserIds" multiple placeholder="请选择参与人员">
          <el-option v-for="item in userOptions" :key="item.userId" :label="item.nickName"
                     :value="item.userId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="日程标记" prop="markColor">
        <div class="circle-list">
          <div @click="chooseCircle(color.value)"
               v-for="(color, index) in dict.type.mark_color_type" :key="index" class="circle-item"
               :style="{ '--background': color.value }">
            <i class="circle-icon el-icon-check"
               :class="{active: formData.markColor === color.value}"></i>
          </div>
        </div>
      </el-form-item>
      <el-form-item label="日程描述">
        <el-input type="textarea" :rows="4" placeholder="请输入日程描述" v-model="formData.remark" />
      </el-form-item>
    </el-form>
  </IDialog>
</template>

<script>
import dialogMixin from '@/mixins/dialogMixin.vue';
import { number } from 'echarts';
import service from '../api';
const STATUS_TYPE = {
  add: '新增',
  update: '编辑',
}
export default ({
  mixins: [dialogMixin],
  dicts: ['notice_time_type', 'mark_color_type'],
  props: {
    status: {
      type: String,
      default: 'add'
    },
    editInfo: {
      type: Object
    },
    dateTime: {
      type: Date
    },
  },
  data() {
    return {
      confirmDisabled: false,
      formData: {
        markColor: '',
        scheduleTitle: '',
        startTime: null,
        endTime: null,
        isRepeat: 0,
        isAllDay: 0,
        autoFinish: 0,
        needNotice: 0,
        noticeTimeType: '',
        partUserIds: [],
        remark: '',
      },
      rules: {
        scheduleTitle: [{ required: true, message: '请输入日程标题', trigger: 'blur' }],
        startTime: [{ required: true, type: 'string', message: '请选择日程开始时间', trigger: 'change' }],
        endTime: [{ required: true, type: 'string', message: '请选择日程结束时间', trigger: 'change' }],
      },
      userOptions: [],
      startPickerOptions: {
        disabledDate: (date) => {
          if (this.formData.endTime) {
            const timeStamp = new Date(this.formData.endTime).getTime()
            return date.getTime() > timeStamp || date.getTime() < Date.now() - 24 * 60 * 60 * 1000
          } else {
            return date.getTime() < Date.now() - 24 * 60 * 60 * 1000
          }
        },
      },
      endPickerOptions: {
        disabledDate: (date) => {
          if (this.formData.startTime) {
            const timeStamp = new Date(this.formData.startTime).getTime()
            return date.getTime() < timeStamp
          } else {
            return date.getTime() < Date.now()
          }
        },
      },
    }
  },
  computed: {
    title() {
      return STATUS_TYPE[this.status]
    }
  },
  methods: {
    initData() {
      this.listAllUser()
      if (this.status == 'add') {
        this.formData.startTime = this.dateTime
        this.formData.endTime = this.dateTime
        return
      }
      this.formData = Object.assign(this.formData, this.editInfo)
      this.formData.partUserIds = this.formData.ownerIds.split(',').map(item => Number(item))
    },
    listAllUser() {
      service.listAllUser().then(res => {
        const { data } = res
        this.userOptions = data
      })
    },
    chooseCircle(color) {
      this.formData.markColor = color;
    },
    handleClose() {
      this.formData = {
        markColor: '',
        scheduleTitle: '',
        startTime: null,
        endTime: null,
        isRepeat: 0,
        isAllDay: 0,
        autoFinish: 0,
        needNotice: 0,
        noticeTimeType: '',
        partUserIds: [],
        remark: '',
      }
      this.$refs.formData.resetFields()
      this.showDialog = false
    },
    handleConfirm() {
      this.$refs.formData.validate(async valid => {
        if (valid) {
          this.confirmDisabled = true
          const param = {
            ...this.formData,
            ownerIds: this.formData.partUserIds.join(',')
          }
          try {
            const res = this.status === 'add' ? await service.scheduleAdd(param) : await service.scheduleEdit(param)
            this.$modal.msgSuccess(`${STATUS_TYPE[this.status]}成功!`)
            this.handleClose()
            this.$parent.fetchList()
            this.confirmDisabled = false
          } catch {
            this.confirmDisabled = false
          }

        }
      })
    },
  },
})
</script>

<style lang="scss" scoped>
.form-item {
  width: 100%;
}
.circle-list {
  height: 36px;
  display: flex;
  align-items: center;
  .circle-item {
    margin-right: 6px;
    width: 20px;
    height: 20px;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    background: var(--background);
    color: #fff;
    .circle-icon {
      display: none;
      &.active {
        display: block;
      }
    }
  }
}
</style>