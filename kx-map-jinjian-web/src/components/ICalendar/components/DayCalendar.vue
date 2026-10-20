<template>
  <div class="day-calendar-wrapper">
    <slot
      name="day"
      :dayInfo="dayData" />
  </div>
</template>

<script>
export default ({
  props: {
    dayData: {
      type: Object,
      default: () => ({}),
    },
  },
})
</script>

<style lang="scss" scoped>
.day-calendar-wrapper{
  width: 100%;
  box-sizing: border-box;
  padding: 10px;
}
</style>