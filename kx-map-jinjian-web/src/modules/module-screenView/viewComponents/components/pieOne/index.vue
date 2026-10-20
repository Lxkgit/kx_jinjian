<template>
  <div class="grid-box">
    <div ref="echart" class="echarts"></div>
    <div class="bg"></div>
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
          { value: 23, name: '红牌商户数' },
          { value: 17, name: '黄牌商户数' },
          { value: 52, name: '绿牌商户数' }
        ]
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
      const that = this;
      const colorList = [
        '#FF1B4C', '#FFC26F', '#53FFB1'
      ];
      const myChart = echarts.init(this.$refs.echart);

      // 预计算数据总和
      const total = this.info.reduce((sum, item) => sum + item.value, 0);

      this.option = {
        legend: {
          orient: 'vertical',
          right: 0,
          top: 'center',
          itemGap: 20,
          color: '#fff',
          itemHeight: 6,
          itemWidth: 6,

          formatter: function (name) {
            const item = that.option.series[0].data.find(d => d.name === name);
            const p = Math.round((item.value / total) * 100); // 占比
            const index = that.option.series[0].data.findIndex(d => d.name === name);
            return `${name}    {${String.fromCharCode(97 + index)}|${p} %}`;  //String.fromCharCode(97) 代表字母a  依次类推
          },
          textStyle: {
            color: "#fff",
            fontSize: 14,
            rich: Object.fromEntries(colorList.map((color, index) => [
              String.fromCharCode(97 + index),
              { color }
            ]))
          }
        },
        series: [
          {
            avoidLabelOverlap: true,
            labelLine: {
              show: true
            },
            label: {
              show: true,
              position: 'outside',
              showAbove: true,
              position: 'outside',
              formatter: function (params) {
                const { data } = params;
                return [`{a|${data.value}}`, `{b|${data.name}}`].join('\n');
              },
              rich: {
                a: {
                  fontSize: 16,
                  lineHeight: 20,
                  color: '#fff',
                },
                b: {
                  fontSize: 14,
                  color: '#fff',
                  lineHeight: 20,
                },
              }
            },
            type: 'pie',
            center: ['32%', '50%'],
            radius: ['36%', '62%'],
            data: this.info,
            itemStyle: {
              color: params => colorList[params.dataIndex]
            }
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
  height: 250px;
  margin-top: -20px;
  display: flex;
  align-items: center;
  position: relative;

  .echarts {
    width: 100%;
    height: 100%;

  }

  .bg {
    position: absolute;
    left: 38px;
    width: 200px;
    height: 200px;
    background: url(../../assets/pieBg.png) no-repeat;
    background-size: 100%;
  }
}
</style>