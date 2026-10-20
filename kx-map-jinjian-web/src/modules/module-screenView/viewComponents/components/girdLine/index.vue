<template>
  <div class="grid-box">
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
          legendData: ["责任网点"],
          xData: [
            "杭州",
            "宁波",
            "温州",
            "绍兴",
            "湖州",
            "嘉兴",
            "金华",
            "衢州",
            "舟山",
            "台州",
            "丽水",
          ],
          data: [444, 500, 1000, 290, 300, 400, 600, 700, 300, 740, 200],
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
      const xData = this.info.xData;
      this.option = {
        visualMap: [
          {
            show: false,
            min: 0,
            max: xData.length - 1,
            type: "continuous",
            seriesIndex: 0,
            inRange: {
              color: [
                "rgba(132, 167, 255, 0.1)",
                "rgba(2, 198, 255, 1)",
                "rgba(3, 104, 255, 1)",
                "rgba(87, 131, 246, 7)",
              ],
            },
          },
          {
            show: false,
            min: 0,
            max: xData.length - 1,
            type: "continuous",
            seriesIndex: 1,
            inRange: {
              color: [
                "rgba(132, 255, 220, 1)",
                "rgba(2, 255, 255, 1)",
                "rgba(3, 255, 133, 1)",
                "rgba(87, 246, 206, 1)",
              ],
            },
          },
        ],
        tooltip: {
          trigger: "axis",
        },
        color: ['rgba(0, 255, 229, 1)'],
        legend: {
          data: this.info.legendData,
          icon: "roundRect",
          top: "5%",
          right: "5%",
          itemHeight: 4,
          textStyle: {
            color: "#fff",
          },
        },
        grid: {
          left: "3%",
          right: "4%",
          top: "15%",
          bottom: "5%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            boundaryGap: false,
            data: xData,
            axisLine: {
              show: true,
              lineStyle: {
                color: "#fff",
              },
            },
            axisTick: {
              show: false,
              alignWithLabel: true,
            },
            splitLine: {
              //修改背景线条样式
              show: true,
              lineStyle: {
                type: "dashed",
                color: "rgba(230, 247, 255, 0.1)",
              },
            },
          },
        ],
        yAxis: [
          {
            type: "value",
            splitLine: {
              //修改背景线条样式
              show: true,
              lineStyle: {
                type: "dashed",
                color: "rgba(230, 247, 255, 0.1)",
              },
            },
            axisLine: {
              show: false,
              lineStyle: {
                color: "#fff",
              },
            },
          },
        ],
        series: [
          {
            name: this.info.legendData[0],
            type: "line",
            stack: "Total",
            smooth: true,
            lineStyle: {
              width: 1,
              color: 'rgba(0, 255, 229, 1)'
            },
            showSymbol: false,
            areaStyle: {
              opacity: 0.8,
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: "rgba(0, 255, 229, 0.4)",
                },
                {
                  offset: 1,
                  color: "rgba(0, 255, 191, 0)",
                },
              ]),
            },

            data: this.info.data,
          },
        ],
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
.grid-box {
  width: 430px;
  height: 160px;
  display: flex;
  align-items: center;

  .echarts {
    width: 100%;
    height: 100%;
  }
}
</style>
