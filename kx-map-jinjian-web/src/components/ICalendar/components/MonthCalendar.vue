<template>
  <div class="month-calendar-wrapper">
    <div class="month-calendar_week">
      <span v-for="week in weeks" :key="week">周{{ week }}</span>
    </div>
    <div class="month-calendar_days">
      <div v-for="(aWeek,index) in monthData" :key="index" class="month-calendar_day_line">
        <div v-for="day in aWeek" :key="day.month+day.day" :style="{color: getCellColor(day)}"
             @dblclick="dblclick(day)" class="month-calendar_day_cell">
          <span>{{ day.day }}</span>
          <slot name="month" :monthInfo="day" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default ({
  props: {
    weeks: {
      type: Array,
      default: () => [],
    },
    monthData: {
      type: Array,
      default: () => [],
    },
  },
  methods: {
    getCellColor(day) {
      let color = day.type == -1 ? '#c0c4cc' : (day.type == 1 ? '#c0c4cc  ' : '')
      return color
    },
    dblclick(day) {
      this.$emit('dblclick', day)
    },
  },
})
</script>

<style lang="scss" scoped>
.month-calendar-wrapper {
  width: 100%;
  box-sizing: border-box;
  padding: 10px;
  .month-calendar_week {
    display: grid;
    grid-template-columns: repeat(7, 1fr);
    margin-bottom: 10px;
    gap: 10px;
    > span {
      font-size: 16px;
      font-weight: bold;
    }
  }
  .month-calendar_days {
    .month-calendar_day_line {
      display: grid;
      grid-template-columns: repeat(7, 1fr);
      gap: 10px;
      .month-calendar_day_cell {
        padding: 10px 5px;
        border-top: 1px solid #e9e9e9;
        min-height: 100px;
        &:hover {
          color: #409eff;
        }
        > span {
          font-size: 16px;
        }
      }
    }
  }
}
</style>