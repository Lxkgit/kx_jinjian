<template>
  <div class="week-card-wrapper">
    <span class="current-month">{{ currentMonth }}</span>
    <div class="week-calendar">
      <div class="week-item" v-for="(week,index) in weekData" :key="index"
           :class="{lastDay: week.weekNum<todayWeek, currentDay: week.weekNum==currentWeek}"
           @click="clickHandle(week)">
        <span class="item-week">{{ week.week }}</span>
        <span class="item-week">{{ week.day }}</span>
        <span :class="`${signList[index]?'item-sign':'item-normal'}`"></span>
      </div>
    </div>
  </div>
</template>

<script>
const oneDayTime = 24 * 60 * 60 * 1000;
export default ({
  props: {
    signList: {
      type: Array,
    },
  },
  data() {
    return {
      weeks: ["一", "二", "三", "四", "五", "六", "日"],
      weekData: [],
      ifshow: false,
      currentWeek: this.value,
    }
  },
  computed: {
    currentMonth() {
      const now = new Date()
      const year = now.getFullYear()
      const month = now.getMonth() + 1
      return `${year}年${month < 10 ? `0${month}` : month}月`
    },
    todayWeek() {
      const current = new Date()
      return current.getDay() > 0 ? current.getDay() - 1 : 6
    },
  },
  watch: {
  },
  mounted() {
    this.generateWeek()
    const current = new Date()
    this.currentWeek = current.getDay() > 0 ? current.getDay() - 1 : 6
  },
  methods: {
    generateWeek() {
      const newDate = new Date();

      const nowTime = newDate.getTime();
      const weekDay = newDate.getDay() > 0 ? newDate.getDay() : 7;

      this.weekData = []
      for (let i = 1; i < 8; i++) {
        let current = new Date((i - weekDay) * oneDayTime + nowTime);
        this.weekData.push({
          date: current,
          weekNum: current.getDay() > 0 ? current.getDay() - 1 : 6,
          week: `周${this.weeks[current.getDay() > 0 ? current.getDay() - 1 : 6]
            }`,
          month: current.getMonth() + 1,
          day:
            current.getDate() >= 10
              ? current.getDate()
              : `0${current.getDate()}`,
        });
      }
      this.$parent.weekSchedule(this.weekData, new Date())

    },
    clickHandle(week) {
      this.currentWeek = week.weekNum
      this.$parent.weekSchedule(this.weekData, week.date)
    },
  },
})
</script>

<style lang="scss" scoped>
.week-card-wrapper {
  width: 100%;
  padding: 25px 20px;
  background: #f6f6f6;
  border-radius: 10px;
  .current-month {
    color: #5e81f4;
    font-size: 16px;
    font-weight: bold;
    margin-bottom: 25px;
    display: block;
  }
  .week-calendar {
    display: flex;
    justify-content: space-between;
    .week-item {
      cursor: pointer;
      display: flex;
      flex-direction: column;
      align-items: center;
      font-size: 16px;
      padding: 15px 4px;
      text-align: center;
      &.lastDay {
        opacity: 0.5;
      }
      &.currentDay {
        background: #5e81f4;
        border-radius: 20px;
        .item-week {
          color: #fff;
        }
        .item-sign {
          background: #fff;
        }
      }
      .item-week {
        margin-bottom: 20px;
      }
      .item-sign {
        display: block;
        width: 10px;
        height: 10px;
        background: #f35e5e;
        border-radius: 50%;
      }
      .item-normal {
        display: block;
        width: 10px;
        height: 10px;
        border-radius: 50%;
      }
    }
  }
}
</style>