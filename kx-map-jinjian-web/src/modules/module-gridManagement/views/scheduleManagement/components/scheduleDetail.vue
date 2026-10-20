<template>
  <IDialog v-model="showDialog" @handleClose="handleClose" :showConfirm="false">
    <template #title>
      <div class="title-wrapper">
        <img src="../assets/calendar.png" alt="">
        <span>{{ editInfo&&editInfo.scheduleTitle }}</span>
      </div>
    </template>
    <!-- content -->
    <div class="date-wrapper">
      <dateDisplay :dateTime="editInfo&&editInfo.startTime" title="开始时间"></dateDisplay>
      <div class="date-interval">-</div>
      <dateDisplay :dateTime="editInfo&&editInfo.endTime" title="结束时间"></dateDisplay>
    </div>
    <el-descriptions :column="1" class="descriptions-wrapper">
      <el-descriptions-item v-for="(item,index) in scheduleDetail" :key="index" :label="item.label"
                            :span="item.span">{{ item.value }}</el-descriptions-item>
    </el-descriptions>
    <div class="detail-title">创建人</div>
    <personList :personList="creatorList"></personList>
    <div class="detail-title">参与人员</div>
    <personList :personList="partList"></personList>
    <template #foot>
      <el-button v-if="ifLastDay()" type="danger" @click="deleteHandle">删除</el-button>
      <el-button v-if="ifLastDay()" type="primary" @click="editHandle">编辑</el-button>
    </template>
  </IDialog>
</template>

<script>
import service from '../api';
import dialogMixin from '@/mixins/dialogMixin.vue';
import dateDisplay from './dateDisplay';
import personList from './personList';
export default ({
  mixins: [dialogMixin],
  components: {
    dateDisplay,
    personList
  },
  props: {
    editInfo: {
      type: Object,
    }
  },
  data() {
    return {
      scheduleDetail: [
        { label: '日程提醒', value: '', prop: 'noticeTimeTypeName', span: 1 },
        { label: '当前状态', value: '', prop: 'statusName', span: 1 },
        { label: '日程描述', value: '', prop: 'remark', span: 2 },
      ],
      creatorList: [],
      partList: [],
    }
  },
  methods: {
    initData() {
      this.scheduleDetail.forEach(item => {
        item.value = this.editInfo[item.prop]
      })
      this.creatorList = [this.editInfo.createByName]
      this.partList = this.editInfo.ownerNames && this.editInfo.ownerNames.split(',')
    },
    handleClose() {
      this.showDialog = false
    },
    deleteHandle() {
      this.$modal.confirm('确认要删除该日程?').then(() => {
        const param = {
          id: this.editInfo.id
        }
        service.scheduleDelete(param).then(res => {
          this.$modal.msgSuccess('删除成功')
          this.handleClose()
          this.$parent.fetchList()
        })
      }).catch(() => {

      })
    },
    editHandle() {
      this.$parent.editHandle(this.editInfo)
    },
    ifLastDay() {
      return new Date(this.editInfo && this.editInfo.endTime).getTime() > new Date().getTime()
    },

  },

})
</script>

<style lang="scss" scoped>
.title-wrapper {
  display: flex;
  align-items: center;
  > img {
    width: 18px;
    height: 18px;
    margin-right: 6px;
  }
  > span {
    font-size: 16px;
  }
}
.date-wrapper {
  display: flex;
  align-items: center;
  margin-bottom: 18px;
  .date-interval {
    margin: 0 10px;
    color: #539fff;
  }
}
.detail-title {
  font-size: 14px;
  margin: 6px 0;
}
</style>