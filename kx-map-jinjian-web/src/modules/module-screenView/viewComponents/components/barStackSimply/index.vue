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
          yDate: [444, 500, 400, 600, 700, 300, 740, 200, 300, 200, 100],
          yDate1: [200, 444, 500, 1000, 290, 300, 400, 600, 134, 222, 333],
          yDate2: [444, 500, 1000, 290, 300, 400, 600, 700, 444, 555, 222],

        }
      }
    }
  },
  data() {
    return {
      option: {},
      sumList: []
    }
  },
  mounted() {
    this.sumList = this.info.sumList
    this.initEchart();
  },
  methods: {
    initEchart() {
      const myChart = echarts.init(this.$refs.echart);
      let colorList = [
        '#0044FF',
        '#0EECE4',
        '#F8ED68',
      ]
      var barWidth = 10
      const xData = this.info.xData
      this.option = {
        color: colorList,
        grid: {
          left: '2%',
          right: '1%',
          top: '3%',
          bottom: '5%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: xData,
            axisLabel: {
              interval: 0,

              textStyle: {
                fontSize: 11,
                color: "#fff"
              }
            },
            axisLine: {
              show: true,
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
              show: false,
              lineStyle: {
                type: "dashed",
                color: "rgba(230, 247, 255, 0.1)"
              }
            },
          }
        ],
        yAxis: [
          {
            type: 'value',
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
              }
            },
          }
        ],
        series: [
          {
            barWidth: barWidth,
            name: xData[0],
            type: 'bar',
            stack: 'stack',
            data: this.info.yDate
          },
          {
            name: xData[1],
            type: 'bar',
            stack: 'stack',
            data: this.info.yDate1
          },
          {
            name: xData[2],
            type: 'bar',
            stack: 'stack',
            data: this.info.yDate2
          }
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
  height: 300px;
  display: flex;
  align-items: center;

  .echarts {
    width: 100%;
    height: 100%;
  }


}
</style>