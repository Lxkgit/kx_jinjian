<template>
  <div class="screen-left" :class="{'screen-left-show': leftShow}">
    <div class="repeat-down" />
    <div class="main-wrapper">
      <titleBox height="645px" title="任务概况">
         <task-rate
          :imgType="1"
          id="task-rate1" ></task-rate>

        <task-rate
          id="task-rate2"
          :imgType="2"
          :textNameValue="taskRate2" 
        ></task-rate>

        <task-bar class="p-10" width="100%" height="280px"></task-bar>

      </titleBox>


      <titleBox height="310px" title="任务排名">
        <task-ranking  class="p-10"></task-ranking>
      </titleBox>


      
    </div>
    <div class="operate-wrapper">
      <searchPart></searchPart>

    </div>
    <div class="bottom-wrapper">
      <legendPart></legendPart>
    </div>
  </div>
</template>

<script>
import titleBox from '@/modules/module-screenView/components/titleBox'
import searchPart from '../components/searchPart';
import legendPart from '@/modules/module-screenView/components/legendPart.vue';
import taskRate from "../components/taskRate";
import taskBar from "../components/taskBar";
import taskRanking from "../components/taskRanking";

export default ({
  components: {
    titleBox,
    searchPart,
    legendPart,
    taskRate,
    taskBar,
    taskRanking
  },
  props: {
    leftShow: Boolean,
  },
  data() {
    return {
      taskRate2: [
        {
          name: "子任务完成率",
          value: 43.6,
        },
        {
          name: "子任务总量",
          value: "245",
        },
      ], // 任务完成率
    }
  },
  mounted() {
  },
  methods: {
  
  },
})
</script>

<style lang="scss" scoped>
.screen-left {
  position: absolute;
  width: 960px;
  height: 1009px;
  top: 71px;
  left: 0px;
  transition: all 0.3s linear;
  background: url("~@/modules/module-screenView/assets/image/bg-left.png"),
    url("~@/modules/module-screenView/assets/image/repeat-up.png") 490px top
      no-repeat;
  background-size: 100% 100%, 0px 89px;
  pointer-events: none;
  &.screen-left-show {
    left: -440px;
    background-size: 100% 100%, 440px 89px;
    .repeat-down {
      width: 440px;
      right: -440px;
    }
  }
  .repeat-down {
    width: 0px;
    height: 24px;
    position: absolute;
    bottom: 0px;
    right: 0px;
    background: url("~@/modules/module-screenView/assets/image/repeat-down.png")
      repeat;
    transition: all 0.3s linear;
  }
  .main-wrapper {
    width: 417px;
    height: 920px;
    position: absolute;
    top: 10px;
    left: 20px;
    pointer-events: auto;
    .p-10{
      padding: 10px;
    }

  }
  .operate-wrapper {
    position: absolute;
    left: 490px;
    top: 40px;
    z-index: 999;
    pointer-events: auto;
  }
}
</style>