<template>
  <div class="calendar-wrapper">
    <div class="calendar-header">
      <div class="calendar-header_left">
        <slot name="header_left" />
      </div>
      <div class="calendar-header_center">
        <el-button type="text" class="el-icon-arrow-left calendar-header_btn" @click="preDate" />
        <div class="calendar-header_center_text">
          <span>{{ currentDate }}</span>
        </div>
        <el-button type="text" class="el-icon-arrow-right calendar-header_btn" @click="nextDate" />
      </div>
      <div class="calendar-header_right">
        <slot name="header_right" />
        <el-radio-group v-if="showTypeSwitch" v-model="dateType" size="small" @input="changeHandle">
          <el-radio-button v-for="item in radioOptions" :key="item.value" :label="item.value">
            {{ item.label }}
          </el-radio-button>
        </el-radio-group>
        <el-button size="small" class="calendar-header_btn" @click="todayHandle">
          今天
        </el-button>
      </div>
    </div>
    <div class="calendar-content">
      <month-calendar v-if="dateType==='month'" :weeks="weeks" :month-data="monthData"
                      @dblclick="dblclick">
        <template #month="{monthInfo}">
          <slot name="month" :monthInfo="monthInfo" />
        </template>
      </month-calendar>
      <WeekCalendar v-else-if="dateType==='week'" :week-data="weekData"
                    :week-time-data="weekTimeData">
        <template #week="{weekInfo}">
          <slot name="week" :weekInfo="weekInfo" />
        </template>
      </WeekCalendar>
      <day-calendar v-else-if="dateType==='day'" :day-data="dayData">
        <template #day="{dayInfo}">
          <slot name="day" :dayInfo="dayInfo" />
        </template>
      </day-calendar>
    </div>
  </div>
</template>

<script>
import MonthCalendar from './components/MonthCalendar.vue'
import WeekCalendar from './components/WeekCalendar.vue'
import DayCalendar from './components/DayCalendar.vue'
const oneDayTime = 24 * 60 * 60 * 1000
export default ({
  name: 'ICalendar',
  components: {
    MonthCalendar,
    WeekCalendar,
    DayCalendar,
  },
  props: {
    date: {
      type: Date,
      default: () => new Date(),
    },
    value: {
      type: String,
      default: 'month',
    },
    showTypeSwitch: {
      type: Boolean,
      default: false
    },
  },
  data() {
    return {
      nowDate: this.date,
      currentDate: '',
      dateType: this.value,
      radioOptions: [
        { label: '月', value: 'month' },
        { label: '周', value: 'week' },
        { label: '日', value: 'day' },
      ],
      weeks: ['一', '二', '三', '四', '五', '六', '日'],
      weekData: [],
      weekTimeData: [],
      monthData: [],
      dayData: {},
    }
  },
  watch: {
    value: {
      handler(val) {
        this.dateType = val
      },
    },
    dateType: {
      handler(val) {
        this.$emit('input', val)
      },
    },
    date: {
      handler(val) {
        this.nowDate = val
        this.changeHandle()
      },
    },
  },
  mounted() {
    this.changeHandle()
  },
  methods: {
    setCurrentDate(date) {
      this.$emit('fetchDate', date)
      const year = date.getFullYear()
      const month = date.getMonth() + 1
      const day = date.getDate()
      switch (this.dateType) {
        case 'month':
          this.currentDate = `${year}年${month}月`
          break
        case 'week': {
          const newDate = new Date(`${year}-${month}-${day} 00:00:00`)
          const nowTime = newDate.getTime()
          const weekDay = newDate.getDay()

          const mondayTime = (1 - weekDay) * oneDayTime + nowTime
          const sundayTime = (7 - weekDay) * oneDayTime + nowTime

          const mondayYear = new Date(mondayTime).getFullYear()
          const mondayMonth = new Date(mondayTime).getMonth() + 1
          const mondayDay = new Date(mondayTime).getDate() >= 10 ? new Date(mondayTime).getDate() : `0${new Date(mondayTime).getDate()}`
          const sundayYear = new Date(sundayTime).getFullYear()
          const sundayMonth = new Date(sundayTime).getMonth() + 1
          const sundayDay = new Date(sundayTime).getDate() >= 10 ? new Date(sundayTime).getDate() : `0${new Date(sundayTime).getDate()}`

          this.currentDate = `${mondayYear}年${mondayMonth}月${mondayDay}日
          - ${sundayYear}年${sundayMonth}月${sundayDay}日`
          break
        }
        case 'day':
          this.currentDate = `${year}年${month}月${day >= 10 ? day : `0${day}`}日`
          break
        default:
          break
      }
    },
    // 上一个日期
    preDate() {
      let n = this.nowDate
      switch (this.dateType) {
        case 'month': {
          let date = new Date(n.getFullYear(), n.getMonth() - 1, n.getDate(), n.getHours(), n.getMinutes(), n.getSeconds(), n.getMilliseconds())
          this.setDateInfo(date)
          this.generateMonth(date)
          break
        }
        case 'week': {
          let date = new Date(n.getTime() - 7 * oneDayTime)
          this.setDateInfo(date)
          this.generateWeek(date)
          break
        }
        case 'day': {
          let date = new Date(n.getTime() - oneDayTime)
          this.setDateInfo(date)
          this.generateDay(date)
          break
        }
        default:
          break
      }
    },
    // 下一个日期
    nextDate() {
      let n = this.nowDate
      switch (this.dateType) {
        case 'month': {
          let date = new Date(n.getFullYear(), n.getMonth() + 1, n.getDate(), n.getHours(), n.getMinutes(), n.getSeconds(), n.getMilliseconds())
          this.setDateInfo(date)
          this.generateMonth(date)
          break
        }
        case 'week': {
          let date = new Date(n.getTime() + 7 * oneDayTime)
          this.setDateInfo(date)
          this.generateWeek(date)
          break
        }
        case 'day': {
          let date = new Date(n.getTime() + oneDayTime)
          this.setDateInfo(date)
          this.generateDay(date)
          break
        }
        default:
          break
      }
    },
    todayHandle() {
      let date = new Date()
      this.setDateInfo(date)
      switch (this.dateType) {
        case 'month': {
          this.generateMonth(date)
          break
        }
        case 'week': {
          this.generateWeek(date)
          break
        }
        case 'day': {
          this.generateDay(date)
          break
        }
        default:
          break
      }
    },
    setDateInfo(date) {
      this.setCurrentDate(date)
      this.nowDate = date
    },
    changeHandle() {
      this.setCurrentDate(this.nowDate)
      switch (this.dateType) {
        case 'month': {
          this.generateMonth(this.nowDate)
          break
        }
        case 'week': {
          this.generateWeek(this.nowDate)
          break
        }
        case 'day': {
          this.generateDay(this.nowDate)
          break
        }
        default:
          break
      }
    },
    // 生成一月的数据
    generateMonth(date) {
      // 设置当前月的第一天
      const firstDate = new Date(date.setDate(1))
      const weekStart = firstDate.getDay()
      let milsStart = firstDate.getTime()
      // 获取当前月的最后一天
      const lastDate = new Date(date.getFullYear(), date.getMonth() + 1, 0)
      const lastDay = lastDate.getDate()
      const weekEnd = lastDate.getDay()
      const milsEnd = lastDate.getTime() + oneDayTime

      let monthList = []
      let current
      // 补齐上个月
      for (let i = (weekStart ? weekStart - 1 : 6); i > 0; i--) {
        current = new Date(milsStart - i * oneDayTime)
        monthList.push({
          type: -1,
          date: current,
          fullYear: current.getFullYear(),
          month: current.getMonth() + 1,
          day: current.getDate() >= 10 ? current.getDate() : `0${current.getDate()}`,
        })
      }
      // 当前月
      for (let i = 0; i < lastDay; i++) {
        current = new Date(milsStart + i * oneDayTime)
        monthList.push({
          type: 0,
          date: current,
          fullYear: current.getFullYear(),
          month: current.getMonth() + 1,
          day: current.getDate() >= 10 ? current.getDate() : `0${current.getDate()}`,
        })
      }
      // 补齐下个月
      for (let i = 0; i < (weekEnd ? 7 - weekEnd : 0); i++) {
        current = new Date(milsEnd + i * oneDayTime)
        monthList.push({
          type: 1,
          date: current,
          fullYear: current.getFullYear(),
          month: current.getMonth() + 1,
          day: current.getDate() >= 10 ? current.getDate() : `0${current.getDate()}`,
        })
      }
      this.monthData = []
      for (let i in monthList) {
        let index = i % 7
        if (index === 0) {
          this.monthData.push([])
        }
        this.monthData[Math.floor(i / 7)].push(monthList[i])
      }
      // 少于6行，补足6行
      if (this.monthData.length <= 5) {
        milsStart = current.getTime()
        let lastLine = []
        for (let i = 1; i <= 7; i++) {
          current = new Date(milsStart + i * oneDayTime)
          lastLine.push({
            type: 1,
            date: current,
            fullYear: current.getFullYear(),
            month: current.getMonth() + 1,
            day: current.getDate() >= 10 ? current.getDate() : `0${current.getDate()}`,
          })
        }
        this.monthData.push(lastLine)
      }
    },
    // 生成一周的数据
    generateWeek(date) {
      const year = date.getFullYear()
      const month = date.getMonth() + 1
      const day = date.getDate()
      const newDate = new Date(`${year}-${month}-${day} 00:00:00`)
      const nowTime = newDate.getTime()
      const weekDay = newDate.getDay()

      this.weekData = []
      for (let i = 1; i < 8; i++) {
        let current = new Date((i - weekDay) * oneDayTime + nowTime)
        this.weekData.push({
          date: current,
          week: `周${this.weeks[current.getDay() > 0 ? current.getDay() - 1 : 6]}`,
          month: current.getMonth() + 1,
          day: current.getDate() >= 10 ? current.getDate() : `0${current.getDate()}`,
        })
      }

      let weekTimeList = []
      // 全天对应星期
      for (let i in this.weekData) {
        weekTimeList.push({
          time: '全天',
          data: this.weekData[i],
        })
      }
      // 每小时对应星期
      for (let i = 0; i < 25; i++) {
        for (let w in this.weekData) {
          weekTimeList.push({
            time: i,
            data: this.weekData[w],
          })
        }
      }
      this.weekTimeData = []
      for (let i in weekTimeList) {
        let index = i % 7
        if (index === 0) {
          this.weekTimeData.push([])
        }
        this.weekTimeData[Math.floor(i / 7)].push(weekTimeList[i])
      }
    },
    // 生成一天的数据
    generateDay(date) {
      this.dayData = {
        date,
        fullYear: date.getFullYear(),
        month: date.getMonth() + 1,
        day: date.getDate() >= 10 ? date.getDate() : `0${date.getDate()}`,
      }
    },
    dblclick(day) {
      this.$emit('dblclick', day)
    },
  },
})
</script>

<style lang="scss" scoped>
.calendar-wrapper {
  width: 100%;
  height: 100%;
  .calendar-header {
    height: 30px;
    line-height: 30px;
    padding: 5px;
    position: relative;
    &_left {
      position: absolute;
      left: 5px;
      top: 50%;
      transform: translateY(-50%);
    }
    &_center {
      position: absolute;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
      display: flex;
      align-items: center;
      font-size: 16px;

      &_text {
        color: #333;
        font-weight: bold;
        margin: 0 10px;
      }
    }
    &_right {
      position: absolute;
      right: 5px;
      top: 50%;
      transform: translateY(-50%);
      display: flex;
      align-items: center;
      .calendar-header_btn {
        margin-left: 14px;
      }
    }
  }
}
</style>