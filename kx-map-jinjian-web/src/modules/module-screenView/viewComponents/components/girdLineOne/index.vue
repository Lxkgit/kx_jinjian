<template>
  <div class="grid-box">
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
          xData: ['杭州', '宁波', '温州', '绍兴', '湖州', '嘉兴', '金华', '衢州', '舟山', '台州', '丽水'],
          yDate: [200, 444, 500, 1000, 290, 300, 400, 600, 700, 300, 740],
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
      const xData = this.info.xData
      this.option = {
        tooltip: {
          trigger: 'axis',
        },
        legend: {
          data: ['投诉事件数量'],
          icon: 'roundRect',
          itemHeight: 3,
          itemStyle: {
            color: '#FF0011'
          },
          top: "-1%",
          right: "5%",
          textStyle: {
            color: "#fff",
          },
        },
        grid: {
          left: '3%',
          right: '4%',
          top: '5%',
          bottom: '5%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: xData,
            axisLine: {
              show: false,
              lineStyle: {
                color: "#fff",
              },
            },
            axisTick: {
              show: false,
              alignWithLabel: true
            },
            splitLine: {
              //修改背景线条样式
              show: true,
              lineStyle: {
                type: "dashed",
                color: "rgba(230, 247, 255, 0.1)"
              },
              textStyle: {
                fontSize: 10
              }
            },
          }
        ],
        yAxis: [
          {
            type: 'value',
            splitLine: {
              //修改背景线条样式
              show: true,
              lineStyle: {
                type: "dashed",
                color: "rgba(230, 247, 255, 0.1)"
              }
            },
            axisLine: {
              show: false,
              lineStyle: {
                color: "#fff",
              },
            },

          }
        ],
        series: [
          {
            name: '投诉事件数量',
            type: 'line',
            stack: 'Total',
            smooth: true,
            lineStyle: {
              width: 1,
              color: 'rgba(255, 0, 17, 1)'
            },
            showSymbol: false,
            areaStyle: {
              opacity: 1,
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: 'rgba(255, 0, 17, 0.4)'
                },
                {
                  offset: 1,
                  color: 'rgba(255, 0, 17, 0)'
                }
              ])
            },

            data: this.info.yDate
          },
        ]
      };
      myChart.setOption(this.option);
    }
  },
  watch: {
    info(newVal) {
      this.initEchart();
    }
  }
};
</script>

<style scoped lang="scss">
.grid-box {
  width: 430px;
  height: 280px;
  display: flex;
  align-items: center;

  .echarts {
    width: 100%;
    height: 100%;
  }
}
</style>