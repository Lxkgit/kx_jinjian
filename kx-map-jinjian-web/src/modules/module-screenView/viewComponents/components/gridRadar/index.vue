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
          indicatorData: [
            { max: 200000, name: '杭州市' },
            { max: 200000, name: '宁波市' },
            { max: 200000, name: '温州市' },
            { max: 200000, name: '绍兴市' },
            { max: 200000, name: '湖州市' },
            { max: 200000, name: '嘉兴市' },
            { max: 200000, name: '金华市' },
            { max: 200000, name: '衢州市' },
            { max: 200000, name: '舟山市' },
            { max: 200000, name: '台州市' },
            { max: 200000, name: '丽水市' },
          ],
          date: [19722, 151241, 132697, 150916, 45675, 73554, 186995, 83856, 134, 114332, 23615],

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
      this.option = {
        tooltip: {
          trigger: 'axis'
        },
        radar: {
          indicator: this.info.indicatorData,
          axisName: {
            color: "#fff"
          },

        },
        series: [
          {
            type: 'radar',
            tooltip: {
              trigger: 'item',
              position: 'inside'
            },
            data: [
              {
                value: this.info.date,
                name: '商户摸排情况',
                symbol: 'none',
                lineStyle: {
                  width: 2,
                  color: "rgba(87, 131, 246, 7)"
                },

                areaStyle: {
                  color: new echarts.graphic.RadialGradient(0.1, 0.5, 1, [
                    {
                      color: 'rgba(0, 255, 255, 0.6)',
                      offset: 0
                    },
                    {
                      color: 'rgba(56, 0, 255, 0.1)',
                      offset: 1
                    }
                  ])
                }
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