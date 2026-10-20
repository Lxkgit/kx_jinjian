<template>
  <div class="bar-box">
    <div ref="echart" class="echarts"></div>
  </div>
</template>
<script>
import * as echarts from 'echarts';
export default {
  props: {
    info: {
      type: Object,
      default: () => {
        return {
          legendData: ['红牌商户', '黄牌商户'],
          xData: ['杭州', '宁波', '温州', '绍兴', '湖州', '嘉兴', '金华', '衢州', '舟山', '台州', '丽水'],
          yData1: [400, 200, 200, 100, 200, 300, 444, 400, 600, 700, 150],
          yData2: [400, 500, 240, 300, 130, 340, 440, 500, 600, 700, 120],
        }
      }
    }
  },
  data() {
    return {
      option: {}
    }
  },
  mounted() {
    this.initEchart();
  },
  methods: {
    initEchart() {
      const myChart = echarts.init(this.$refs.echart);
      var barWidth = 10;
      this.option = {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
        },
        legend: {
          data: this.info.legendData,
          top: "-1%",
          right: "5%",
          textStyle: {
            color: "#fff",
          },
        },
        yAxis: {
          type: "value",
          axisLine: {
            show: false,
            lineStyle: {
              color: "#fff",
            },
          },
          axisTick: {
            show: false,
          },
          splitLine: {
            //修改背景线条样式
            show: true,
            lineStyle: {
              type: "dashed",
              color: "rgba(230, 247, 255, 0.2)"
            }
          },

        },
        xAxis: {
          type: "category",
          offset: 0,
          axisLine: {
            show: true,
            lineStyle: {
              color: "#fff",
            }
          },
          data: this.info.xData,
          axisTick: {
            show: false,
          },
          splitLine: {
            //修改背景线条样式
            show: false,
          },

        },
        series: [
          {
            name: this.info.legendData[0],
            type: "bar",
            barWidth: barWidth,
            // showBackground: true,
            // backgroundStyle: {
            //   color: "background: rgba(255, 255, 255);",
            // },
            // label: {
            //   show: false,
            //   position: "insideRight",
            // },
            // emphasis: {
            //   focus: "series",
            // },
            data: this.info.yData1,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: 'rgba(255, 41, 41, 1)' },
                { offset: 1, color: 'rgba(231, 30, 107, 0.35)' }
              ]),
            },
          },
          {
            name: this.info.legendData[1],
            type: "bar",
            stack: "总量",
            barWidth: barWidth,

            label: {
              show: false,
              position: "insideLeft",
              color: "#fff", // 使标签颜色与柱子背景色对比明显

            },

            // emphasis: {
            //   focus: "series",
            // },
            data: this.info.yData2, // 取相反数，但视觉上表示向左
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: 'rgba(255, 233, 146, 1)' },
                { offset: 1, color: 'rgba(255, 143, 24, 0.35)' }
              ])
            },
          },
        ],
        // 调整grid布局，使两个系列的柱子能够紧密排列在一起
        grid: {
          left: "3%",
          right: "4%",
          top: "8%",
          bottom: "5%",
          containLabel: true,
        },
      };
      myChart.setOption(this.option);
    }
  },
  watch: {
    info(newVal) {
      this.initEchart();
    },
    deep: true,
  }
};
</script>

<style scoped lang="scss">
.bar-box {
  width: 430px;
  height: 300px;
  display: flex;
  align-items: center;

  .echarts {
    width: 100%;
    height: 100%;

  }
}
</style>