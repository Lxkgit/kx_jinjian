<template>
  <div class="week-calendar-wrapper">
    <div class="week-calendar_week">
      <span>时间</span>
      <span
        v-for="(week,index) in weekData"
        :key="index">
        {{ week.week }} {{ week.month }}/{{ week.day }}
      </span>
    </div>
    <div class="week-calendar_times">
      <div
        v-for="(times,index) in weekTimeData"
        :key="index"
        class="week-calendar_time_line">
        <div class="week-calendar_time_first">
          <span>
            {{ times[0].time }}
          </span>
        </div>
        <div
          v-for="(week,i) in times"
          :key="week.time+i">
          <slot
            name="week"
            :weekInfo="week" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default ({
  props: {
    weekData: {
      type: Array,
      default: () => [],
    },
    weekTimeData: {
      type: Array,
      default: () => [],
    },
  },
})
</script>

<style lang="scss" scoped>
.week-calendar-wrapper{
  width: 100%;
  box-sizing: border-box;
  padding: 10px;
  .week-calendar_week{
    display: grid;
    grid-template-columns: 1fr repeat(7,3fr);
    text-align: center;
    >span {
      background: #F5F5F5;
      line-height: 40px;
      border: 1px solid #E9E9E9;
      display: inline-block;
      font-weight: bold;
      &:not(:first-child){
        border-left: none;
      }
    }
  }
  .week-calendar_times{
    .week-calendar_time_line{
      display: grid;
      grid-template-columns: 1fr repeat(7,3fr);
      text-align: center;
      >div{
        min-height: 80px;
        border: 1px solid #E9E9E9;
        border-top: none;
        
        &:not(:first-child){
          border-left: none;
        }
        >span{
          position: relative;
          top: calc(50% - 7px) ;
          
        }
      }
      .week-calendar_time_first{
        background: #F5F5F5;
      }
    }
  }
}
</style>