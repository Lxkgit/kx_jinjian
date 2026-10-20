<template>
  <div class="date-display-wrapper">
    <span class="day-week-wrapper">
      {{ getDay(dateTime) }} {{ getWeek(dateTime) }}
    </span>
    <span class="time-wrapper">
      {{ getTime(dateTime) }}
    </span>
    <span class="title-wrapper">{{ title }}</span>
  </div>
</template>

<script>
export default ({
  props: {
    dateTime: {
      type: [Object, String]
    },
    title: {
      type: String
    }
  },
  methods: {
    getDay(time) {
      return this.parseTime(time, "{m}月{d}日")
    },
    getTime(time) {
      return this.parseTime(time, "{h}:{i}")
    },
    getWeek(time) {
      return this.parseTime(time, "周{a}")
    }
  },
})
</script>

<style lang="scss" scoped>
.date-display-wrapper {
  display: flex;
  flex-direction: column;
  align-items: center;
  .day-week-wrapper {
    font-size: 16px;
    color: #539fff;
  }
  .time-wrapper {
    font-size: 28px;
    line-height: 2;
    font-weight: bold;
    color: #539fff;
  }
  .title-wrapper {
    padding: 4px 8px;
    display: block;
    border-radius: 8px;
    background: #f2f2f2;
    color: #b4b4b4;
  }
}
</style>