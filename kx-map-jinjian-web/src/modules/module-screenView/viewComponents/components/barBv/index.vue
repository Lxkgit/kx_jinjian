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
          legendData: ['重点网络', '重点用现用户'],
          yData: ['杭州', '宁波', '温州', '绍兴', '湖州', '嘉兴', '金华', '衢州', '舟山', '台州', '丽水'],
          leftData: [40000, 30000, 20000, 13000, 10000, 30000, 44400, 40000, 60000, 7000, 12000],
          rightData: [40000, 30000, 20000, 13000, 10000, 30000, 44400, 40000, 60000, 7000, 12000],
        }
      }
    }
  },
  data() {
    return {
      option: {},
      leftData: [],
      rightData: [],
      max: 0
    }
  },
  mounted() {
    this.initEchart();
  },
  methods: {
    initEchart() {
      const myChart = echarts.init(this.$refs.echart);

      this.max = Math.max(...this.info.leftData, ...this.info.rightData);
      this.info.leftData.forEach(item => {
        this.leftData.push(item * -1);
      });
      this.info.rightData.forEach(item => {
        this.rightData.push(item);
      });
      var barWidth = 14;
      this.option = {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
          formatter: function (params) {
            var tooltipHtml = '';
            params.forEach(function (item) {
              var value = item.value;
              if (value < 0) {
                value = -value;
              }
              tooltipHtml += item.seriesName + ': ' + value + '<br/>';
            });
            return tooltipHtml;
          }
        },
        legend: {
          data: this.info.legendData,
          top: "5%",
          right: "5%",
          textStyle: {
            color: "#fff",
          },
        },
        yAxis: {
          type: "value",
          max: this.max,
          min: - this.max,
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


          axisLabel: {
            formatter: function (value) {
              if (value < 0) {
                return -value;
              } else {
                return value;
              }
            },
          },

        },
        xAxis: {
          type: "category",
          // position: "left",
          offset: 0,
          axisLine: {
            show: true,
            lineStyle: {
              color: "#fff",
            }
          },
          data: this.info.yData,
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
            name: "重点网络",
            type: "bar",
            stack: "总量",
            barWidth: barWidth,
            // showBackground: true,
            // backgroundStyle: {
            //   color: "background: rgba(255, 255, 255);",
            // },
            label: {
              show: false,
              position: "insideRight",
            },
            // emphasis: {
            //   focus: "series",
            // },
            data: this.rightData,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: 'rgba(68, 241, 182, 1) ' },
                { offset: 1, color: 'rgba(48, 219, 233, 1)' }
              ]),
            },
          },
          {
            name: "重点用现用户",
            type: "bar",
            stack: "总量",
            barWidth: barWidth,

            label: {
              show: false,
              position: "insideLeft",
              color: "#fff", // 使标签颜色与柱子背景色对比明显
              formatter: function (value) {
                if (value.data < 0) {
                  return -value.data;
                }
              },
            },

            // emphasis: {
            //   focus: "series",
            // },
            data: this.leftData, // 取相反数，但视觉上表示向左
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: 'rgba(49, 120, 239, 1)' },
                { offset: 1, color: ' rgba(48, 211, 239, 1)' }
              ])
            },
          },
        ],
        // 调整grid布局，使两个系列的柱子能够紧密排列在一起
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
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