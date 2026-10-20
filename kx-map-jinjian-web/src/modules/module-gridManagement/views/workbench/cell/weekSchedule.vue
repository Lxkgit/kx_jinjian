<template>
  <div class="week-schedule-wrapper">
    <weekCard :signList="signList"></weekCard>
    <div class="task-wrapper">
      <i class="task-logo"></i>
      <span class="task-content">您当日有<span>{{ taskNum }}</span>项日程</span>
    </div>
    <taskTimeline :timelineData="timelineData"></taskTimeline>

  </div>
</template>

<script>
import service from '../api';
import weekCard from '../components/weekCard';
import taskTimeline from '../components/taskTimeline';
export default ({
  components: {
    weekCard,
    taskTimeline
  },
  data() {
    return {
      signList: [0, 0, 0, 0, 0, 0, 0],
      taskNum: 0,
      timelineData: [],
    }
  },
  mounted() {
  },
  methods: {
    weekSchedule(week, current) {
      const [lastDay] = week.slice(-1)
      const param = {
        startTime: `${this.parseTime(week[0].date, '{y}-{m}-{d}')} 00:00:00`,
        endTime: `${this.parseTime(lastDay.date, '{y}-{m}-{d}')} 23:59:59`
      }
      service.weekSchedule(param).then(res => {
        const { data } = res
        week.forEach((item, index) => {
          const schedule = data.filter(el => this.parseDateTime(el.startTime) === this.parseDateTime(item.date))
          if (schedule.length > 0) {
            this.$set(this.signList, index, 1)
          } else {
            this.$set(this.signList, index, 0)
          }

          if (this.parseTime(item.date, '{a}') === this.parseTime(current, '{a}')) {

            this.taskNum = schedule.length
            this.timelineData = schedule
          }
        })
      })

    },
    parseDateTime(time) {
      return this.parseTime(time, '{y}-{m}-{d}')
    }
  },
})
</script>

<style lang="scss" scoped>
.week-schedule-wrapper {
  height: 100%;
  .task-wrapper {
    margin: 15px 0 30px;
    height: 47px;
    line-height: 47px;
    background: rgba(94, 129, 244, 0.13);
    border-radius: 4px;
    font-size: 16px;
    color: #5e81f4;
    display: flex;
    align-items: center;

    .task-logo {
      display: inline-block;
      margin: 0 17px 0 12px;
      width: 15px;
      height: 15px;
      background: url(../assets/task.png) no-repeat;
    }
    .task-content {
      > span {
        display: inline-block;
        margin: 0 10px;
      }
    }
  }
}
</style>