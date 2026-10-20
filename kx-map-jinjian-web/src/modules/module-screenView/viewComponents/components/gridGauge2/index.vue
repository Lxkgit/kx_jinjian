<template>
  <div class="gridBox">
    <div class="left">
      <div ref="echart" class="echarts"></div>
    </div>
    <div class="right">
      <ul>
        <li v-for="item in list" :key="item.title">
          <div class="lContent">{{ item.title }} <span class="ml10">{{ item.rate }}%</span> </div>
          <div class="rContent">{{ item.value }} </div>
        </li>


      </ul>
    </div>
  </div>
</template>
<script>
import * as echarts from 'echarts';
export default {
  props: {
    info: {
      type: Array,
      default: () => {
        return [
          {
            title: '责任网点',
            value: 400,
          },
          {
            title: '普通网点',
            value: 600,
          }
        ]
      }
    }
  },
  data() {
    return {
      option: {},
      total: 0,
      list: [],
      value: 40,
    }
  },
  mounted() {
    this.updateList();
    this.initEchart();
  },
  methods: {
    updateList() {
      this.total = this.info.reduce((sum, item) => sum + item.value, 0);
      this.list = this.info.map((item) => {
        return {
          ...item,
          rate: Math.round(item.value / this.total * 100)
        };
      })
      this.value = this.list[0].value;
    },
    initEchart() {
      const myChart = echarts.init(this.$refs.echart);
      this.value = this.list[0].value;

      this.option = {
        series: [
          {
            name: 'Pressure',
            type: 'gauge', // 仪表盘图
            startAngle: 90,
            endAngle: -270,
            min: 0,
            max: this.total,
            radius: '75%',
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
                width: 10,
                color: [
                  [1, '#18FDDE']
                ]
              }
            },
            detail: {
              show: false
            },
            data: [
              {
                value: this.total
              }
            ]
          },
          {
            name: 'Pressure',
            type: 'gauge', // 仪表盘图
            startAngle: -90,
            endAngle: 270,
            min: 0,
            max: this.total,
            center: ['50%', '50%'],
            radius: '80%',
            progress: {
              // 进度环
              show: true,
              width: 15,
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
                      color: `#00C7EC` // 0% 处的颜色
                    },
                    {
                      offset: 1,
                      color: `#0056FF` // 100% 处的颜色
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
                width: 20,
                color: [
                  [1, 'rgba(0,0,0,0.2)']
                ]
              }
            },

            detail: {
              valueAnimation: true,
              width: '60%',
              borderRadius: 8,
              offsetCenter: [0, '-0%'],
              fontSize: 28,
              fontWeight: 'bolder',
              formatter: (value) => {
                // const rate = Math.floor(value / this.total * 100)
                return `{a|${this.total}}\n{b|总数}`
              },
              rich: {
                a: {
                  fontSize: 22,
                  color: `#fff`,
                },
                b: {
                  fontSize: 16,
                  color: `rgba(255,255,255,0.5)`,
                }
              }
            },
            data: [
              {
                value: this.value
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
      this.updateList();
      this.initEchart();
    }
  }
};
</script>

<style scoped lang="scss">
.gridBox {
  width: 430px;
  height: 130px;
  margin-top: -10px;
  display: flex;
  position: relative;

  .left {
    display: block;
    height: 118px;
    width: 138px;
    margin-right: 20px;

    .echarts {
      width: 100%;
      height: 100%;
    }
  }

  .right {
    margin-top: 16px;

    li {
      display: flex;
      justify-content: space-between;
      padding-left: 50px;
      border-radius: 20px;
      width: 246px;
      height: 38px;
      line-height: 38px;
      opacity: 1;
      color: #fff;
      margin-bottom: 16px;
      position: relative;
      font-size: 14px;

      /* 自动布局 */
      display: flex;
      background: linear-gradient(90deg, rgba(64, 148, 223, 0.2778) 0%, rgba(79, 180, 216, 0) 100%);

      .ml10 {
        margin-left: 10px;
        color: #18FDDE;
      }

      .rContent {
        color: #18FDDE;
      }

      &:before {
        content: '';
        position: absolute;
        transform: translate(-50%, -50%);
        left: 25px;
        top: 50%;
        width: 10px;
        height: 10px;
        border-radius: 50%;
        background: #18FDDE;
      }
    }

    &:nth-child(2) {
      .ml10 {
        margin-left: 10px;
        color: #00C7EC;
      }

      &:before {
        background: #00C7EC;
      }

      .rContent {
        color: #00C7EC;
      }
    }

  }





}
</style>