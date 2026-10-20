<template>
  <div class="task-timeline-wrapper">
    <el-timeline v-if="timelineData.length>0">
      <el-timeline-item v-for="(item,index) in timelineData" :key="index" :timestamp="item.time"
                        placement="top" type="primary">
        <el-card>
          <span class="task-title">{{  item.scheduleTitle }}</span>
          <div class="task-item">
            <span class="task-name">人员</span>
            <span class="task-person" v-for="(el,i) in item.ownerNames&&item.ownerNames.split(',')"
                  :key="i">
              <i class="person-logo"></i>
              {{ el }}
            </span>
          </div>
          <div class="task-item">
            <span class="task-name">日程描述</span>
            <el-tooltip effect="dark" :content="item.remark" placement="top-start">
              <span class="task-value">{{ item.remark }}</span>
            </el-tooltip>
          </div>
          <div class="task-line">
            <div class="task-item">
              <span class="task-name">创建人</span>
              <span class="task-value">{{ item.createByName }}</span>
            </div>
            <div class="task-item">
              <span class="task-name">创建时间</span>
              <span class="task-value">{{ item.createTime }}</span>
            </div>
          </div>
        </el-card>
      </el-timeline-item>
    </el-timeline>
    <el-empty v-else description="暂无数据"></el-empty>
  </div>
</template>

<script>
export default ({
  props: {
    timelineData: {
      type: Array,
    }
  },
})
</script>

<style lang="scss" scoped>
.task-timeline-wrapper {
  height: calc(100% - 300px);
  padding: 0 4px;
  overflow: auto;
  &::-webkit-scrollbar {
    width: 0;
  }
  ::v-deep .el-empty {
    padding: 0;
  }
  ::v-deep .el-card {
    background: #f6f6f6;
  }
  .task-title {
    display: block;
    margin-bottom: 12px;
    font-size: 16px;
    font-weight: bold;
    color: #333;
  }
  .task-line {
    display: flex;
    justify-content: space-between;
  }
  .task-item {
    display: flex;
    align-items: center;
    .task-name {
      min-width: 56px;
      font-size: 14px;
      color: #666666;
      line-height: 2;
      display: inline-block;
      margin-right: 8px;
    }
    .task-person {
      font-size: 14px;
      font-weight: bold;
      color: #3c4892;
      display: flex;
      align-items: center;
      margin-right: 8px;
      .person-logo {
        display: inline-block;
        width: 18px;
        height: 18px;
        margin-right: 6px;
        background: url(../assets/person.png) no-repeat;
        background-size: 100% 100%;
      }
    }
    .task-value {
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }
  }
}
</style>