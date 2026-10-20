<template>
  <div class="bar-box">
    <div ref="echart" class="echarts"></div>
  </div>
</template>
<script>
import * as echarts from "echarts";
export default {
  props: {
    info: {
      type: Object,
      default: () => {
        return {
          title: "红牌商户",
          xData: [
            "24年9月",
            "24年10月",
            "24年11月",
            "24年12月",
            "25年1月",
            "25年2月",
          ],
          yData: [20000, 16000, 5083, 7892, 3897, 23000],
          // 折线图默认数据和标题
          lineTitle: "趋势",
          lineData: [15000, 14000, 7000, 9000, 5000, 20000],
        };
      },
    },
  },
  data () {
    return {
      option: {},
    };
  },
  mounted () {
    this.initEchart();
  },
  methods: {
    initEchart () {
      const myChart = echarts.init(this.$refs.echart);
      var barWidth = 10;
      this.option = {
        legend: {
          show: true,
          top: 0,
          textStyle: {
            color: "#fff",
          },
          data: [this.info.title, this.info.lineTitle],
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
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
              color: "rgba(230, 247, 255, 0.2)",
            },
          },
        },
        xAxis: {
          type: "category",
          offset: 0,
          axisLine: {
            show: true,
            lineStyle: {
              color: "#fff",
            },
            textStyle: {
              fontSize: 11,
              color: "#fff",
            },
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
            name: this.info.title,
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
            data: this.info.yData,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: "#0EECE4" },
                { offset: 1, color: "#058FE7" },
              ]),
            },
          },
          {
            name: this.info.lineTitle,
            type: "line",
            smooth: true,
            // 提高 z 值确保绘制在柱子之上
            z: 10,
            // 临时显示拐点便于调试，可改回 false
            showSymbol: true,
            symbol: "none",
            symbolSize: 6,
            lineStyle: {
              width: 2,
              color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                { offset: 0, color: "#FFB86C" },
                { offset: 1, color: "#FF6B6B" },
              ]),
            },
            areaStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: "rgba(255,107,107,0.25)" },
                { offset: 1, color: "rgba(255,184,108,0.05)" },
              ]),
            },
            data: this.info.lineData,
          },
        ],
        // 调整grid布局，使两个系列的柱子能够紧密排列在一起
        grid: {
          left: "3%",
          right: "4%",
          top: "14%",
          bottom: "3%",
          containLabel: true,
        },
      };
      myChart.setOption(this.option);
    },
  },
  watch: {
    info (newVal) {
      this.initEchart();
    },
  },
};
</script>

<style scoped lang="scss">
.bar-box {
  width: 430px;
  height: 240px;
  display: flex;
  align-items: center;

  .echarts {
    width: 100%;
    height: 100%;
  }
}
</style>
