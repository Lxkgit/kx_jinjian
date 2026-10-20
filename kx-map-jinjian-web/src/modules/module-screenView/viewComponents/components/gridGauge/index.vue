<template>
  <div class="gridBox">
    <div ref="echart" class="echarts"></div>
    <div class="bottom">{{ info.title }}</div>
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
          max: 100,
          value: 50,
          colorStart: '#FFCF4C',
          colorStop: '#005EFF',
          title: '行政村网格'

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
      var that = this

      this.option = {
        grid: {
          left: '3%',
          right: '4%',
          top: '0%',
          bottom: '0%',
          containLabel: true
        },
        series: [
          {
            name: 'Pressure',
            type: 'gauge', // 仪表盘图
            startAngle: 220,
            endAngle: -40,
            min: 0,
            max: this.info.max,
            center: ['50%', '50%'],
            progress: {
              show: false
            },
            pointer: { show: false },
            axisTick: { show: false },
            splitLine: { show: false },
            axisLabel: { show: false },
            axisLine: {
              lineStyle: {
                width: 20,
                color: [
                  [1, 'rgba(255,255,255,0.1) ']
                ]
              }
            },
            detail: {
              valueAnimation: true,
              width: '60%',
              lineHeight: 30,
              borderRadius: 8,
              offsetCenter: [0, '-0%'],
              fontSize: 24,
              fontWeight: 'bolder',
              formatter: (value) => {
                const rate = Math.round(value / this.info.max * 100)
                return `{a|${value}个}\n{b|${rate}%}`
              },
              rich: {
                a: {
                  fontSize: 24,
                  color: `#fff`,
                },
                b: {
                  fontSize: 18,
                  color: `rgba(255,255,255,0.5`,
                }
              }
            },
            data: [
              {
                value: this.info.value
              }
            ]
          },
          {
            name: 'Pressure',
            type: 'gauge', // 仪表盘图
            startAngle: 220,
            endAngle: -40,
            min: 0,
            max: this.info.max,
            center: ['50%', '50%'],
            progress: {
              // 进度环
              show: true,
              width: 10,
              itemStyle: {
                color: {
                  type: 'linear',
                  x: 0,
                  y: 0,
                  x2: 0,
                  y2: 1,
                  colorStops: [
                    {
                      // 进度环的渐变色
                      offset: 0,
                      color: `${this.info.colorStart}` // 0% 处的颜色
                    },
                    {
                      offset: 1,
                      color: `${this.info.colorStop}` // 100% 处的颜色
                    }
                  ]
                }
              }
            },
            pointer: { show: false },
            axisTick: { show: false },
            splitLine: { show: false },
            axisLabel: { show: false },
            axisLine: {
              lineStyle: {
                width: 10,
                color: [
                  [1, 'rgba(0,0,0,0.2)']
                ]
              }
            },
            detail: {
              show: false
            },
            data: [
              {
                value: this.info.value
              }
            ]
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
.gridBox {
  width: 200px;
  height: 160px;
  display: flex;
  position: relative;

  .echarts {
    width: 100%;
    height: 100%;
  }

  .bottom {
    color: #fff;
    text-align: center;
    font-size: 14px;
    position: absolute;
    bottom: 10px;
    left: 50%;
    transform: translateX(-50%);
    width: 140px;
    height: 38px;
    background: url('../../assets/gaugeBottom.png') no-repeat;
    background-size: 100%;
  }


}
</style>