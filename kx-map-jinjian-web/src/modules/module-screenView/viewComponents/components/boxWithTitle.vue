<template>
  <div class="box-content">
    <img src="../assets/activity.png" class="img-top">
    <div class="box-title">{{title}}</div>
    <info-box class="box-main" :style="{'height':`${height}`}">
      <slot></slot>
    </info-box>
  </div>
</template>

<script>
import infoBox from "./infoBox.vue";
export default {
  name: "activityDetail",
  components: { infoBox },
  props: {
   title:String,
   height:{
    type:String,
    default:"350px"
   }
  },
};
</script>

<style scoped lang="scss">
.box-content{
  padding-top: 60px;
  position: relative;
  .img-top{
    position: absolute;
    top:21px;
    left: -10px;
    width: 83px;
    height: 64px;
  }
  .box-title{
    color: #8BF6FF;
    font-size: 18px;
    position: absolute;
    left: 80px;
    top:20px;
    line-height: 40px;
  }
}
.box-main {
  // display: flex;
  // flex-direction: column;
  // justify-content: space-around;
  // align-items: center;
  padding: 20px;
}
</style>