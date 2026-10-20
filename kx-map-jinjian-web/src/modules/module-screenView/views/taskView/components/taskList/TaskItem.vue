<template>
  <div class="task-Item">
    <div class="main">
      <span  :class='`task-title ${open?"open":""}`' @click="itemClick"  :style='`width:${hd[0].width}%`' :title="taskInfo.name">
        {{ taskInfo.name }}
      </span>
      <span  :class='`task-status status${taskInfo.status}`'
      :style='`width:${hd[1].width}%;color:${STATUS[taskInfo.status].color}`'
      @click="checkDetail(taskInfo.id)"
      v-html="STATUS[taskInfo.status].name"></span>
      <span class="special-num" :style='`width:${hd[2].width}%`'>
        {{ taskInfo.rata }}
      </span>
      <span :style='`width:${hd[3].width}%`' class="task-name">
        {{ taskInfo.dept }}
      </span>
      <div :style='`width:${hd[4].width}%`' class="task-btn">
        <span class="btn-view" @click="indicatorView">指标视图</span>
        <span @click="$emit('view','main')">轨迹查看</span>
      </div>
    </div>
    <ul class="sub" v-show="open">
      <li v-for="(i,index) in taskInfo.sonList" :key="index">
        <span class="task-name" :style='`width:${hd[0].width}%`'>{{i.name}}</span>
        <span
        @click="checkSonDetail(i.id)" 
        :class='`task-status status${i.status}`' 
        v-html="STATUS[i.status].name" 
        :style='`width:${hd[1].width}%;color:${STATUS[i.status].color}`'
        > </span>
        <span class="task-name" :style='`width:${hd[2].width}%`'>{{i.manage}}</span>
        <span class="task-name" :style='`width:${hd[3].width}%`'>{{i.dept}}</span>
        <div  class="task-btn" :style='`width:${hd[4].width}%`'>
          <span @click="$emit('view',i)">轨迹查看</span>
        </div>
      </li>
    </ul>
  </div>
</template>
<script>
const STATUS = [
  {name:'未开始',color:"#ccc"},
  {name:'进行中',color:"#32ECEE"},
  {name:'已结束',color:"#2AFE84"},
  {name:'手动<br/>结束',color:"#E95908"},
]
export default {
  name: "TaskItem",
  props:{
    hd:Array,
    taskInfo:Object,
  },
  data(){
    return {
      open:false,
      STATUS,
    }
  },
  computed:{

  },
  methods:{
    itemClick(){
      this.open = !this.open
      if(!this.taskInfo.sonList){
        this.$emit('getTaskSon')
      }
    },
    // 查看主任务详情
    checkDetail(id) {
      this.$emit('checkDetail', id)
    },
    // 子任务详情
    checkSonDetail(id) {
      this.$emit('checkSonDetail', id)
    },
    // 指标视图
    indicatorView() {
      let e = {
        A: '11'
      }
      this.$emit('indicatorView', e)
    }
  }
};
</script>
<style lang="scss" scoped>
.task-Item{
  margin-top: 10px;
  position: relative;
  .main{
    width: 100%;
    height: 62px;
    background: rgba(150,252,253,0.1);
    display: flex;
    justify-content: center;
    align-items: center; 
    font-size: 16px;
    font-family: Noto Sans S Chinese;
    font-weight: 500;
    color: #FFFFFF;
    text-align: center;
    border: 1px solid rgba(150,252,253,0.1);
    >span{
      display: inline-block;
    }
    &:hover{
      background: rgba(150,252,253,0.2);
      border: 1px solid #15E1E7;
    }
    .special-num{
      font-family: screenSpecial;
    }
  }
  .sub{
    border: 1px solid #1F3A4790;
    >li{
      border-bottom: 1px solid #FFFFFF20;
      display: flex;
      justify-content: center;
      align-items: center; 
      height: 38px;
      font-size: 16px;
      font-family: Noto Sans S Chinese;
      font-weight: 500;
      color: #FFFFFF;
      text-align: center;
    }
  }
  .task-title{
      background: url('./img/close.png') 8px center no-repeat;
      padding-left: 28px;
      cursor: pointer;
      overflow: hidden;//溢出隐藏
      white-space: nowrap;//禁止换行
      text-overflow: ellipsis;//...
      &.open{
        background: url('./img/open.png') 8px center no-repeat;
      }
    }
    .task-status{
      cursor: pointer;
      position: relative;
      padding-left: 5px;
      &::before{
        content: "";
        position: absolute;
        width: 8px;
        height: 8px;
        border-radius: 8px;
        left: 2px;
        top:10px;
      }
      &.status0::before{
        background: #cccccc;
        box-shadow:  0px 0px 3px 1px #cccccc, 0px 0px 5px 2px #cccccc80;
        top:5px;
      }
      &.status1::before{
        background: #32ECEE;
        box-shadow:  0px 0px 3px 1px #32ECEE, 0px 0px 5px 2px #32ECEE80;
        top:5px;
      }
      &.status2::before{
        background: #2AFE84;
        box-shadow:  0px 0px 3px 1px #2AFE84, 0px 0px 5px 2px #2AFE8480;
        top:5px;
      }
      &.status3::before{
        background: #E95908;
        box-shadow:  0px 0px 3px 1px #E95908, 0px 0px 5px 2px #E9590880;
      }
    }
    .task-name{
      overflow: hidden;//溢出隐藏
      white-space: nowrap;//禁止换行
      text-overflow: ellipsis;//...
    }
    .task-btn{
      >span{
        display: inline-block;
        border-radius: 6px;
        font-size: 12px;
        background: #00F6FF40;
        padding: 3px 5px;
        cursor: pointer;
      }
      .btn-view{
        border: 1px solid #32ECEE30;
        background:none;
        margin-bottom: 5px;
      }
    }
}
</style>
