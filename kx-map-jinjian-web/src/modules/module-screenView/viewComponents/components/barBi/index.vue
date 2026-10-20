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
          legendData: ['零钞预约次数', '零钞预约兑换金额'],
          yData: ['杭州市', '宁波市', '温州市', '绍兴市', '湖州市', '嘉兴市', '金华市', '衢州市', '舟山市', '台州市', '丽水市'],
          leftData: [40000, 30000, 20000, 13000, 10000, 20000, 44400, 40000, 60000, 7000, 12000],
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

      var barWidth = 16;
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
          top: "0%",
          right: "5%",
          itemHeight: 4,
          textStyle: {
            color: "#fff",
          },
        },

        yAxis: {
          axisLine: {
            show: true,
            lineStyle: {
              color: "#fff",
            },
          },
          axisTick: {
            show: false,
          },
          splitLine: {
            //修改背景线条样式
            show: false,
          },
          type: "category",
          data: this.info.yData,

        },
        xAxis: {
          type: "value",
          position: "left",
          offset: 0,
          max: this.max,
          min: - this.max,
          axisLine: {
            show: true,
            lineStyle: {
              color: "#fff",
            }
          },
          axisTick: {
            show: false,
          },
          splitLine: {
            //修改背景线条样式
            show: false,
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
        series: [
          {
            name: this.info.legendData[0],
            type: "bar",
            stack: "总量",
            barWidth: barWidth,
            showBackground: true,
            label: {
              show: false,
              position: "insideRight",
            },

            data: this.leftData,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
                { offset: 0, color: '#1890FF' },
                { offset: 1, color: 'rgba(24, 144, 255, 0.35)' }
              ]),
              borderRadius: [0, 2, 2, 0]
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
              formatter: function (value) {
                if (value.data < 0) {
                  return -value.data;
                }
              },
            },

            data: this.rightData, // 取相反数，但视觉上表示向左
            itemStyle: {
              color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [
                { offset: 0, color: 'rgba(30, 231, 231, 1)' },
                { offset: 1, color: 'rgba(30, 231, 231, 0.35)' }
              ]),

              borderRadius: [2, 0, 0, 2]
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
    }
  }
};
</script>

<style scoped lang="scss">
.bar-box {
  width: 430px;
  height: 350px;
  display: flex;
  align-items: center;

  .echarts {
    width: 100%;
    height: 100%;

  }
}
</style>