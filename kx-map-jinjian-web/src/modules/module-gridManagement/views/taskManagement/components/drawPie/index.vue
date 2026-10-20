<template>
  <div class="card" :style="`width: ${width}; height: ${height}`">
    <!-- 顶部的标题与数据 -->
    <div class="top" v-if="hadTop">
      <div class="topTitle">{{ topText }}</div>
      <div class="topNum" :style="`color:${middleFontColor}`">{{ topNum }}%</div>
    </div>
    <!-- 中间的圆环 -->
    <div
      class="middle"
      :style="`width: ${circleSize}px; height: ${circleSize}px`"
    >
      <div id="myChart" ref="echarts" style="width: 100%; height: 100%"></div>
    </div>
    <!-- 底部的副标题与数据 -->
    <div class="bottom" v-if="hadBootom">
      <div class="boTitle">{{ bottomText }}</div>
      <div class="boNum">{{ bottomNum }}</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "drawPie",
  props: {
    // 顶部是否存在
    hadTop: {
      type: Boolean,
      default: true,
    },
    // 主标题
    topText: {
      type: String,
      default: () => "顶部标题",
    },
    // 主标题对应的数据
    topNum: {
      type: [String,Number],
      default: () => "100%",
    },
    // 顶部是否存在
    hadBootom: {
      type: Boolean,
      default: true,
    },
    // 副标题
    bottomText: {
      type: String,
      default: () => "底部标题",
    },
    // 副标题对应的任务数量
    bottomNum: {
      type: [String,Number],
      default: () => 100,
    },
    // 传入的完成度
    num: {
      type: Number,
      default: () => 66.6,
    },
    // 整体卡片的大小
    width: {
      type: String,
      default: "240px",
    },
    height: {
      type: String,
      default: "240px",
    },
    // 圆环的大小
    circleSize: {
      type: Number,
      default: () => 120,
    },
    // 圆环中心的文字大小
    middleFontSize: {
      type: Number,
      default: () => 20,
    },
    // 圆环中心文字的颜色
    middleFontColor: {
      type: String,
      default: () => "#7291FA",
    },
  },
  data() {
    return {};
  },
  watch:{
    num:{
      handler(val){
        if(val){
          this.drawPie(val)
        }
      },
      immediate:true
    }
  },
  methods: {
    drawPie(num) {
      // 当在此处的使用dom的id进行绑定时，使用多次组件只会渲染出来一次，会出现覆盖的情况
      // 解决方法：通过ref获取到组件，或者给echart根据传入的num绑定动态的id
      this.myChart = this.$echarts.init(this.$refs.echarts);
      this.myChart.setOption({
        title: {
          text: num ? num + '%':'0%',
          left: "center",
          top: "center",
          textStyle: {
            // 主标题样式
            color: this.middleFontColor,
            fontWeight: "bold",
            fontSize: this.middleFontSize,
          },
        },
        series: [
          {
            label: {
              show: false,
            },
            type: "pie",
            // 颜色
            color: ["#7793EF", "#fff"],
            // 数据
            data: [
              {
                value: num,
              },
              {
                value: 100 - num,
              },
            ],
            // 圆的半径
            radius: ["78%", "95%"],
          },
        ],
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.card {
  border-radius: 5px;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  flex-direction: column;
  background-color: #f6f7ff;
  .top {
    width: 100%;
    display: flex;
    justify-content: space-evenly;
    align-items: center;

    .topTitle {
      font-size: 16px;
      font-weight: 400;
    }
    .topNum {
      font-size: 24px;
      font-weight: bold;
    }
  }
  .bottom {
    width: 100%;
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    .boTitle {
      font-size: 16px;
    }
    .boNum {
      font-size: 20px;
      font-weight: bold;
    }
  }
}
</style>
