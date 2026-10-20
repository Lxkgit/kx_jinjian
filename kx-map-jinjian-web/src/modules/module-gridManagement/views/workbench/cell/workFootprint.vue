<template>
  <div class="work-footprint-wrapper">
    <div class="task-wrapper">
      <div class="task-total">
        <span class="item-label total-label">任务总数</span>
        <span class="item-value total-value">{{ taskInfo.taskTotalNumber }}</span>
      </div>
      <div class="task-performance">
        <div class="item-box">
          <span class="item-label">已完成</span>
          <span class="item-value per-value">{{ taskInfo.finishTaskNumber }}</span>
        </div>
        <span class="per-value">/</span>
        <div class="item-box">
          <span class="item-label">未完成</span>
          <span class="item-value per-value">{{ taskInfo.unFinishTaskNumber }}</span>
        </div>
      </div>
      <div class="task-progress">
        <el-progress :percentage="taskInfo.finishRatio?Number(taskInfo.finishRatio):0"
                     :show-text="false" :stroke-width="9" define-back-color="#fff"></el-progress>
      </div>
    </div>
    <div class="detail-wrapper">
      <i-table ref="table" :loading="loading" :data="tableData" align="center" stripe size="mini"
               :allColumns="columns" :showPagination="false" showIndex :maxHeight="maxHeight">
        <template #action="scope">
          <el-button type="text">查看</el-button>
        </template>
      </i-table>
    </div>
  </div>
</template>

<script>
import service from '../api';
export default ({
  props: {
    formParam: {
      type: Object
    }
  },
  data() {
    return {
      loading: false,
      tableData: [],
      maxHeight: '100%',
      taskInfo: {},
      columns: [
        { label: '日期', prop: 'startTime' },
        { label: '任务数量', prop: 'taskTotalNumber' },
        { label: '完成任务数', prop: 'finishTaskNumber' },
        {
          label: '完成率', prop: 'finishRatio', formatter: ({ finishTaskNumber, taskTotalNumber }) => {
            return `${(finishTaskNumber / taskTotalNumber * 100).toFixed(2)}%`
          }
        },
        { label: '操作', slot: true },
      ]
    }
  },
  watch: {
    formParam: {
      handler() {
        this.workTrackList()
      },
      deep: true
    },
  },
  mounted() {
    this.workTrackOverview()
    this.workTrackList()
  },
  methods: {
    workTrackOverview() {
      service.workTrackOverview().then(res => {
        const { data } = res
        this.taskInfo = data
      })
    },
    workTrackList() {
      service.workTrackList(this.formParam).then(res => {
        const { rows } = res
        this.tableData = rows
      })
    },
  },
})
</script>

<style lang="scss" scoped>
.work-footprint-wrapper {
  height: calc(100% - 18px);
  width: 100%;
  display: flex;
  justify-content: space-between;
  .task-wrapper {
    width: 350px;
    padding: 20px 36px;
    height: 100%;
    background: url(../assets/total-bg.png) no-repeat;
    background-size: 100% 100%;
    position: relative;

    .task-total {
      position: absolute;
      top: 20px;
      left: 36px;
    }
    .task-performance {
      position: absolute;
      bottom: 40px;
      left: 50%;
      transform: translateX(-50%);
      display: flex;
      justify-content: space-between;
      align-items: center;
      .item-box {
        display: flex;
        align-items: center;
      }
    }
    .task-progress {
      position: absolute;
      bottom: 20px;
      width: calc(100% - 56px);
      left: 50%;
      transform: translateX(-50%);
    }
    .item-label,
    .item-value {
      font-size: 16px;
      color: #666666;
      white-space: nowrap;
    }

    .total-label {
      line-height: 1;
      color: #333333;
      font-weight: bold;
    }
    .total-value {
      display: block;
      font-size: 44px;
      line-height: 1.5;
      font-weight: bold;
      color: #5e81f4;
    }
    .per-value {
      margin: 0 10px;
      color: #1c1a1d;
      font-weight: bold;
      font-size: 30px;
    }
  }
  .detail-wrapper {
    width: calc(100% - 370px);
    height: 100%;
    ::v-deep .el-table th.el-table__cell {
      background: #e3e5e8;
    }
    ::v-deep .el-table .el-table__header-wrapper th,
    ::v-deep .el-table .el-table__body-wrapper td.el-table__cell {
      height: 40px;
    }
  }
}
</style>